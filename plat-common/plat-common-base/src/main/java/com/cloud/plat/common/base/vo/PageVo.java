package com.cloud.plat.common.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 18:12
 * @contact 269016084@qq.com
 *
 **/
@Data
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页号")
    private int pageNumber;

    @ApiModelProperty(value = "页面大小")
    private int pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;
}
