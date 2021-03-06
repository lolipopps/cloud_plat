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
 * ???????????????
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/client")
@Api(value = "sys_client", tags = "?????????????????????")
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
    @ApiOperation(value = "?????????????????????")
    public Result<Page<SysOauthClient>> getByCondition(SysOauthClient client,
                                                       SearchVo searchVo,
                                                       PageVo pageVo) {

        Page<SysOauthClient> page = sysOauthClientDetailsService.findByCondition(client, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<SysOauthClient>>().setData(page);
    }

    @RequestMapping(value = "/getSecretKey", method = RequestMethod.GET)
    @ApiOperation(value = "????????????secretKey")
    public Result<String> getSecretKey() {

        String secretKey = IdUtil.simpleUUID();
        return new ResultUtil<String>().setData(secretKey);
    }

    @RequestMapping(value = "/info/{clientId}", method = RequestMethod.GET)
    @ApiOperation(value = "??????????????????")
    public Result<Object> info(@ApiParam("?????????id") @PathVariable String clientId) {

        SysOauthClient client = getClient(clientId);

        Map<String, Object> map = new HashMap<>(16);
        map.put("name", client.getName());
        map.put("homeUri", client.getHomeUri());
        map.put("autoApprove", client.getAutoApprove());
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ApiOperation(value = "????????????code")
    @PlatLogAo(description = "??????????????????", type = LogTypeEnum.LOGIN)
    public Result<Object> authorize(@ApiParam("?????????") @RequestParam String username,
                                    @ApiParam("??????") @RequestParam String password,
                                    @ApiParam("?????????id") @RequestParam String clientId,
                                    @ApiParam("???????????????????????????") @RequestParam String redirect_uri,
                                    @ApiParam("???????????????code") @RequestParam(required = false, defaultValue = "code") String response_type,
                                    @ApiParam("??????????????????") @RequestParam String state) {

        SysOauthClient client = getClient(clientId);

        if (!client.getRedirectUri().equals(redirect_uri)) {
            return ResultUtil.error("????????????redirect_uri?????????");
        }
        // ????????????
        SysUser user;
        if (NameUtil.mobile(username)) {
            user = userService.findByMobile(username);
        } else if (NameUtil.email(username)) {
            user = userService.findByEmail(username);
        } else {
            user = userService.findByUsername(username);
        }
        if (user == null) {
            return ResultUtil.error("??????????????????");
        }
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResultUtil.error("?????????????????????");
        }
        String accessToken = securityUtil.getToken(user.getUsername(), true);
        // ??????code 5???????????????
        String code = IdUtil.simpleUUID();
        // ???????????????clientId??????
        redisTemplate.set(OAuthConstant.OAUTH_CODE_PRE + code,
                new Gson().toJson(new Oauth2TokenInfoVo(clientId, user.getUsername())), 5L, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        map.put("redirect_uri", redirect_uri);
        map.put("state", state);
        map.put("accessToken", accessToken);
        // ????????????
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new SecurityUserDetails(new SysUser().setUsername(user.getUsername())), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ApiOperation(value = "??????access_token??????")
    public Result<Object> token(@ApiParam("????????????") @RequestParam String grantType,
                                @ApiParam("?????????id") @RequestParam String clientId,
                                @ApiParam("???????????????") @RequestParam String clientSecret,
                                @ApiParam("???????????????code") @RequestParam(required = false) String code,
                                @ApiParam("??????token") @RequestParam(required = false) String refreshToken,
                                @ApiParam("???????????????????????????") @RequestParam(required = false) String redirect_uri) {

        SysOauthClient client = getClient(clientId);

        // ??????clientSecret
        if (!client.getClientSecret().equals(clientSecret)) {
            return ResultUtil.error("client_secret?????????");
        }
        Oauth2TokenInfoVo tokenInfo = null;
        if (OAuthConstant.AUTHORIZATION_CODE.equals(grantType)) {
            // ??????????????????
            if (!client.getRedirectUri().equals(redirect_uri)) {
                return ResultUtil.error("????????????redirect_uri?????????");
            }
            // ??????code ??????????????????
            String codeValue = redisTemplate.get(OAuthConstant.OAUTH_CODE_PRE + code);
            if (StrUtil.isBlank(codeValue)) {
                return ResultUtil.error("code?????????");
            }
            tokenInfo = new Gson().fromJson(codeValue, Oauth2TokenInfoVo.class);
            if (!tokenInfo.getClientId().equals(clientId)) {
                return ResultUtil.error("code?????????");
            }
        } else if (OAuthConstant.REFRESH_TOKEN.equals(grantType)) {
            // ???refreshToken?????????????????????
            String refreshTokenValue = redisTemplate.get(OAuthConstant.OAUTH_TOKEN_INFO_PRE + refreshToken);
            if (StrUtil.isBlank(refreshTokenValue)) {
                return ResultUtil.error("refresh_token?????????");
            }
            tokenInfo = new Gson().fromJson(refreshTokenValue, Oauth2TokenInfoVo.class);
            if (!tokenInfo.getClientId().equals(clientId)) {
                return ResultUtil.error("refresh_token?????????");
            }
        } else {
            return ResultUtil.error("????????????grant_type?????????");
        }

        String token = null, refreshTokenTmp = null;
        Long expiresIn = null;
        String tokenKey = OAuthConstant.OAUTH_TOKEN_PRE + tokenInfo.getUsername() + "::" + clientId,
                refreshKey = OAuthConstant.OAUTH_REFRESH_TOKEN_PRE + tokenInfo.getUsername() + "::" + clientId;
        if (OAuthConstant.AUTHORIZATION_CODE.equals(grantType)) {
            // ??????token??????
            String oldToken = redisTemplate.get(tokenKey);
            String oldRefreshToken = redisTemplate.get(refreshKey);
            if (StrUtil.isNotBlank(oldToken) && StrUtil.isNotBlank(oldRefreshToken)) {
                // ???token
                token = oldToken;
                refreshToken = oldRefreshToken;
                expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            } else {
                // ????????? 30?????????
                String newToken = IdUtil.simpleUUID();
                String newRefreshToken = IdUtil.simpleUUID();
                redisTemplate.set(tokenKey, newToken, 30L, TimeUnit.DAYS);
                redisTemplate.set(refreshKey, newRefreshToken, 30L, TimeUnit.DAYS);
                // ???token?????????????????????
                redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
                redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newRefreshToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
                token = newToken;
                refreshToken = newRefreshToken;
                expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            }
        } else if (OAuthConstant.REFRESH_TOKEN.equals(grantType)) {
            // ??????token?????? ?????????token 30?????????
            String newToken = IdUtil.simpleUUID();
            String newRefreshToken = IdUtil.simpleUUID();
            redisTemplate.set(tokenKey, newToken, 30L, TimeUnit.DAYS);
            redisTemplate.set(refreshKey, newRefreshToken, 30L, TimeUnit.DAYS);
            // ???token?????????????????????
            redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
            redisTemplate.set(OAuthConstant.OAUTH_TOKEN_INFO_PRE + newRefreshToken, new Gson().toJson(tokenInfo), 30L, TimeUnit.DAYS);
            token = newToken;
            refreshToken = newRefreshToken;
            expiresIn = redisTemplate.getExpire(OAuthConstant.OAUTH_TOKEN_INFO_PRE + token, TimeUnit.SECONDS);
            // ???refreshToken??????
            redisTemplate.delete(OAuthConstant.OAUTH_TOKEN_INFO_PRE + refreshToken);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("access_token", token);
        map.put("expires_in", expiresIn);
        map.put("refresh_token", refreshToken);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/authorized", method = RequestMethod.POST)
    @ApiOperation(value = "??????????????????code/??????????????????")
    public Result<Object> authorized(@ApiParam("?????????id") @RequestParam String clientId,
                                     @ApiParam("???????????????????????????") @RequestParam String redirect_uri,
                                     @ApiParam("??????????????????") @RequestParam String state) {

        SysOauthClient client = getClient(clientId);

        // ??????????????????
        if (!client.getRedirectUri().equals(redirect_uri)) {
            return ResultUtil.error("????????????redirect_uri?????????");
        }
        SysUser user = securityUtil.getCurrUser();
        // ??????code 5???????????????
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
    @ApiOperation(value = "??????????????????????????????????????????")
    public Result<Object> logout() {

        SysUser user = securityUtil.getCurrUser();

        // ????????????????????????accessToken
        String token = redisTemplate.get(SecurityConstant.USER_TOKEN + user.getUsername());
        redisTemplate.delete(token);
        redisTemplate.delete(SecurityConstant.USER_TOKEN + user.getUsername());
        // ??????????????????????????????????????????access_token
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_TOKEN_PRE + user.getUsername() + "::*");
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_REFRESH_TOKEN_PRE + user.getUsername() + "::*");
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "??????????????????")
    public Result<Object> user(@ApiParam("??????") @RequestParam String access_token) {

        String tokenValue = redisTemplate.get(OAuthConstant.OAUTH_TOKEN_INFO_PRE + access_token);
        if (StrUtil.isBlank(tokenValue)) {
            return ResultUtil.error("access_token???????????????");
        }
        Oauth2TokenInfoVo tokenInfo = new Gson().fromJson(tokenValue, Oauth2TokenInfoVo.class);
        SysUser user = userService.findByUsername(tokenInfo.getUsername());
        if (user == null) {
            return ResultUtil.error("?????????????????????");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", tokenInfo.getUsername());
        map.put("avatar", user.getAvatar());
        return ResultUtil.data(map);
    }

    private SysOauthClient getClient(String clientId) {

        SysOauthClient client = sysOauthClientDetailsService.findByCilentId(clientId);
        if (client == null) {
            throw new HytException("?????????client_id?????????");
        }
        return client;
    }

}
