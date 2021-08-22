package com.cloud.plat.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hytma
 */
@Target(ElementType.METHOD)//表示用于标识方法
@Retention(RetentionPolicy.RUNTIME)//表示运行时保留
public @interface LogParam {
    /**
     * 主要是标志日志的描述信息
     * @return
     */
    String note() default "";
}