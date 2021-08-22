package com.cloud.plat.admin.service.impl;
import com.cloud.plat.admin.api.entity.SysRoleMenu;
import com.cloud.plat.admin.service.SysRoleMenuService;
import com.cloud.plat.admin.dao.SysRoleMenuDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限接口实现
 * @describe
 */
@Slf4j
@Service
@Transactional
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuDao rolePermissionDao;

    @Override
    public SysRoleMenuDao getRepository() {
        return rolePermissionDao;
    }

    @Override
    public List<SysRoleMenu> findByPermissionId(Long menuId) {

        return rolePermissionDao.findByMenuId(menuId);
    }

    @Override
    public List<SysRoleMenu> findByRoleId(Long roleId) {

        return rolePermissionDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {

        rolePermissionDao.deleteByRoleId(roleId);
    }
}