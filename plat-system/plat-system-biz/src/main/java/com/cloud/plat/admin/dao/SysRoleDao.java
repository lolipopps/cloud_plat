package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.common.base.base.BaseDao;


import java.util.List;

/**
 * 角色数据处理层
 * @describe x
 */
public interface SysRoleDao extends BaseDao<SysRole, Long> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<SysRole> findByDefaultRole(Boolean defaultRole);

    /**
     * 通过roleId查找
     * @param name
     * @return
     */
    List<SysRole> findByName(String name);
}
