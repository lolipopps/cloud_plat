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

package com.cloud.plat.common.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.cloud.plat.admin.api.entity.PlatLog;
import com.cloud.plat.common.log.enums.LogTypeEnum;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 系统日志工具类
 *
 * @author L.cm
 */
@UtilityClass
public class PlatLogUtil {

    public PlatLog getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        PlatLog sysLog = new PlatLog();
        sysLog.setCreateBy(Objects.requireNonNull(getUsername()));
        sysLog.setUpdateBy(Objects.requireNonNull(getUsername()));
        sysLog.setLogType(LogTypeEnum.NORMAL.getType());
        sysLog.setIpInfo(ServletUtil.getClientIP(request));
        sysLog.setRequestUrl(URLUtil.getPath(request.getRequestURI()));
        sysLog.setName(request.getMethod());
        sysLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        sysLog.setRequestParam(HttpUtil.toParams(request.getParameterMap()));
        sysLog.setServiceId(getClientId(request));
        return sysLog;
    }

    /**
     * 获取客户端
     *
     * @return clientId
     */
    private String getClientId(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            BasicAuthenticationConverter basicAuthenticationConverter = new BasicAuthenticationConverter();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = basicAuthenticationConverter
                    .convert(request);
            if (usernamePasswordAuthenticationToken != null) {
                return usernamePasswordAuthenticationToken.getName();
            }
        }
        return null;
    }

    /**
     * 获取用户名称
     *
     * @return username
     */
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

}