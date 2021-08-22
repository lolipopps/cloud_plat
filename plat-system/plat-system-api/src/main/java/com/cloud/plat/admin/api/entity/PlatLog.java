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
import com.cloud.plat.common.core.util.ObjectUtil;
import com.cloud.plat.common.base.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 17:58
 * @contact 269016084@qq.com
 **/
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_log")
@TableName("sys_log")
@ApiModel(value = "日志")
public class PlatLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "方法操作名称")
    private String name;

    @ApiModelProperty(value = "日志类型 0登陆日志 1操作日志")
    private String logType;

    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    @ApiModelProperty(value = "请求类型")
    private String requestType;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "请求用户手机号")
    private String username;

    @ApiModelProperty(value = "ip信息")
    private String ipInfo;


    /**
     * 服务ID
     */
    @ApiModelProperty(value = "应用标识")
    private String serviceId;

    @ApiModelProperty(value = "花费时间")
    private Long costTime;

    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    /**
     * 转换请求参数为Json
     *
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {

        this.requestParam = ObjectUtil.mapToString(paramMap);
    }


}
