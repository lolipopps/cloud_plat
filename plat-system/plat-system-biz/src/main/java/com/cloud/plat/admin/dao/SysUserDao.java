package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户数据处理层
 * @describe x
 */
public interface SysUserDao extends BaseDao<SysUser, Long> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    SysUser findByMobile(String mobile);

    /**
     * 通过邮件获取用户
     * @param email
     * @return
     */
    SysUser findByEmail(String email);

    /**
     * 通过部门id获取
     * @param deptId
     * @return
     */
    List<SysUser> findByDeptId(Long deptId);

    /**
     * 通过部门id获取
     * @param ids
     * @return
     */
    List<SysUser> findByIdIn(Long[] ids);

    /**
     * 通过用户名模糊搜索
     * @param key
     * @param status
     * @return
     */
    @Query("select u from SysUser u where u.username like %?1%   and u.status = ?2")
    List<SysUser> findByUsernameLikeAndStatus(String key, Integer status);

    /**
     * 更新部门名称
     * @param deptId
     * @param deptTitle
     */
    @Modifying
    @Query("update SysUser u set u.deptTitle=?2 where u.deptId=?1")
    void updateDeptTitle(Long deptId, String deptTitle);


}
