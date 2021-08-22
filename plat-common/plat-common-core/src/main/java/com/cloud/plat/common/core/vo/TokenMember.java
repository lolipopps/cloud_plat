package com.cloud.plat.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:46
 * @contact 269016084@qq.com
 *
 **/
@Data
@AllArgsConstructor
public class TokenMember implements Serializable {

    private String username;

    private Integer platform;
}
