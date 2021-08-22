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

import com.baomidou.mybatisplus.annotation.*;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.common.base.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 17:54
 * @contact 269016084@qq.com
 *
 **/
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role")
@TableName("sys_role")
@ApiModel(value = "角色")
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色名 以ROLE_开头")
	private String name = CommonConstant.ROLE_USER;;

	@ApiModelProperty(value = "是否为注册默认角色")
	private Boolean defaultRole;

	@ApiModelProperty(value = "数据权限类型 0全部默认 1自定义 2本部门及以下 3本部门 4仅本人")
	private Integer dataType = CommonConstant.DATA_TYPE_ALL;

	@ApiModelProperty(value = "备注")
	private String description;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "拥有权限")
	private List<SysRoleMenu> permissions;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "拥有数据权限")
	private List<SysRoleDept> departments;


}
