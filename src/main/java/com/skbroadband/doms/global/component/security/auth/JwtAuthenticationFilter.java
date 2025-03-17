package com.skbroadband.doms.global.component.security.auth;

import com.skbroadband.doms.global.utils.ThrowingFunction;
import com.skbroadband.doms.web.entity.AccessTokenWhiteList;
import com.skbroadband.doms.web.service.AcceptService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security
 * @File : JwtAuthenticationFilter
 * @Program :
 * @Date : 2023-01-06
 * @Comment :
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtWhiteListService jwtWhiteListService;
    private final AcceptService acceptService;

    @SneakyThrows
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String admNo;
        final String token;
        final String tokenName = Objects.requireNonNull(RequestContextUtils.findWebApplicationContext(request))
                .getEnvironment().getProperty("application.jwt.token-name");
        
        if(Objects.isNull(request.getCookies())) {
            token = null;
        } else {
            token = Stream.of(request.getCookies())
                    .filter(cookie -> Objects.requireNonNull(tokenName).equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .map(ThrowingFunction.unchecked(value -> URLDecoder.decode(value, "UTF-8")))
                    .findFirst()
                    .orElse(null);
        }

        /* token 유무 체크 */
        if(!StringUtils.hasText(token)) {
            chain.doFilter(request, response);
            return;
        }

        if (jwtService.isTokenExpired(token)) {
            Cookie tokenCookie = new Cookie(tokenName, null);
            tokenCookie.setMaxAge(0);
            response.addCookie(tokenCookie);

            Cookie menuExpandCoolie = new Cookie("IS_MENU_EXPAND", null);
            menuExpandCoolie.setMaxAge(0);
            response.addCookie(menuExpandCoolie);

            chain.doFilter(request, response);
            return;
        }

        admNo = jwtService.extractSubject(token);

        /* white list 검증 */
        Optional<AccessTokenWhiteList> whiteList = jwtWhiteListService.findToken(Long.valueOf(admNo));
        if(whiteList.isPresent()) {
            if(!token.equals(whiteList.get().getAccessToken())) {
                chain.doFilter(request, response);
                return;
            }
        } else {
            chain.doFilter(request, response);
            return;
        }

        if (StringUtils.hasText(admNo) && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!acceptService.chkAcceptIpByUseTfAndDelTf(request.getRemoteAddr(), "Y", "N")) {
                chain.doFilter(request, response);
                return;
            }

            Account userDetails = (Account)userDetailsService.loadUserByUsername(admNo);
            if(!userDetails.isEnabled()) {
                // 쿠기 삭제
                Cookie tokenCookie = new Cookie(tokenName, "");
                tokenCookie.setPath("/");
                tokenCookie.setMaxAge(0);
                response.addCookie(tokenCookie);

                chain.doFilter(request, response);
                return;
            }

            /* token 생성 */
            String newToken = jwtService.generateToken(userDetails);

            /** token 저장 */
            jwtWhiteListService.upateTokens(AccessTokenWhiteList.builder()
                    .admNo(Long.valueOf(admNo))
                    .accessToken(newToken)
                    .issuedDate(jwtService.extractClaim(token, Claims::getIssuedAt).toInstant())
                    .expiresIn(jwtService.extractClaim(token, Claims::getExpiration).toInstant())
                    .build());
            Cookie cookie = new Cookie(tokenName , URLEncoder.encode(newToken, "UTF-8"));
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            /* authentication 생성 */
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
