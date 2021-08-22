package com.cloud.plat.common.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:42
 * @contact 269016084@qq.com
 *
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    private List<String> image = new ArrayList<>();

    private List<String> sms = new ArrayList<>();

    private List<String> vaptcha = new ArrayList<>();

    private List<String> email = new ArrayList<>();
}
