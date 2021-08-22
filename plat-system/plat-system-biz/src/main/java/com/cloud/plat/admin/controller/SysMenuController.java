package com.cloud.plat.admin.controller;
import cn.hutool.core.util.StrUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.dto.SysMenuDto;
import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.admin.api.entity.SysRoleMenu;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.vo.MenuVo;
import com.cloud.plat.admin.config.MySecurityMetadataSource;
import com.cloud.plat.admin.service.ISysMenuService;
import com.cloud.plat.admin.service.SysMenuService;
import com.cloud.plat.admin.service.SysRoleMenuService;
import com.cloud.plat.admin.util.VoUtil;
import com.cloud.plat.common.core.util.ObjectUtil;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.Result;
import com.cloud.plat.common.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @describe
 */
@Slf4j
@RestController
@Api(value = "sys_menu", tags = "菜单管理模块")
@RequestMapping("/sys/menu")
@CacheConfig(cacheNames = "menu")
@Transactional
public class SysMenuController {

    @Autowired
    private SysMenuService permissionService;

    @Autowired
    private SysRoleMenuService rolePermissionService;

    @Autowired
    private ISysMenuService iPermissionService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户页面菜单数据")
    public Result<List<MenuVo>> getAllMenuList() {

        List<MenuVo> menuList;
        // 读取缓存
        SysUser u = securityUtil.getCurrUser();
        String key = "permission::userMenuList::" + u.getId();
        String v = redisTemplate.get(key);
        if (StrUtil.isNotBlank(v)) {
            menuList = new Gson().fromJson(v, new TypeToken<List<MenuVo>>() {
            }.getType());
            return new ResultUtil<List<MenuVo>>().setData(menuList);
        }

        // 用户所有权限 已排序去重
        List<SysMenu> list = iPermissionService.findByUserId(u.getId());

        // 筛选0级页面
        menuList = list.stream().filter(p -> CommonConstant.PERMISSION_NAV.equals(p.getType()))
                .sorted(Comparator.comparing(SysMenu::getSortOrder))
                .map(VoUtil::permissionToMenuVo).collect(Collectors.toList());
        getMenuByRecursion(menuList, list);

        // 缓存
        redisTemplate.set(key, new Gson().toJson(menuList), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<MenuVo>>().setData(menuList);
    }

    private void getMenuByRecursion(List<MenuVo> curr, List<SysMenu> list) {

        curr.forEach(e -> {
            if (CommonConstant.LEVEL_TWO.equals(e.getLevel())) {
                List<String> buttonPermissions = list.stream()
                        .filter(p -> (e.getId()).equals(p.getParentId()) && CommonConstant.PERMISSION_OPERATION.equals(p.getType()))
                        .sorted(Comparator.comparing(SysMenu::getSortOrder))
                        .map(SysMenu::getButtonType).collect(Collectors.toList());
                e.setPermTypes(buttonPermissions);
            } else {
                List<MenuVo> children = list.stream()
                        .filter(p -> (e.getId()).equals(p.getParentId()) && CommonConstant.PERMISSION_PAGE.equals(p.getType()))
                        .sorted(Comparator.comparing(SysMenu::getSortOrder))
                        .map(VoUtil::permissionToMenuVo).collect(Collectors.toList());
                e.setChildren(children);
                if (e.getLevel() < 3) {
                    getMenuByRecursion(children, list);
                }
            }
        });
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取权限菜单树")
    @Cacheable(key = "'allList'")
    public Result<List<SysMenu>> getAllList() {

        List<SysMenu> list = permissionService.getAll();
        // 0级
        List<SysMenu> list0 = list.stream().filter(e -> (CommonConstant.LEVEL_ZERO).equals(e.getLevel()))
                .sorted(Comparator.comparing(SysMenu::getSortOrder)).collect(Collectors.toList());
        getAllByRecursion(list0, list);
        return new ResultUtil<List<SysMenu>>().setData(list0);
    }

    private void getAllByRecursion(List<SysMenu> curr, List<SysMenu> list) {

        curr.forEach(e -> {
            List<SysMenu> children = list.stream().filter(p -> (e.getId()).equals(p.getParentId()))
                    .sorted(Comparator.comparing(SysMenu::getSortOrder)).collect(Collectors.toList());
            e.setChildren(children);
            if (e.getLevel() < 3) {
                getAllByRecursion(children, list);
            }
        });
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    @CacheEvict(key = "'menuList'")
    public Result<SysMenu> add(SysMenu permission) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            List<SysMenu> list = permissionService.findByTitle(permission.getTitle());
            if (list != null && list.size() > 0) {
                return new ResultUtil<SysMenu>().setErrorMsg("名称已存在");
            }
        }
        SysMenu u = permissionService.save(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        return new ResultUtil<SysMenu>().setData(u);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<SysMenu> edit(SysMenuDto permissionDto) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstant.PERMISSION_OPERATION.equals(permissionDto.getType())) {
            // 若名称修改
            SysMenu p = permissionService.get(permissionDto.getId());
            if (!p.getTitle().equals(permissionDto.getTitle())) {
                List<SysMenu> list = permissionService.findByTitle(permissionDto.getTitle());
                if (list != null && list.size() > 0) {
                    return new ResultUtil<SysMenu>().setErrorMsg("名称已存在");
                }
            }
        }
        SysMenu permission = new SysMenu();
        ObjectUtil.copyPropertiesIgnoreNull(permissionDto, permission);
        SysMenu u = permissionService.update(permission);
        // 重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        // 手动批量删除缓存
        redisTemplate.deleteByPattern("user::" + "*");
        redisTemplate.deleteByPattern("permission::userMenuList::*");
        redisTemplate.delete("permission::allList");
        return new ResultUtil<SysMenu>().setData(u);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    @CacheEvict(key = "'menuList'")
    public Result<Object> delByIds(@RequestParam Long[] ids) {

        for (Long id : ids) {
            List<SysRoleMenu> list = rolePermissionService.findByPermissionId(id);
            if (list != null && list.size() > 0) {
                return ResultUtil.error("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (Long id : ids) {
            permissionService.delete(id);
        }
        // 重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        // 手动删除缓存
        redisTemplate.delete("permission::allList");
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "搜索菜单")
    public Result<List<SysMenu>> searchPermissionList(@RequestParam String title) {

        List<SysMenu> list = permissionService.findByTitleLikeOrderBySortOrder("%" + title + "%");
        return new ResultUtil<List<SysMenu>>().setData(list);
    }
}
