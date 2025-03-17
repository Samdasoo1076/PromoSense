package com.skbroadband.doms.global.component;

import com.skbroadband.doms.global.component.security.auth.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : LoginUserAuditorAware
 * @Program :
 * @Date : 2023-01-06
 * @Comment :
 */
@Component
public class LoginUserAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(((Account)authentication.getPrincipal()).getAdmNo());
    }
}
