package com.cloud.plat.admin.dao;

import com.cloud.plat.common.base.base.BaseDao;
import com.cloud.plat.admin.api.entity.SysDept;

import java.util.List;

/**
 * 部门数据处理层
 * @describe 
 */
public interface SysDeptDao extends BaseDao<SysDept, Long> {

    /**
     * 通过父id获取 升序
     * @param parentId
     * @return
     */
    List<SysDept> findByParentIdOrderBySortOrder(Long parentId);

    /**
     * 通过父id获取 升序 数据权限
     * @param parentId
     * @param departmentIds
     * @return
     */
    List<SysDept> findByParentIdAndIdInOrderBySortOrder(Long parentId, List<Long> departmentIds);

    /**
     * 通过父id和状态获取 升序
     * @param parentId
     * @param status
     * @return
     */
    List<SysDept> findByParentIdAndStatusOrderBySortOrder(Long parentId, Integer status);

    /**
     * 部门名模糊搜索 升序
     * @param title
     * @return
     */
    List<SysDept> findByTitleLikeOrderBySortOrder(String title);

    /**
     * 部门名模糊搜索 升序 数据权限
     * @param title
     * @param departmentIds
     * @return
     */
    List<SysDept> findByTitleLikeAndIdInOrderBySortOrder(String title, List<Long> departmentIds);
}