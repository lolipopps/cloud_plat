package com.cloud.plat.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 17:57
 * @contact 269016084@qq.com
 *
 **/
@Data
@Accessors(chain = true)
public class SysTitleMenuDto {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

}
