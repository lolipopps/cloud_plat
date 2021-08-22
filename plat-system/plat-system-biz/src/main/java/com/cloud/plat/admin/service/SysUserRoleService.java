package com.cloud.plat.admin.service;


import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.common.base.base.BaseService;


import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:25
 * @contact 269016084@qq.com
 **/
public interface SysUserRoleService extends BaseService<SysUserRole, Long> {

    /**
     * 通过roleId查找
     *
     * @param roleId
     * @return
     */
    List<SysUserRole> findByRoleId(Long roleId);


    /**
     * 通过roleName查找
     *
     * @param roleName
     * @return
     */
    List<SysUser> findUserByRoleName(String roleName);

    /**
     * 通过roleId查找用户
     *
     * @param roleId
     * @return
     */
    List<SysUser> findUserByRoleId(Long roleId);

    /**
     * 删除用户角色
     *
     * @param userId
     */
    void deleteByUserId(Long userId);
}
