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
 * @Class com.pig4cloud.pig.admin.api.entity SysRoleDept
 * @Description <类描述>
 * @author hyt
 * @create  2021/8/21 17:51
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role_dept")
@TableName("sys_role_dept")
@ApiModel(value = "角色部门")
public class SysRoleDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "部门id")
    private Long deptId;
}