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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:11
 * @contact 269016084@qq.com
 *
 **/
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_oauth_client")
@TableName("sys_oauth_client")
@ApiModel(value = "客户端权限认证")
public class SysOauthClient extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "网站名称")
	private String name;

	@ApiModelProperty(value = "客户id")
	private String clientId;

	@ApiModelProperty(value = "网站主页")
	private String homeUri;

	@ApiModelProperty(value = "秘钥")
	private String clientSecret;

	@ApiModelProperty(value = "成功授权后的回调地址")
	private String redirectUri;

	@ApiModelProperty(value = "自动授权 默认false")
	private Boolean autoApprove = false;

}
