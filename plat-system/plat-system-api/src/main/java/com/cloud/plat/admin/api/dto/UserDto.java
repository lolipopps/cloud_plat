package com.cloud.plat.admin.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.common.core.util.NameUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 19:05
 * @contact 269016084@qq.com
 *
 **/
@Data
@ApiModel(value = "用户")
public class UserDto {


    @ApiModelProperty(value = "登录名")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = NameUtil.regUsername, message = "登录账号不能包含特殊字符且长度不能>16")
    private String username;


    @ApiModelProperty(value = "密码")
    @Pattern(regexp = NameUtil.regPassword, message = "密码为字母加数字组合 6-20位")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "手机")
    @Pattern(regexp = NameUtil.regMobile, message = "11位手机号格式不正确")
    @ColumnTransformer(read = "AES_DECRYPT(UNHEX(mobile),'freight')", write = "HEX(AES_ENCRYPT(?, 'freight'))")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像")
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = CommonConstant.USER_PRI_TYPE_NORMAL;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    @ApiModelProperty(value = "所属部门id")
    private Long departmentId;

    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birth;
}

