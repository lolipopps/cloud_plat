package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysRoleMenu;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色权限数据处理层
 * @describe
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenu, Long> {

    /**
     * 通过permissionId获取
     * @param menuId
     * @return
     */
    List<SysRoleMenu> findByMenuId(Long menuId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<SysRoleMenu> findByRoleId(Long roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    @Modifying
    @Query("delete from SysRoleMenu r where r.roleId = ?1")
    void deleteByRoleId(Long roleId);
}