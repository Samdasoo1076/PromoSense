package com.skbroadband.doms.global.component.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.log
 * @File : DomsLoggingFilter
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE+10)
@Component
@WebFilter(urlPatterns = "/*")
public class DomsLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("requestId", new StringBuffer("#").append(UUID.randomUUID().toString(), 0, 7).append(" -").toString() );  // RequestId를 키로 사용

        final long startTime = System.currentTimeMillis();
        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        CachedHttpServletResponse cachedHttpServletResponse = new CachedHttpServletResponse(response);

        logRequest(cachedHttpServletRequest);
        filterChain.doFilter(cachedHttpServletRequest, cachedHttpServletResponse);
        logResponse(startTime, cachedHttpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/assets/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/favicon.ico"),
                new AntPathRequestMatcher("/robots.txt")
        );

        return orRequestMatcher.matches(request);
    }

    private void logRequest(CachedHttpServletRequest request) throws IOException {
        log.info("Request: method={}, uri={}, parameters={}, payload={}, headers={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getAllParameters(),
                IOUtils.toString(request.getInputStream(), request.getCharacterEncoding()),
                request.getAllHeaders());
    }

    private void logResponse(long startTime, CachedHttpServletResponse response) {
        log.info("Response({} ms): status={}, payload={}, headers={}",
                System.currentTimeMillis() - startTime,
                response.getStatus(),
                responsePayload(response),
                response.getAllHeaders());
    }

    private String responsePayload(CachedHttpServletResponse response) {
        if(response.getContentType() != null && response.getContentType().startsWith("text/html")) {
            return "[html source code]";
        }

        return IOUtils.toString(response.getContentAsByteArray(),
                response.getCharacterEncoding());
    }
}
