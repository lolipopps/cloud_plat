package com.cloud.plat.admin.api.dto;

import com.cloud.plat.common.core.util.NameUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Data
public class UserMobileDto {

    @ApiModelProperty(value = "登录名")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = NameUtil.regUsername, message = "登录账号不能包含特殊字符且长度不能>16")
    private String username;

    @ApiModelProperty(value = "密码")
    @Pattern(regexp = NameUtil.regPassword, message = "密码为字母加数字组合 6-20位")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "手机")
    @Pattern(regexp = NameUtil.regMobile, message = "11位手机号格式不正确")
    @ColumnTransformer(read = "AES_DECRYPT(UNHEX(mobile),'freight')"
            , write = "HEX(AES_ENCRYPT(?, 'freight'))")
    private String mobile;

}
