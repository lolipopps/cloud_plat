package com.cloud.plat.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.admin.mapper.SysUserRoleMapper;
import com.cloud.plat.admin.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @describe x
 */
@Service
public class ISysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> findByUserId(Long userId) {

        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public List<Long> findDeptIdsByUserId(Long userId) {

        return userRoleMapper.findDepIdsByUserId(userId);
    }
}
