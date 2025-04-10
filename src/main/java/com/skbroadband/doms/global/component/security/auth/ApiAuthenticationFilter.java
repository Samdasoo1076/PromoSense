package com.skbroadband.doms.global.component.security.auth;

import com.skbroadband.doms.global.constant.CustomerType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security.auth
 * @File : ApiAuthenticationFilter
 * @Program :
 * @Date : 2023-02-13
 * @Comment :
 */
public class ApiAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessKey = request.getParameter("DOMS_KEY");
        String origin = request.getHeader("Origin");
        if(StringUtils.isEmpty(origin) || StringUtils.isEmpty(accessKey)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String directAccessKey = Objects.requireNonNull(RequestContextUtils.findWebApplicationContext(request))
                .getEnvironment().getProperty("application.api.access-key.bdirectshop");

        final String btvcableAccessKey = Objects.requireNonNull(RequestContextUtils.findWebApplicationContext(request))
                .getEnvironment().getProperty("application.api.access-key.btvcable");

        final String bworldAccessKey = Objects.requireNonNull(RequestContextUtils.findWebApplicationContext(request))
                .getEnvironment().getProperty("application.api.access-key.bworld");

        final String testAccessKey = Objects.requireNonNull(RequestContextUtils.findWebApplicationContext(request))
                .getEnvironment().getProperty("application.api.access-key.test");

        origin = "bdirectshop";


        Authentication authentication = null;
        if(origin.contains("bdirectshop") && directAccessKey.equals(accessKey)) {
            authentication = new UsernamePasswordAuthenticationToken(CustomerType.BDIRECTSHOP,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_BDIRECTSHOP")));
        } else if(origin.contains("btvcable") && btvcableAccessKey.equals(accessKey)) {
            authentication = new UsernamePasswordAuthenticationToken(CustomerType.BTVCABLE,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_BTVCABLE")));
        } else if(origin.contains("bworld") && bworldAccessKey.equals(accessKey)) {
            authentication = new UsernamePasswordAuthenticationToken(CustomerType.BWORLD,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_BWORLD")));
        } else if(origin.contains("test") && testAccessKey.equals(accessKey)) {
            authentication = new UsernamePasswordAuthenticationToken(CustomerType.TEST,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_TEST")));
        }

        if(!Objects.isNull(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
