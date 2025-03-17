package com.skbroadband.doms.global.annotation;

import com.skbroadband.doms.global.component.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.annotation
 * @File : PasswordCheck
 * @Program :
 * @Date : 2023-02-14
 * @Comment :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordConstraint {
    String message() default "";
    Class[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
