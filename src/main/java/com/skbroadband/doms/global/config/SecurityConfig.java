package com.skbroadband.doms.global.config;

import com.skbroadband.doms.global.component.security.auth.ApiAuthenticationFilter;
import com.skbroadband.doms.global.component.security.auth.JwtAuthenticationFilter;
import com.skbroadband.doms.global.component.security.auth.JwtService;
import com.skbroadband.doms.global.component.security.auth.JwtWhiteListService;
import com.skbroadband.doms.global.component.security.permission.DomsPermissionEvaluator;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.utils.ThrowingFunction;
import com.skbroadband.doms.web.entity.AdminLog;
import com.skbroadband.doms.web.repository.AdminLogRepository;
import com.skbroadband.doms.web.service.AcceptService;
import com.skbroadband.doms.web.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.config
 * @File : SecurityConfig
 * @Program :
 * @Date : 2022-12-07
 * @Comment :
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@RequiredArgsConstructor
public class SecurityConfig  extends GlobalMethodSecurityConfiguration {
    private final JwtWhiteListService jwtWhiteListService;
    private final AccountService userDetailsService;
    private final JwtService jwtService;
    private final AcceptService acceptService;
    private final AdminLogRepository adminLogRepository;

    @Value("${application.jwt.token-name}")
    private String tokenName;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator());

        return expressionHandler;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring()
                    .antMatchers(
                            "/resources/**",
                            "/assets/**",
                            "/js/**",
                            "/favicon.ico",
                            "/images/**",
                            "/robots.txt"
                    );
        };
    }

    @Bean
    @Order(value = 0)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter();

        httpSecurity
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().hasAnyRole("API_BDIRECTSHOP", "API_BTVCABLE", "API_BWORLD")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationApiEntryPoint())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationApiEntryPoint())
                .and()
                .addFilterAt(apiAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Order(value = 1)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, jwtWhiteListService, acceptService);

        httpSecurity
                .authorizeRequests()
                .antMatchers("/",
                        "/login",
                        "/signup/**",
                        "/home/index",      // 추가
                        "/home/**",         // 추가
                        "/search/**",
                        "/search/password",
                        "/file/download",
                        "/robots.txt").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
                .antMatchers("/**/list.do", "/**/detail.do").access("hasPermission('', 'read')")
                .antMatchers("/**/write.do").access("hasPermission('', 'write')")
                .antMatchers("/**/update.do").access("hasPermission('', 'update')")
                .antMatchers("/**/delete.do").access("hasPermission('', 'delete')")
                .antMatchers("/**/save.do").access("hasPermission('', 'write,update')")
                .antMatchers("/**/download.do").access("hasPermission('', 'download')")
                .anyRequest().authenticated() // 그 외 모든 리소스를 의미하며 인증 필요
                .and()
                .csrf().disable()
                .logout()
                .addLogoutHandler((request, response, authentication) -> {
                    String token = Stream.of(request.getCookies())
                            .filter(cookie -> Objects.requireNonNull(tokenName).equals(cookie.getName()))
                            .map(Cookie::getValue)
                            .map(ThrowingFunction.unchecked(value -> URLDecoder.decode(value, "UTF-8")))
                            .findFirst()
                            .orElse(null);
                    String admNo = jwtService.extractSubject(token);

                    adminLogRepository.save(AdminLog.builder()
                            .admNo(Long.valueOf(admNo))
                            .logIp(request.getRemoteAddr())
                            .task("로그아웃")
                            .logDate(Instant.now())
                            .taskType(WorkType.Logout.getCode())
                            .build());
                })
                .logoutSuccessUrl("/login") // 로그아웃 성공 URL (기본 값 : "/login?logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
                .deleteCookies("JSESSIONID", "IS_MENU_EXPAND", tokenName) // 로그아웃 시 JSESSIONID 제거
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DomsPermissionEvaluator customPermissionEvaluator() {
        return new DomsPermissionEvaluator();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value());
            request.getRequestDispatcher("/error").forward(request, response);
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationApiEntryPoint() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
            request.getRequestDispatcher("/error").forward(request, response);
        };
    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) ->
                response.sendRedirect("/login");
    }

}
