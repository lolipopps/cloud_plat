package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.entity.SysRoleMenu;
import com.cloud.plat.common.base.base.BaseService;

import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:28
 * @contact 269016084@qq.com
 *
 **/
public interface SysRoleMenuService extends BaseService<SysRoleMenu, Long> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<SysRoleMenu> findByPermissionId(Long permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<SysRoleMenu> findByRoleId(Long roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(Long roleId);
}