package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysRoleDept;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色部门数据处理层
 * @describe 
 */
public interface SysRoleDeptDao extends BaseDao<SysRoleDept, Long> {

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<SysRoleDept> findByRoleId(Long roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    @Modifying
    @Query("delete from SysRoleDept r where r.roleId = ?1")
    void deleteByRoleId(Long roleId);

    /**
     * 通过角色id删除
     * @param deptId
     */
    @Modifying
    @Query("delete from SysRoleDept r where r.deptId = ?1")
    void deleteByDepartmentId(Long deptId);
}