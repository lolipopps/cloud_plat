package com.cloud.plat.admin.api.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.plat.admin.api.constant.CommonConstant;
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
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 19:19
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_dept_header")
@TableName("sys_dept_header")
@ApiModel(value = "部门负责人")
public class SysDeptHeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联部门id")
    private Long deptId;

    @ApiModelProperty(value = "关联部门负责人")
    private Long userId;

    @ApiModelProperty(value = "负责人类型 默认0主要 1副职")
    private Integer type = CommonConstant.HEADER_TYPE_MAIN;
}