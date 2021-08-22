package com.cloud.plat.admin.api.dto;

import com.cloud.plat.common.core.constant.CommonConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 17:57
 * @contact 269016084@qq.com
 *
 **/
@Data
public class SysMenuDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id号")
    private Long id;

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

}
