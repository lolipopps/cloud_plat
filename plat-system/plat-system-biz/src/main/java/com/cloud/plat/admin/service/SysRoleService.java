package com.cloud.plat.admin.service;


import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.common.base.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:26
 * @contact 269016084@qq.com
 **/
public interface SysRoleService extends BaseService<SysRole, Long> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<SysRole> findByDefaultRole(Boolean defaultRole);

    /**
     * 获取默认角色
     *
     * @param name
     * @return
     */
    List<SysRole> findByName(String name);

    /**
     * 分页获取
     *
     * @param key
     * @param pageable
     * @return
     */
    Page<SysRole> findByCondition(String key, Pageable pageable);
}
