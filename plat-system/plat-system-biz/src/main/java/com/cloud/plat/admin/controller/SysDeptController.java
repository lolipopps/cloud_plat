package com.cloud.plat.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.dto.SysDeptHeader;
import com.cloud.plat.admin.api.entity.SysDept;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.service.SysDeptHeaderService;
import com.cloud.plat.admin.service.SysDeptService;
import com.cloud.plat.admin.service.SysRoleDeptService;
import com.cloud.plat.admin.service.SysUserService;
import com.cloud.plat.admin.util.HibernateProxyTypeAdapter;
import com.cloud.plat.common.core.exception.HytException;
import com.cloud.plat.common.core.util.CommonUtil;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.Result;
import com.cloud.plat.common.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @describe
 */
@Slf4j
@RestController
@Api(value = "sys_dept", tags = "日志管理模块")
@RequestMapping("/sys/dept")
@CacheConfig(cacheNames = "dept")
@Transactional
public class SysDeptController {

    @Autowired
    private SysDeptService departmentService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleDeptService roleDepartmentService;

    @Autowired
    private SysDeptHeaderService departmentHeaderService;


    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过parentId获取")
    public Result<List<SysDept>> getByParentId(@PathVariable Long parentId,
                                               @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<SysDept> list;
        SysUser u = securityUtil.getCurrUser();
        String key = "department::" + parentId + "::" + u.getId() + "::" + openDataFilter;
        String v = redisTemplate.get(key);
        if (StrUtil.isNotBlank(v)) {
            list = new Gson().fromJson(v, new TypeToken<List<SysDept>>() {
            }.getType());
            return new ResultUtil<List<SysDept>>().setData(list);
        }
        list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        setInfo(list);
        redisTemplate.set(key,
                new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<SysDept>>().setData(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(SysDept department) {

        SysDept d = departmentService.save(department);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if (!CommonConstant.PARENT_ID.equals(department.getParentId())) {
            SysDept parent = departmentService.get(department.getParentId());
            if (parent.getIsParent() == null || !parent.getIsParent()) {
                parent.setIsParent(true);
                departmentService.update(parent);
            }
        }
        // 更新缓存
        redisTemplate.deleteByPattern("department::*");
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(SysDept department,
                               @RequestParam(required = false) Long[] mainHeader,
                               @RequestParam(required = false) Long[] viceHeader) {

        SysDept old = departmentService.get(department.getId());
        SysDept d = departmentService.update(department);
        // 先删除原数据
        departmentHeaderService.deleteByDeptId(department.getId());
        List<SysDeptHeader> headers = new ArrayList<>();
        if (mainHeader != null) {
            for (Long id : mainHeader) {
                SysDeptHeader dh = new SysDeptHeader().setUserId(id).setDeptId(d.getId())
                        .setType(CommonConstant.HEADER_TYPE_MAIN);
                headers.add(dh);
            }
        }
        if (viceHeader != null) {
            for (Long id : viceHeader) {
                SysDeptHeader dh = new SysDeptHeader().setUserId(id).setDeptId(d.getId())
                        .setType(CommonConstant.HEADER_TYPE_VICE);
                headers.add(dh);
            }
        }
        // 批量保存
        departmentHeaderService.saveOrUpdateAll(headers);
        // 若修改了部门名称
        if (!old.getTitle().equals(department.getTitle())) {
            userService.updateDeptTitle(department.getId(), department.getTitle());
            // 删除所有用户缓存
            redisTemplate.deleteByPattern("user::" + "*");
        }
        // 手动删除所有部门缓存
        redisTemplate.deleteByPattern("department::" + "*");
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@RequestParam Long[] ids) {

        for (Long id : ids) {
            deleteRecursion(id, ids);
        }
        // 手动删除所有部门缓存
        redisTemplate.deleteByPattern("department::" + "*");
        // 删除数据权限缓存
        redisTemplate.deleteByPattern("userRole::depIds::" + "*");
        return ResultUtil.success("批量通过id删除数据成功");
    }

    public void deleteRecursion(Long id, Long[] ids) {

        List<SysUser> list = userService.findByDepartmentId(id);
        if (list != null && list.size() > 0) {
            throw new HytException("删除失败，包含正被用户使用关联的部门");
        }
        // 获得其父节点
        SysDept dep = departmentService.get(id);
        SysDept parent = null;
        if (dep != null && null != dep.getParentId()) {
            parent = departmentService.get(dep.getParentId());
        }
        departmentService.delete(id);
        // 删除关联数据权限
        roleDepartmentService.deleteByDepartmentId(id);
        // 删除关联部门负责人
        departmentHeaderService.deleteByDeptId(id);
        // 删除流程关联节点
        // 判断父节点是否还有子节点
        if (parent != null) {
            List<SysDept> childrenDeps = departmentService.findByParentIdOrderBySortOrder(parent.getId(), false);
            if (childrenDeps == null || childrenDeps.isEmpty()) {
                parent.setIsParent(false);
                departmentService.update(parent);
            }
        }
        // 递归删除
        List<SysDept> departments = departmentService.findByParentIdOrderBySortOrder(id, false);
        for (SysDept d : departments) {
            if (!CommonUtil.judgeIds(d.getId(), ids)) {
                deleteRecursion(d.getId(), ids);
            }
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public Result<List<SysDept>> searchByTitle(@RequestParam String title,
                                               @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<SysDept> list = departmentService.findByTitleLikeOrderBySortOrder("%" + title + "%", openDataFilter);
        setInfo(list);
        return new ResultUtil<List<SysDept>>().setData(list);
    }

    public void setInfo(List<SysDept> list) {

        // lambda表达式
        list.forEach(item -> {
            if (!CommonConstant.PARENT_ID.equals(item.getParentId())) {
                SysDept parent = departmentService.get(item.getParentId());
                item.setParentTitle(parent.getTitle());
            } else {
                item.setParentTitle("一级部门");
            }
            // 设置负责人
            item.setMainHeader(departmentHeaderService.findHeaderByDeptId(item.getId(), CommonConstant.HEADER_TYPE_MAIN));
            item.setViceHeader(departmentHeaderService.findHeaderByDeptId(item.getId(), CommonConstant.HEADER_TYPE_VICE));
        });
    }
}
