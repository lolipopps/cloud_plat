/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.plat.admin.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.cloud.plat.admin.api.entity.SysOauthClient;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.vo.Oauth2TokenInfoVo;
import com.cloud.plat.admin.service.SysOauthClientService;
import com.cloud.plat.admin.service.SysUserService;
import com.cloud.plat.common.core.constant.OAuthConstant;
import com.cloud.plat.common.core.constant.SecurityConstant;
import com.cloud.plat.common.core.exception.HytException;
import com.cloud.plat.common.core.util.NameUtil;
import com.cloud.plat.common.log.annotation.PlatLogAo;
import com.cloud.plat.common.log.enums.LogTypeEnum;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.util.PageUtil;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.PageVo;
import com.cloud.plat.common.base.vo.Result;
import com.cloud.plat.common.base.vo.SearchVo;
import com.cloud.plat.common.security.service.SecurityUserDetails;
import com.cloud.plat.common.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/client")
@Api(value = "sys_client", tags = "客户端管理模块")
public class SysOauthClientController {


    @Autowired
    private SysOauthClientService sysOauthClientDetailsService;


    @Autowired
    private SysUserService userService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<SysOauthClient>> getByCondition(SysOauthClient client,
                                                       SearchVo searchVo,
                                                       PageVo pageVo) {

        Page<SysOauthClient> page = sysOauthClientDetailsService.findByCondition(client, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<SysOauthClient>>().setData(page);
    }

    @RequestMapping(value = "/getSecretKey", method = RequestMethod.GET)
    @ApiOperation(value = "生成随机secretKey")
    public Result<String> getSecretKey() {

        String secretKey = IdUtil.simpleUUID();
        return new ResultUtil<String>().setData(secretKey);
    }

    @RequestMapping(value = "/info/{clientId}", method = RequestMethod.GET)
    @ApiOperation(value = "站点基本信息")
    public Result<Object> info(@ApiParam("客户端id") @PathVariable String clientId) {

        SysOauthClient client = getClient(clientId);

        Map<String, Object> map = new HashMap<>(16);
        map.put("name", client.getName());
        map.put("homeUri", client.getHomeUri());
        map.put("autoApprove", client.getAutoApprove());
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ApiOperation(value = "认证获取code")
    @PlatLogAo(description = "认证中心登录", type = LogTypeEnum.LOGIN)
    public Result<Object> authorize(@ApiParam("用户名") @RequestParam String username,
                                    @ApiParam("密码") @RequestParam String password,
                                    @ApiParam("客户端id") @RequestParam String clientId,
                                    @ApiParam("成功授权后回调地址") @RequestParam String redirect_uri,
                                    @ApiParam("授权类型为code") @RequestParam(required = false, defaultValue = "code") String response_type,
                                    @ApiParam("客户端状态值") @RequestParam String state) {

        SysOauthClient client = getClient(clientId);

        if (!client.getRedirectUri().equals(redirect_uri)) {
            return ResultUtil.error("回调地址redirect_uri不正确");
        }
        // 登录认证
        SysUser user;
        if (NameUtil.mobile(username)) {
            user = userService.findByMobile(username);
        } else if (NameUtil.email(username)) {
            user = userService.findByEmail(username);
        } else {
            user = userService.findByUsername(username);
        }
        if (user == null) {
            return ResultUtil.error("用户名不存在");
        }
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResultUtil.error("用户密码不正确");
        }
        String accessToken = securityUtil.getToken(user.getUsername(), true);
        // 生成code 5分钟内有效
        String code = IdUtil.simpleUUID();
        // 存入用户及clientId信息
        redisTemplate.set(OAuthConstant.OAUTH_CODE_PRE + code,
                new Gson().toJson(new Oauth2TokenInfoVo(clientId, user.getUsername())), 5L, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        map.put("redirect_uri", redirect_uri);
        map.put("state", state);
        map.put("accessToken", accessToken);
        // 记录日志
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new SecurityUserDetails(new SysUser().setUsername(user.getUsername())), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ApiOperation(value = "获取access_token令牌")
    public Result<Object> token(@ApiParam("授权类型") @RequestParam String grantType,
                                @ApiParam("客户端id") @RequestParam String clientId,
                                @ApiParam("客户端秘钥") @RequestParam String clientSecret,
                                @ApiParam("认证返回的code") @RequestParam(required = false) String code,
                                @ApiParam("刷新token") @RequestParam(required = false) String refreshToken,
                                @ApiParam("成功授权后回调地址") @RequestParam(required = false) String redirect_uri) {

        SysOauthClient client = getClient(clientId);

        // 判断clientSecret
        if (!client.getClientSecret().equals(clientSecret)) {
            return ResultUtil.error("client_secret不正确");
        }
        Oauth2TokenInfoVo tokenInfo = null;
        if (OAuthConstant.AUTHORIZATION_CODE.equals(grantType)) {
            // 判断回调地址
            if (!client.getRedirectUri().equals(redirect_uri)) {
                return ResultUtil.error("回调地址redirect_uri不正确");
            }
            // 判断code 获取用户信息
            String codeValue = redisTemplate.get(OAuthConstant.OAUTH_CODE_PRE + code);
            if (StrUtil.isBlank(codeValue)) {
                return ResultUtil.error("code已过期");
            }
            tokenInfo = new Gson().fromJson(codeValue, Oauth2TokenInfoVo.class);
            if (!tokenInfo.getClientId().equals(clientId)) {
                return ResultUtil.error("code不正确");
            }
        } else if (OAuthConstant.REFRESH_TOKEN.equals(grantType)) {
            // 从refreshToken中获取用户信息
            String refreshTokenValue = redisTemplate.get(OAuthConstant.OAUTH_TOKEN_INFO_PRE + refreshToken);
            if (StrUtil.isBlank(refreshTokenValue)) {
                return ResultUtil.error("refresh_token已过期");
            }
            tokenInfo = new Gson().fromJson(refreshTokenValue, Oauth2TokenInfoVo.class);
            if (!tokenInfo.getClientId().equals(clientId)) {
                return ResultUtil.error("refresh_token不正确");
            }
        } else {
            return ResultUtil.error("授权类型grant_type不正确");
        }

        String token = null, refreshTokenTmp = null;
        Long expiresIn = null;
        String tokenKey = OAuthConstant.OAUTH_TOKEN_PRE + tokenInfo.getUsername() + "::" + clientId,
                refreshKey = OAuthConstant.OAUTH_REFRESH_TOKEN_PRE + tokenInfo.getUsername() + "::" + clientId;
        if (OAuthConstant.AUTHORIZATION_CODE.equals(grantType)) {
            // 生成token模式
            String oldToken = redisTemplate.get(tokenKey);
            String oldRefreshToken = redisTemplate.get(refreshKey);
            if (StrUtil.isNotBlank(oldToken) && StrUtil.isNotBlank(oldRefreshToken)) {
                // 旧token
                token = oldToken;
                refreshToken = oldRefreshToken;
                expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            } else {
                // 新生成 30天过期
                String newToken = IdUtil.simpleUUID();
                String newRefreshToken = IdUtil.simpleUUID();
                redisTemplate.set(tokenKey, newToken, 30L, TimeUnit.DAYS);
                redisTemplate.set(refreshKey, newRefreshToken, 30L, TimeUnit.DAYS);
                // 新token中存入用户信息
                redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
                redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newRefreshToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
                token = newToken;
                refreshToken = newRefreshToken;
                expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            }
        } else if (OAuthConstant.REFRESH_TOKEN.equals(grantType)) {
            // 刷新token模式 生成新token 30天过期
            String newToken = IdUtil.simpleUUID();
            String newRefreshToken = IdUtil.simpleUUID();
            redisTemplate.set(tokenKey, newToken, 30L, TimeUnit.DAYS);
            redisTemplate.set(refreshKey, newRefreshToken, 30L, TimeUnit.DAYS);
            // 新token中存入用户信息
            redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
            redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newRefreshToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
            token = newToken;
            refreshToken = newRefreshToken;
            expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            // 旧refreshToken过期
            redisTemplate.delete(OAuthConstant.OAUTH_TOKEN_INFO_PRE + refreshToken);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("access_token", token);
        map.put("expires_in", expiresIn);
        map.put("refresh_token", refreshToken);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.POST)
    @ApiOperation(value = "已认证过获取code/单点登录实现")
    public Result<Object> authorized(@ApiParam("客户端id") @RequestParam String clientId,
                                     @ApiParam("成功授权后回调地址") @RequestParam String redirect_uri,
                                     @ApiParam("客户端状态值") @RequestParam String state) {

        SysOauthClient client = getClient(clientId);

        // 判断回调地址
        if (!client.getRedirectUri().equals(redirect_uri)) {
            return ResultUtil.error("回调地址redirect_uri不正确");
        }
        SysUser user = securityUtil.getCurrUser();
        // 生成code 5分钟内有效
        String code = IdUtil.simpleUUID();
        redisTemplate.set(OAuthConstant.OAUTH_CODE_PRE + code,
                new Gson().toJson(new Oauth2TokenInfoVo(clientId, user.getUsername())), 5L, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        map.put("redirect_uri", redirect_uri);
        map.put("state", state);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录（内部信任站点使用）")
    public Result<Object> logout() {

        SysUser user = securityUtil.getCurrUser();

        // 删除当前用户登录accessToken
        String token = redisTemplate.get(SecurityConstant.USER_TOKEN + user.getUsername());
        redisTemplate.delete(token);
        redisTemplate.delete(SecurityConstant.USER_TOKEN + user.getUsername());
        // 删除当前用户授权第三方应用的access_token
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_TOKEN_PRE + user.getUsername() + "::*");
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_REFRESH_TOKEN_PRE + user.getUsername() + "::*");
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息")
    public Result<Object> user(@ApiParam("令牌") @RequestParam String access_token) {

        String tokenValue = redisTemplate.get(OAuthConstant.OAUTH_TOKEN_INFO_PRE + access_token);
        if (StrUtil.isBlank(tokenValue)) {
            return ResultUtil.error("access_token已过期失效");
        }
        Oauth2TokenInfoVo tokenInfo = new Gson().fromJson(tokenValue, Oauth2TokenInfoVo.class);
        SysUser user = userService.findByUsername(tokenInfo.getUsername());
        if (user == null) {
            return ResultUtil.error("用户信息不存在");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", tokenInfo.getUsername());
        map.put("avatar", user.getAvatar());
        return ResultUtil.data(map);
    }

    private SysOauthClient getClient(String clientId) {

        SysOauthClient client = sysOauthClientDetailsService.findByCilentId(clientId);
        if (client == null) {
            throw new HytException("客户端client_id不存在");
        }
        return client;
    }

}
