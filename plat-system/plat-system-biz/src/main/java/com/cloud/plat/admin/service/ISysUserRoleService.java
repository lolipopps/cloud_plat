package com.cloud.plat.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUserRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @describe x
 */
@CacheConfig(cacheNames = "userRole")
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    List<SysRole> findByUserId(Long userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<Long> findDeptIdsByUserId(Long userId);
}
