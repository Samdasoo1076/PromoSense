package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.Crypto;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.component.security.auth.JwtService;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.CheckedGlobalException;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.service.AcceptService;
import com.skbroadband.doms.web.service.LoginService;
import com.skbroadband.doms.web.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : LoginController
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserDetailsService userDetailsService;
    private final AcceptService acceptService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Crypto crypto;

    @Value("${application.jwt.token-name}")
    private String tokenName;
    @Value("${server.etc-info.address}")
    private String address;

    /**
     * index 페이지
     * @return
     */
    @GetMapping("/")
    public String index(@LoginUser Account user) {
        if(Objects.isNull(user)) {
            return "redirect:/login";
        }
//        return "redirect:/main.view";
        return "redirect:/campaign/list/list.do?acl=C0001";
    }

    /**
     * main 페이지
     * @return
     */
    @GetMapping(value = "/main.view")
    public String main(@LoginUser Account account) {
        return "main";
    }

    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                if("remember".equals(name)) {
                    try {
                        return crypto.descrypt(value);
                    } catch(Exception e) {
                        return "";
                    }
                }
            }
        }
        return "";
    }
    /**
     * login 페이지
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("user",  AdminInfoDto.builder().admId(getCookie(request)).build());

        return "login/login";
    }

    /**
     * login 처리
     *
     * @param adminInfoDto
     * @param remember
     * @param response
     * @return
     * @throws Exception
     */
    @Log(content = "로그인", action = WorkType.Login)
    @PostMapping("/login")
    public String login(@ModelAttribute("user") AdminInfoDto adminInfoDto, String remember,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /* 아이디 기억하기 */
        if("on".equals(remember)) {
            Cookie rememberCookie = new Cookie("remember", crypto.encrypt(adminInfoDto.getAdmId()));
            rememberCookie.setPath("/");
            rememberCookie.setMaxAge(60*60*24*7);
            rememberCookie.setHttpOnly(true);
            rememberCookie.setSecure(true);
            response.addCookie(rememberCookie);
        } else {
            Cookie rememberCookie = new Cookie("remember", crypto.encrypt(adminInfoDto.getAdmId()));
            rememberCookie.setPath("/");
            rememberCookie.setMaxAge(0);
            rememberCookie.setHttpOnly(true);
            rememberCookie.setSecure(true);
            response.addCookie(rememberCookie);
        }

        /* ip 체크 */
        if(!acceptService.chkAcceptIpByUseTfAndDelTf(request.getRemoteAddr(), "Y", "N")) {
            bindingResult.addError(new ObjectError("user", "인증된 IP가 아닙니다."));
        }

        /* id 유효성 체크 */
        if(StringUtils.isEmptyOrWhitespace(adminInfoDto.getAdmId())) {
            bindingResult.addError(new FieldError("user", "admId", "아이디는 필수입니다."));
            return "login/login";
        }
        /* password 유효성 체크 */
        if(StringUtils.isEmptyOrWhitespace(adminInfoDto.getPasswd())) {
            bindingResult.addError(new FieldError("user", "passwd", "패스워드는 필수입니다."));
            return "login/login";
        }

        String token = "";
        try {
            token = loginService.login(adminInfoDto.getAdmId(), adminInfoDto.getPasswd());
        } catch(CheckedGlobalException ignored) {
            bindingResult.addError(new ObjectError("user", ignored.getMessage()));
            log.error("LoginController.login: {}", ignored);
        }

        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        /* token 생성 */
        Cookie cookie = new Cookie(tokenName , URLEncoder.encode(token, "UTF-8"));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        /** ahtentication  생성 */
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractSubject(token));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Cookie menuExpandCookie = new Cookie("IS_MENU_EXPAND", "false");
        menuExpandCookie.setPath("/");
        response.addCookie(menuExpandCookie);

        return "redirect:/campaign/list/list.do?acl=C0001";
    }

    /**
     * 가입페이지
     */
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user",  AdminInfoDto.builder().build());

        return "signup/signup";
    }

    /**
     * 계정신청
     *
     * @param adminInfoDto
     * @return
     */
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") AdminInfoDto adminInfoDto, BindingResult bindingResult) throws Exception {
        //동일 아이디 체크
        Map<String, Object> id = loginService.chkUserId(adminInfoDto.getAdmId());
        if((Boolean)id.get("exists")) {
            bindingResult.addError(new FieldError("user", "admId", (String) id.get("message")));
        }
        //동일 이메일 체크
        Map<String, Object> mail = loginService.chkEmail(DigestUtils.sha256Hex(adminInfoDto.getAdmEmail()));
        if((Boolean)mail.get("exists")) {
            bindingResult.addError(new FieldError("user", "admEmail", (String) id.get("message")));
        }

        if (bindingResult.hasErrors()) {
            return "signup/signup";
        }

        String originEmail = adminInfoDto.getAdmEmail();

        adminInfoDto.setPasswd(passwordEncoder.encode(adminInfoDto.getPasswd()));
        adminInfoDto.setPasswdDate(Instant.now());
        adminInfoDto.setAdmEmailHash(DigestUtils.sha256Hex(adminInfoDto.getAdmEmail()));
        adminInfoDto.setAdmEmail(crypto.encrypt(adminInfoDto.getAdmEmail()));
        adminInfoDto.setAdmHphoneHash(DigestUtils.sha256Hex(adminInfoDto.getAdmHphone()));
        adminInfoDto.setAdmHphone(crypto.encrypt(adminInfoDto.getAdmHphone()));
        adminInfoDto.setAdmFlag("0");
        adminInfoDto.setUseTf("Y");
        adminInfoDto.setDelTf("N");
        adminInfoDto.setLoginFailCnt(0);
        adminInfoDto.setRegDate(Instant.now());
        adminInfoDto.setUpDate(Instant.now());

        loginService.addUser(adminInfoDto);

        /* mail 전송 */
        mailService.send("신청상태",
                originEmail,
                "/form/signupMail.html",
                context -> {
                    context.setVariable("title","신청상태");
                    context.setVariable("message", adminInfoDto.getAdmName()+"님은 <b style=\"font-size: 16px; color: #4130df\">승인 대기</b> 중입니다.");
                    context.setVariable("link",address+"/login");
                });

        return "signup/complete";
    }

    /**
     * 아이디 중복 체크
     *
     * @param userId
     * @return
     */

    @GetMapping("/signup/user/validate/{user_id}")
    public ResponseEntity<?> chkUserId(@PathVariable("user_id") String userId) {
        return Response.of(loginService.chkUserId(userId));
    }

    /**
     * email 체크
     *
     * @param email
     * @return
     */
    @GetMapping("/signup/email/validate/{email}")
    public ResponseEntity<?> chkEmail(@PathVariable("email") String email) {

        Map<String, Object> rst = null;

        try {
            rst = loginService.chkEmail(DigestUtils.sha256Hex(email));
        } catch(Exception e) {
            log.error("chkEmail crypto ::: ", e.getMessage());
        }

        return Response.of(rst);
    }
}
