package com.cloud.plat.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @describe x
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<SysRole> findByUserId(@Param("userId") Long userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<Long> findDepIdsByUserId(@Param("userId") Long userId);
}
