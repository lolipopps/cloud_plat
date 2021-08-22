package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户角色数据处理层
 * @describe x
 */
public interface SysUserRoleDao extends BaseDao<SysUserRole, Long> {

    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<SysUserRole> findByRoleId(Long roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    @Modifying
    @Query("delete from SysUserRole u where u.userId = ?1")
    void deleteByUserId(Long userId);
}
