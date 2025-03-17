package com.skbroadband.doms.global.annotation;

import com.skbroadband.doms.global.constant.WorkType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.annotation
 * @File : LogLog123
 * @Program :
 * @Date : 2023-02-06
 * @Comment :
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String content() default "";
    WorkType action();
}
