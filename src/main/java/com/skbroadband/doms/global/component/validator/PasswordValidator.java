package com.skbroadband.doms.global.component.validator;

import com.skbroadband.doms.global.annotation.PasswordConstraint;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.validator
 * @File : PasswordValidator
 * @Program :
 * @Date : 2023-02-14
 * @Comment :
 */
public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$")) {
            if(StringUtils.isEmpty(context.getDefaultConstraintMessageTemplate())) {
                context.disableDefaultConstraintViolation();
            }
            context.buildConstraintViolationWithTemplate( "8~16자리 영문 대소문자, 숫자, 특수문자 중 3가지 이상 조합으로 만들어주세요." ).addConstraintViolation();

            return false;
        }

        if(value.matches(".*(\\w)\\1\\1.*")) {
            if(StringUtils.isEmpty(context.getDefaultConstraintMessageTemplate())) {
                context.disableDefaultConstraintViolation();
            }
            context.buildConstraintViolationWithTemplate( "3자리 이상 반복되는 영문, 숫자, 특수문자는 비밀번호로 사용할 수 없습니다." ).addConstraintViolation();

            return false;
        }

        return true;
    }
}
