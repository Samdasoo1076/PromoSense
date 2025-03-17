package com.skbroadband.doms.global.component.security.auth;

import com.skbroadband.doms.global.component.security.Crypto;
import com.skbroadband.doms.global.utils.CommUtils;
import com.skbroadband.doms.web.entity.AdminGroup;
import com.skbroadband.doms.web.entity.AdminInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security.auth
 * @File : Account
 * @Program :
 * @Date : 2023-01-09
 * @Comment :
 */

public class Account implements UserDetails {
    private final AdminInfo user;
    private final Crypto crypto = CommUtils.getBean(Crypto.class);

    private Collection<? extends GrantedAuthority> authorities;

    public Account(AdminInfo user) {
        this.user = user;

        Optional.ofNullable(user.getGroupNo())
                .filter(group -> "Y".equals(group.getUseTf()) && "N".equals(group.getDelTf()))
                .map(AdminGroup::getGroupName)
                .ifPresent(grant ->
                        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(grant)));
    }

    public Long getAdmNo() {
        return user.getId();
    }

    public String getAdmName() {
        return user.getAdmName();
    }

    public String getAdmId() {
        return user.getAdmId();
    }

    public Long getGroupNo() {
        return Objects.isNull(user.getGroupNo())?null:user.getGroupNo().getId();
    }

    public String getEmail() {
        try {
            return  crypto.descrypt(user.getAdmEmail());
        } catch (Exception e) {
            return "";
        }
    }

    public String getPhone() {
        try {
            return  crypto.descrypt(user.getAdmHphone());
        } catch (Exception e) {
            return "";
        }
    }

    public String getApproval() {
        return user.getAdmFlag();
    }

    public String getAdminInfo() {
        return user.getAdmInfo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswd();
    }


    @Override
    public String getUsername() {
        return String.valueOf(user.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getLoginFailCnt() < 5
                && "Y".equals(user.getUseTf())
                && "N".equals(user.getDelTf())
                && "1".equals(user.getAdmFlag());
    }
}
