package com.cloud.plat.admin.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.admin.mapper.SysMenuMapper;
import com.cloud.plat.admin.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @describe x
 */
@Service
public class ISysmenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper permissionMapper;

    @Override
    public List<SysMenu> findByUserId(Long userId) {

        return permissionMapper.findByUserId(userId);
    }
}
