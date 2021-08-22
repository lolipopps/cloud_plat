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
import com.cloud.plat.common.core.constant.CommonConstants;
import com.cloud.plat.common.base.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 17:55
 * @contact 269016084@qq.com
 *
 **/
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_menu")
@TableName("sys_menu")
@ApiModel(value = "菜单权限")
public class SysMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单/权限名称")
	private String name;

	@ApiModelProperty(value = "始终显示 默认是")
	private Boolean showAlways = true;

	@ApiModelProperty(value = "层级")
	private Integer level;

	@ApiModelProperty(value = "类型 -1顶部菜单 0页面 1具体操作")
	private Integer type;

	@ApiModelProperty(value = "菜单标题")
	private String title;

	@ApiModelProperty(value = "页面路径/资源链接url")
	private String path;

	@ApiModelProperty(value = "前端组件")
	private String component;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "按钮权限类型")
	private String buttonType;

	@ApiModelProperty(value = "是否为站内菜单 默认true")
	private Boolean isMenu = true;

	@ApiModelProperty(value = "网页链接")
	private String url;

	@ApiModelProperty(value = "说明备注")
	private String description;

	@ApiModelProperty(value = "父id")
	private Long parentId;

	@ApiModelProperty(value = "排序值")
	@Column(precision = 10, scale = 2)
	private BigDecimal sortOrder;

	@ApiModelProperty(value = "是否启用 0启用 -1禁用")
	private Integer status = CommonConstants.STATUS_NORMAL;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "子菜单/权限")
	private List<SysMenu> children;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "页面拥有的权限类型")
	private List<String> permTypes;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "节点展开 前端所需")
	private Boolean expand = true;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "是否勾选 前端所需")
	private Boolean checked = false;

	@Transient
	@TableField(exist = false)
	@ApiModelProperty(value = "是否选中 前端所需")
	private Boolean selected = false;



}
