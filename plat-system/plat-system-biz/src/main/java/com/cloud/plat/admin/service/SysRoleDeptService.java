package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.entity.SysRoleDept;
import com.cloud.plat.common.base.base.BaseService;

import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:35
 * @contact 269016084@qq.com
 *
 **/
public interface SysRoleDeptService extends BaseService<SysRoleDept, Long> {

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<SysRoleDept> findByRoleId(Long roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 通过角色id删除
     * @param departmentId
     */
    void deleteByDepartmentId(Long departmentId);
}