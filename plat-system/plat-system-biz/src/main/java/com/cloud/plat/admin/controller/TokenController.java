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

import com.cloud.plat.admin.api.feign.RemoteTokenService;
import com.cloud.plat.common.core.constant.SecurityConstants;
import com.cloud.plat.common.core.util.R;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:31
 * @contact 269016084@qq.com
 *
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Api(value = "sys_token", tags = "令牌管理模块")
public class TokenController {

	private final RemoteTokenService remoteTokenService;

	/**
	 * 分页token 信息
	 * @param params 参数集
	 * @return token集合
	 */
	@GetMapping("/page")
	public R token(@RequestParam Map<String, Object> params) {
		return remoteTokenService.getTokenPage(params, SecurityConstants.FROM_IN);
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_token_del')")
	public R<Boolean> delete(@PathVariable String id) {
		return remoteTokenService.removeToken(id, SecurityConstants.FROM_IN);
	}

}
