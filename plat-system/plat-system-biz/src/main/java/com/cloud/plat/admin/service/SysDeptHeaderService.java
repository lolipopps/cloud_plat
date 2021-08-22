package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.dto.SysDeptHeader;
import com.cloud.plat.common.base.base.BaseService;

import java.util.List;

/**
 * 部门负责人接口
 * @describe
 */
public interface SysDeptHeaderService extends BaseService<SysDeptHeader, Long> {

    /**
     * 通过部门和负责人类型获取
     * @param departmentId
     * @param type
     * @return
     */
    List<Long> findHeaderByDeptId(Long departmentId, Integer type);

    /**
     * 通过部门获取
     * @param departmentIds
     * @return
     */
    List<SysDeptHeader> findByDeptIdIn(List<Long> departmentIds);

    /**
     * 通过部门id删除
     * @param departmentId
     */
    void deleteByDeptId(Long departmentId);

    /**
     * 通过userId删除
     * @param userId
     */
    void deleteByUserId(Long userId);
}