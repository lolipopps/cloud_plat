package com.cloud.plat.common.security.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.plat.common.security.properties.AppTokenProperties;
import com.cloud.plat.common.security.properties.TokenProperties;
import com.cloud.plat.common.security.service.AuthUser;
import com.cloud.plat.common.security.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import com.cloud.plat.admin.api.dto.SysRoleDto;
import com.cloud.plat.admin.api.dto.SysTitleMenuDto;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.common.core.constant.SecurityConstant;
import com.cloud.plat.common.core.constant.SecurityConstants;
import com.cloud.plat.common.core.exception.HytException;
import com.cloud.plat.common.core.vo.TokenMember;
import com.cloud.plat.common.core.vo.TokenUser;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 0:44
 * @contact 269016084@qq.com
 **/
@Component
public class SecurityUtil {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private AppTokenProperties appTokenProperties;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public String getToken(String username, Boolean saveLogin) {
        if (StrUtil.isBlank(username)) {
            throw new HytException("username不能为空");
        }
        Boolean saved = false;
        if (saveLogin == null || saveLogin) {
            saved = true;
            if (!tokenProperties.getRedis()) {
                tokenProperties.setTokenExpireTime(tokenProperties.getSaveLoginTime() * 60 * 24);
            }
        }
        // 生成token
        SysUser u = getCurrUser();
        List<String> list = new ArrayList<>();
        // 缓存权限
        if (tokenProperties.getStorePerms()) {
            for (SysTitleMenuDto p : u.getPermissions()) {
                if (StrUtil.isNotBlank(p.getTitle()) && StrUtil.isNotBlank(p.getPath())) {
                    list.add(p.getTitle());
                }
            }
            for (SysRoleDto r : u.getRoles()) {
                list.add(r.getName());
            }
        }
        // 登陆成功生成token
        String token;
        if (tokenProperties.getRedis()) {
            // redis
            token = IdUtil.simpleUUID();
            TokenUser user = new TokenUser(u.getUsername(), list, saved);
            // 单设备登录 之前的token失效
            if (tokenProperties.getSdl()) {
                String oldToken = redisTemplate.get(SecurityConstant.USER_TOKEN + u.getUsername());
                if (StrUtil.isNotBlank(oldToken)) {
                    redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
                }
            }
            if (saved) {
                redisTemplate.set(SecurityConstant.USER_TOKEN + u.getUsername(), token, tokenProperties.getSaveLoginTime(), TimeUnit.DAYS);
                redisTemplate.set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenProperties.getSaveLoginTime(), TimeUnit.DAYS);
            } else {
                redisTemplate.set(SecurityConstant.USER_TOKEN + u.getUsername(), token, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
                redisTemplate.set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
            }
        } else {
            // JWT不缓存权限 避免JWT长度过长
            list = null;
            // JWT
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(u.getUsername())
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getTokenExpireTime() * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }
        return token;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public SysUser getCurrUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new HytException("未检测到登录用户");
        }
        return userDetailsService.getSysUser(authentication.getName());
    }


    public String getAppToken(String username, Integer platform) {

        if (StrUtil.isBlank(username)) {
            throw new HytException("username不能为空");
        }
        // 生成token
        String token = IdUtil.simpleUUID();
        TokenMember member = new TokenMember(username, platform);
        String key = SecurityConstant.MEMBER_TOKEN + member.getUsername() + ":" + platform;
        // 单平台登录 之前的token失效
        if (appTokenProperties.getSpl()) {
            String oldToken = redisTemplate.get(key);
            if (StrUtil.isNotBlank(oldToken)) {
                redisTemplate.delete(SecurityConstant.TOKEN_MEMBER_PRE + oldToken);
            }
        }
        redisTemplate.set(key, token, appTokenProperties.getTokenExpireTime(), TimeUnit.DAYS);
        redisTemplate.set(SecurityConstant.TOKEN_MEMBER_PRE + token, new Gson().toJson(member), appTokenProperties.getTokenExpireTime(), TimeUnit.DAYS);
        return token;
    }

    /**
     * 获取用户
     */
    public static AuthUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUser) {
            return (AuthUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public static AuthUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户角色信息
     * @return 角色集合
     */
    public List<Integer> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Integer> roleIds = new ArrayList<>();
        authorities.stream().filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Integer.parseInt(id));
                });
        return roleIds;
    }
}
