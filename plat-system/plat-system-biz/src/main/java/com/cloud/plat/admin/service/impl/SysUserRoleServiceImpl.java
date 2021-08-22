package com.cloud.plat.admin.service.impl;


import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.admin.service.SysRoleService;
import com.cloud.plat.admin.service.SysUserRoleService;
import com.cloud.plat.admin.dao.SysUserDao;
import com.cloud.plat.admin.dao.SysUserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:29
 * @contact 269016084@qq.com
 *
 **/
@Slf4j
@Service
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleDao userRoleDao;

    @Autowired
    private SysUserDao userDao;



    @Autowired
    private SysRoleService roleService;

    @Override
    public SysUserRoleDao getRepository() {
        return userRoleDao;
    }

    @Override
    public List<SysUserRole> findByRoleId(Long roleId) {
        return userRoleDao.findByRoleId(roleId);
    }

    @Override
    public List<SysUser> findUserByRoleName(String roleName) {
        List<SysRole> roles = roleService.findByName(roleName);
        if(null != roles && roles.size() != 0) {
            List<SysUserRole> userRoleList = userRoleDao.findByRoleId(roles.get(0).getId());
            List<SysUser> list = new ArrayList<>();
            for (SysUserRole ur : userRoleList) {
                SysUser u = userDao.findById(ur.getUserId()).orElse(null);
                if (u != null && CommonConstant.USER_STATUS_NORMAL.equals(u.getStatus())) {
                    list.add(u);
                }
            }
            return list;
        }else{
            return null;
        }

    }

    @Override
    public List<SysUser> findUserByRoleId(Long roleId) {

        List<SysUserRole> userRoleList = userRoleDao.findByRoleId(roleId);
        List<SysUser> list = new ArrayList<>();
        for (SysUserRole ur : userRoleList) {
            SysUser u = userDao.findById(ur.getUserId()).orElse(null);
            if (u != null &&  CommonConstant.USER_STATUS_NORMAL.equals(u.getStatus())) {
                list.add(u);
            }
        }
        return list;
    }

    @Override
    public void deleteByUserId(Long userId) {
        userRoleDao.deleteByUserId(userId);
    }
}
