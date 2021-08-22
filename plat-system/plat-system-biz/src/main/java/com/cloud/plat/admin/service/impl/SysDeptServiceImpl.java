package com.cloud.plat.admin.service.impl;
import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.service.ISysUserRoleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.entity.SysDept;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.dao.SysDeptDao;
import com.cloud.plat.admin.service.SysDeptService;
import com.cloud.plat.common.core.constant.CommonConstants;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 部门接口实现
 * @describe 
 */
@Slf4j
@Service
@Transactional
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptDao departmentDao;

    @Autowired(required=false)
    private SecurityUtil securityUtil;

    @Autowired
    private ISysUserRoleService iUserRoleService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Override
    public SysDeptDao getRepository() {
        return departmentDao;
    }

    @Override
    public List<SysDept> findByParentIdOrderBySortOrder(Long parentId, Boolean openDataFilter) {

        // 数据权限
        List<Long> depIds = getDeparmentIds();
        if (depIds != null && depIds.size() > 0 && openDataFilter) {
            return departmentDao.findByParentIdAndIdInOrderBySortOrder(parentId, depIds);
        }
        return departmentDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<SysDept> findByParentIdAndStatusOrderBySortOrder(Long parentId, Integer status) {

        return departmentDao.findByParentIdAndStatusOrderBySortOrder(parentId, status);
    }

    @Override
    public List<SysDept> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter) {

        // 数据权限
        List<Long> depIds = getDeparmentIds();
        if (depIds != null && depIds.size() > 0 && openDataFilter) {
            return departmentDao.findByTitleLikeAndIdInOrderBySortOrder(title, depIds);
        }
        return departmentDao.findByTitleLikeOrderBySortOrder(title);
    }

    @Override
    public List<Long> getDeparmentIds() {
        List<Long> deparmentIds = new ArrayList<>();
        SysUser u  = securityUtil.getCurrUser();
        // 读取缓存
        String key = "userRole::depIds::" + u.getId();
        String v = redisTemplate.get(key);
        if (StrUtil.isNotBlank(v)) {
            deparmentIds = new Gson().fromJson(v, new TypeToken<List<String>>() {
            }.getType());
            return deparmentIds;
        }
        // 当前用户拥有角色
        List<SysRole> roles = iUserRoleService.findByUserId(u.getId());
        // 判断有无全部数据的角色
        Boolean flagAll = false;
        for (SysRole r : roles) {
            if (r.getDataType() == null || r.getDataType().equals(CommonConstant.DATA_TYPE_ALL)) {
                flagAll = true;
                break;
            }
        }
        // 包含全部权限返回null
        if (flagAll) {
            return null;
        }
        // 每个角色判断 求并集
        for (SysRole r : roles) {
            if (r.getDataType().equals(CommonConstant.DATA_TYPE_UNDER)) {
                // 本部门及以下
                if (null == u.getDeptId()) {
                    // 用户无部门
                    deparmentIds.add(-1L);
                } else {
                    // 递归获取自己与子级
                    List<Long> ids = new ArrayList<>();
                    getRecursion(u.getDeptId(), ids);
                    deparmentIds.addAll(ids);
                }
            } else if (r.getDataType().equals(CommonConstant.DATA_TYPE_SAME)) {
                // 本部门
                if (null == u.getDeptId()){
                    // 用户无部门
                    deparmentIds.add(-1L);
                } else {
                    deparmentIds.add(u.getDeptId());
                }
            } else if (r.getDataType().equals(CommonConstant.DATA_TYPE_CUSTOM)) {
                // 自定义
                List<Long> depIds = iUserRoleService.findDeptIdsByUserId(u.getId());
                if (depIds == null || depIds.size() == 0) {
                    deparmentIds.add(-1L);
                } else {
                    deparmentIds.addAll(depIds);
                }
            }
        }
        // 去重
        LinkedHashSet<Long> set = new LinkedHashSet<>(deparmentIds.size());
        set.addAll(deparmentIds);
        deparmentIds.clear();
        deparmentIds.addAll(set);
        // 缓存
        redisTemplate.set(key, new Gson().toJson(deparmentIds), 15L, TimeUnit.DAYS);
        return deparmentIds;

    }
    private void getRecursion(Long departmentId, List<Long> ids) {

        SysDept department = departmentDao.getById(departmentId);
        ids.add(department.getId());
        if (department.getIsParent() != null && department.getIsParent()) {
            // 获取其下级
            List<SysDept> departments = departmentDao.findByParentIdAndStatusOrderBySortOrder(departmentId, CommonConstants.STATUS_NORMAL);
            departments.forEach(d -> {
                getRecursion(d.getId(), ids);
            });
        }
    }

}