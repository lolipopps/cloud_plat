package com.cloud.plat.admin.service.impl;

import com.cloud.plat.admin.api.entity.SysRoleDept;
import com.cloud.plat.admin.service.SysRoleDeptService;
import com.cloud.plat.admin.dao.SysRoleDeptDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色部门接口实现
 * @describe 
 */
@Slf4j
@Service
@Transactional
public class SysRoleDetServiceImpl implements SysRoleDeptService {

    @Autowired
    private SysRoleDeptDao roleDepartmentDao;

    @Override
    public SysRoleDeptDao getRepository() {
        return roleDepartmentDao;
    }

    @Override
    public List<SysRoleDept> findByRoleId(Long roleId) {

        return roleDepartmentDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {

        roleDepartmentDao.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByDepartmentId(Long departmentId) {

        roleDepartmentDao.deleteByDepartmentId(departmentId);
    }
}