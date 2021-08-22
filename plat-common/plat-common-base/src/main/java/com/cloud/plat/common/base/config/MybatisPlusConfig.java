package com.cloud.plat.common.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 10:35
 * @contact 269016084@qq.com
 *
 **/
@Configuration
@MapperScan({"com.pig4cloud.pig.*.mapper", "com.pig4cloud.pig.*.*.mapper"})
public class MybatisPlusConfig {
    /**
     * 新的分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
