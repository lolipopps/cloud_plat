package com.cloud.plat.admin.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 23:19
 * @contact 269016084@qq.com
 *
 **/
@Data
@AllArgsConstructor
public class RedisVo {

    private String key;

    private String value;

    private Long expireTime;
}
