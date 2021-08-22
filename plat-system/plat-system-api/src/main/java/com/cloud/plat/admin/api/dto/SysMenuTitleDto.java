package com.cloud.plat.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:00
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
public class SysMenuTitleDto {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;
}
