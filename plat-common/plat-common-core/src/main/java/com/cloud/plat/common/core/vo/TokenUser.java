package com.cloud.plat.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:47
 * @contact 269016084@qq.com
 *
 **/
@Data
@AllArgsConstructor
public class TokenUser implements Serializable {

    private String username;

    private List<String> permissions;

    private Boolean saveLogin;
}
