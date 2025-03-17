package com.skbroadband.doms.global.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.annotation
 * @File : CustomerType
 * @Program :
 * @Date : 2023-03-08
 * @Comment :
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : #this")
public @interface Customer {
}
