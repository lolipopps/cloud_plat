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

package com.cloud.plat.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:58
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
public class SysRoleDto {

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "角色名 以ROLE_开头")
	private String name;

	@ApiModelProperty(value = "备注")
	private String description;
}
