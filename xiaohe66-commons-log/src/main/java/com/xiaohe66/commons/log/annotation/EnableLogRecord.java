package com.xiaohe66.commons.log.annotation;

import com.xiaohe66.commons.log.LogRecordAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaohe
 * @since 2022.05.23 17:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogRecordAutoConfiguration.class)
public @interface EnableLogRecord {

}
