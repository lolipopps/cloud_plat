package com.cloud.plat.common.log.annotation;

import com.cloud.plat.common.log.enums.LogSaveType;
import com.cloud.plat.common.log.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 18:14
 * @contact 269016084@qq.com
 *
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlatLogAo {

    /**
     * 日志名称
     * @return
     */
    String description() default "";

    /**
     * 日志名称
     * @return
     */
    LogSaveType saveType() default LogSaveType.MYSQL;

    /**
     * 日志类型
     * @return
     */
    LogTypeEnum type() default LogTypeEnum.NORMAL;
}
