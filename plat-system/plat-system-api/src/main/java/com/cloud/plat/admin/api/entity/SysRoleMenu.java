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

package com.cloud.plat.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.plat.common.base.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @describe
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role_menu")
@TableName("sys_role_menu")
@ApiModel(value = "角色权限")
public class SysRoleMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色id")
	private Long roleId;

	/**
	 * 菜单ID
	 */
	@ApiModelProperty(value = "菜单id")
	private Long menuId;

}
