package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.dto.SysDeptHeader;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 部门负责人数据处理层
 * @describe
 */
public interface SysDeptHeaderDao extends BaseDao<SysDeptHeader, Long> {

    /**
     * 通过部门和负责人类型获取
     * @param departmentId
     * @param type
     * @return
     */
    List<SysDeptHeader> findByDeptIdAndType(Long departmentId, Integer type);

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
    @Modifying
    @Query("delete from SysDeptHeader d where d.deptId = ?1")
    void deleteByDepartmentId(Long departmentId);

    /**
     * 通过userId删除
     * @param userId
     */
    @Modifying
    @Query("delete from SysDeptHeader d where d.userId = ?1")
    void deleteByUserId(Long userId);
}