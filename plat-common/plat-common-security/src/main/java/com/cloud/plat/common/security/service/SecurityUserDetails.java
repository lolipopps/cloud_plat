package com.cloud.plat.common.security.service;

import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.dto.SysRoleDto;
import com.cloud.plat.admin.api.dto.SysTitleMenuDto;
import com.cloud.plat.admin.api.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @describe x
 */
@Slf4j
public class SecurityUserDetails extends SysUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private List<SysTitleMenuDto> permissions;

    private List<SysRoleDto> roles;

    public SecurityUserDetails(SysUser user) {

        if (user != null) {
            // Principal 用户信息
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());

            this.permissions = user.getPermissions();
            this.roles = user.getRoles();
        }
    }

    /**
     * 添加用户拥有的权限和角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 添加请求权限
        if (permissions != null && permissions.size() > 0) {
            for (SysTitleMenuDto permission : permissions) {
                if (StrUtil.isNotBlank(permission.getTitle()) && StrUtil.isNotBlank(permission.getPath())) {
                    authorityList.add(new SimpleGrantedAuthority(permission.getTitle()));
                }
            }
        }
        // 添加角色
        if (roles != null && roles.size() > 0) {
            // lambda表达式
            roles.forEach(item -> {
                if (StrUtil.isNotBlank(item.getName())) {
                    authorityList.add(new SimpleGrantedAuthority(item.getName()));
                }
            });
        }
        return authorityList;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {

        return CommonConstant.USER_STATUS_LOCK.equals(this.getStatus()) ? false : true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {

        return CommonConstant.USER_STATUS_NORMAL.equals(this.getStatus()) ? true : false;
    }
}