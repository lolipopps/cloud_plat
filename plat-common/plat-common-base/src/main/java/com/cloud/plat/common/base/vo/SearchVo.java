package com.cloud.plat.common.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @describe x
 */
@Data
public class SearchVo implements Serializable {

    @ApiModelProperty(value = "起始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;
}
