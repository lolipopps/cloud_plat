package com.cloud.plat.admin.controller;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysRoleDept;
import com.cloud.plat.admin.api.entity.SysRoleMenu;
import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.admin.service.SysRoleDeptService;
import com.cloud.plat.admin.service.SysRoleMenuService;
import com.cloud.plat.admin.service.SysRoleService;
import com.cloud.plat.admin.service.SysUserRoleService;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.util.PageUtil;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.PageVo;
import com.cloud.plat.common.base.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @describe x
 */
@Slf4j
@RestController
@Api(value = "sys_role", tags = "角色管理接口")
@RequestMapping("/role")
@Transactional
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleMenuService rolePermissionService;

    @Autowired
    private SysRoleDeptService roleDepartmentService;


    @Autowired
    private RedisTemplateHelper redisTemplate;

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部角色")
    public Result<Object> roleGetAll() {

        List<SysRole> list = roleService.getAll();
        return ResultUtil.data(list);
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取角色")
    public Result<Page<SysRole>> getRoleByPage(String key, PageVo page) {

        Page<SysRole> list = roleService.findByCondition(key, PageUtil.initPage(page));
        for (SysRole role : list.getContent()) {
            // 角色拥有权限
            List<SysRoleMenu> permissions = rolePermissionService.findByRoleId(role.getId());
            role.setPermissions(permissions);
            // 角色拥有数据权限
            List<SysRoleDept> departments = roleDepartmentService.findByRoleId(role.getId());
            role.setDepartments(departments);
        }
        return new ResultUtil<Page<SysRole>>().setData(list);
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public Result<Object> setDefault(@RequestParam Long id,
                                     @RequestParam Boolean isDefault) {

        SysRole role = roleService.get(id);
        if (role == null) {
            return ResultUtil.error("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.update(role);
        return ResultUtil.success("设置成功");
    }

    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public Result<Object> editRolePerm(@RequestParam Long roleId,
                                       @RequestParam(required = false) Long[] permIds) {

        // 删除其关联权限
        rolePermissionService.deleteByRoleId(roleId);
        // 批量分配新权限
        if (permIds != null) {
            List<SysRoleMenu> list = Arrays.asList(permIds).stream().map(e -> {
                return new SysRoleMenu().setRoleId(roleId).setMenuId(e);
            }).collect(Collectors.toList());
            rolePermissionService.saveOrUpdateAll(list);
        }
        //手动批量删除缓存
        redisTemplate.deleteByPattern("user::" + "*");
        redisTemplate.deleteByPattern("userRole::" + "*");
        redisTemplate.deleteByPattern("permission::userMenuList::*");
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/editRoleDep", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配数据权限")
    public Result<Object> editRoleDep(@RequestParam Long roleId,
                                      @RequestParam Integer dataType,
                                      @RequestParam(required = false) Long[] depIds) {

        SysRole r = roleService.get(roleId);
        r.setDataType(dataType);
        roleService.update(r);
        if (CommonConstant.DATA_TYPE_CUSTOM.equals(dataType)) {
            // 删除其关联数据权限
            roleDepartmentService.deleteByRoleId(roleId);
            // 批量分配新数据权限
            if (depIds != null) {
                List<SysRoleDept> list = Arrays.asList(depIds).stream().map(e -> {
                    return new SysRoleDept().setRoleId(roleId).setDeptId(e);
                }).collect(Collectors.toList());
                roleDepartmentService.saveOrUpdateAll(list);
            }
        }
        // 手动删除相关缓存
        redisTemplate.deleteByPattern("department::" + "*");
        redisTemplate.deleteByPattern("userRole::" + "*");

        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据")
    public Result<SysRole> save(SysRole role) {

        SysRole r = roleService.save(role);
        return new ResultUtil<SysRole>().setData(r);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新数据")
    public Result<SysRole> edit(SysRole entity) {

        SysRole r = roleService.update(entity);
        // 手动批量删除缓存
        redisTemplate.deleteByPattern("user::" + "*");
        redisTemplate.deleteByPattern("userRole::" + "*");
        return new ResultUtil<SysRole>().setData(r);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delByIds(@RequestParam Long[] ids) {

        for (Long id : ids) {
            List<SysUserRole> list = userRoleService.findByRoleId(id);
            if (list != null && list.size() > 0) {
                return ResultUtil.error("删除失败，包含正被用户使用关联的角色");
            }
        }
        for (Long id : ids) {
            roleService.delete(id);
            // 删除关联菜单权限
            rolePermissionService.deleteByRoleId(id);
            // 删除关联数据权限
            roleDepartmentService.deleteByRoleId(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

}
