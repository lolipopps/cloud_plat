package com.cloud.plat.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:39
 * @contact 269016084@qq.com
 *
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "hyt.app-token")
public class AppTokenProperties {

    /**
     * 单平台登陆
     */
    private Boolean spl = true;

    /**
     * token过期时间（天）
     */
    private Integer tokenExpireTime = 30;
}
