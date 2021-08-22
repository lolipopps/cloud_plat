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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.plat.admin.api.dto.SysRoleDto;
import com.cloud.plat.admin.api.dto.SysTitleMenuDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cloud.plat.admin.api.constant.CommonConstant;

import com.cloud.plat.common.core.util.NameUtil;
import com.cloud.plat.common.base.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 17:58
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_user")
@TableName("sys_user")
@ApiModel(value = "用户")
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "登录名")
	@Column(unique = true, nullable = false)
	@Pattern(regexp = NameUtil.regUsername, message = "登录账号不能包含特殊字符且长度不能>16")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "姓名")
	private String nickname;

	@ApiModelProperty(value = "手机")
	@Pattern(regexp = NameUtil.regMobile, message = "11位手机号格式不正确")
	@ColumnTransformer(read = "AES_DECRYPT(UNHEX(mobile),'freight')", write = "HEX(AES_ENCRYPT(?, 'freight'))")
	private String mobile;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "用户头像")
	private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

	@ApiModelProperty(value = "用户类型 0普通用户权限 1管理员")
	private Integer type = CommonConstant.USER_PRI_TYPE_NORMAL;

	@ApiModelProperty(value = "状态 默认0正常 -1拉黑")
	private Integer status = CommonConstant.USER_STATUS_NORMAL;

	@ApiModelProperty(value = "所属部门id")
	private Long deptId;

	@ApiModelProperty(value = "所属部门名称")
	private String deptTitle;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "生日")
	private Date birth;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "用户拥有角色")
	private List<SysRoleDto> roles;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "用户拥有的权限")
	private List<SysTitleMenuDto> permissions;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "导入数据时使用")
	private Integer defaultRole;


}
