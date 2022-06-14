package com.xiaohe66.commons.log.annotation;

import com.xiaohe66.commons.log.tool.LogRecordExtend;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author xiaohe
 * @since 2022.05.20 16:58
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {

    String success();

    String fail() default "";

    String type();

    String subType() default "";

    String extra() default "";

    boolean async() default true;

    /**
     * 用于特殊处理的实现类
     */
    Class<? extends LogRecordExtend> extendClass() default LogRecordExtend.class;

}
