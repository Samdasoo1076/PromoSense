package com.skbroadband.doms.global.component.security.permission;

import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.web.dto.AdminMenuRightDto;
import com.skbroadband.doms.web.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security
 * @File : DomsPermissionEvaluator
 * @Program :
 * @Date : 2022-12-28
 * @Comment :
 */
@Slf4j
public class DomsPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private AuthService authService;
    @Autowired
    private CacheManager ehCacheCacheManager;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated()) {
            return false;
        }

        String aclCode = request.getParameter("acl");
        Long groupNo = ((Account)authentication.getPrincipal()).getGroupNo();

        if(!Objects.isNull(targetDomainObject) && !StringUtils.isEmpty(targetDomainObject.toString())) {
            aclCode = targetDomainObject.toString();
        }

        if(StringUtils.isEmpty(aclCode) || Objects.isNull(groupNo)) {
            return false;
        }

        AdminMenuRightDto adminMenuRightDto = getAclCache(aclCode, groupNo);
        String[] permissions = String.valueOf(permission).split(",");
        return Arrays.stream(permissions).anyMatch(s -> {
            switch (s.trim()) {
                case "read":
                    return "Y".equals(adminMenuRightDto.getReadFlag());
                case "write":
                    return "Y".equals(adminMenuRightDto.getRegFlag());
                case "update":
                    return "Y".equals(adminMenuRightDto.getUpdFlag());
                case "delete":
                    return "Y".equals(adminMenuRightDto.getDelFlag());
                case "download":
                    return "Y".equals(adminMenuRightDto.getFileFlag());
                default:
                    return false;
            }
        });
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    /**
     * cache 등록
     *
     * @param aclCode
     * @param groupNo
     * @return
     */
    private AdminMenuRightDto getAclCache(String aclCode, Long groupNo) {
        String key = aclCode + "_" + groupNo;
        Cache cache = Objects.requireNonNull(ehCacheCacheManager.getCache("aclCache"));

        return Optional.ofNullable(cache.get(key, AdminMenuRightDto.class))
                .orElseGet(() -> {
                    Optional<AdminMenuRightDto> optAdminMenuRightDto = authService.findAccessControl(aclCode, groupNo);
                    optAdminMenuRightDto.ifPresent(adminMenuRightDto -> cache.put(key, adminMenuRightDto));

                    return optAdminMenuRightDto.orElse(AdminMenuRightDto.builder().build());
                });
    }
}
