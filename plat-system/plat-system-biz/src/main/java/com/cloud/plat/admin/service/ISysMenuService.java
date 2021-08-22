package com.cloud.plat.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.plat.admin.api.entity.SysMenu;

import java.util.List;

/**
 * @describe x
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<SysMenu> findByUserId(Long userId);
}
