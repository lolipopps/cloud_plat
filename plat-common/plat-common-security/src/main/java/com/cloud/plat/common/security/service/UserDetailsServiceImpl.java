package com.cloud.plat.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.feign.RemoteUserService;
import com.cloud.plat.common.core.constant.CacheConstants;
import com.cloud.plat.common.core.constant.CommonConstants;
import com.cloud.plat.common.core.constant.SecurityConstants;
import com.cloud.plat.common.base.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 0:28
 * @contact 269016084@qq.com
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * 用户密码登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (AuthUser) cache.get(username).get();
        }
        Result<SysUser> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
        UserDetails userDetails = getUserDetails(result);
        if (cache != null) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }

    /**
     * 构建userdetails
     *
     * @param username 用户信息
     * @return
     */
    public SysUser getSysUser(String username) {
        Result<SysUser> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
        if (result == null || result.getResult() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return result.getResult();
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(Result<SysUser> result) {
        if (result == null || result.getResult() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        SysUser info = result.getResult();
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            info.getRoles().forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role.getName()));
            // 获取资源
            info.getPermissions().forEach(menu -> dbAuthsSet.add(menu.getPath()));

        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));


        // 构造security用户
        return new AuthUser(info.getId(), info.getDeptId(), info.getUsername(),
                SecurityConstants.BCRYPT + info.getPassword(),
                info.getStatus().equals(CommonConstants.STATUS_NORMAL), true, true, true, authorities);
    }



}
