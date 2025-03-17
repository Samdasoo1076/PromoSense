package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.component.security.Crypto;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.service.MailService;
import com.skbroadband.doms.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import com.skbroadband.doms.global.utils.CommUtils;


/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : UserController
 * @Program :
 * @Date : 2022-12-08
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;
    private final Crypto crypto;
    private final PasswordEncoder passwordEncoder;

    @Value("${server.etc-info.address}")
    private String address;

    @GetMapping("/search/id")
    public String searchId(Model model){
        model.addAttribute("user",  AdminInfoDto.builder().build());

        return "login/search.id";
    }

    /**
     * 이름 확인
     *
     * @param admName
     * @return
     */
    @GetMapping("/search/name/{adm_name}")
    public ResponseEntity<?> isAdmName(@PathVariable("adm_name") String admName) {
        if (!admName.isEmpty()) {
            admName = CommUtils.replaceXSS(admName);
        }

        return Response.of(userService.isAdmName(admName.trim()));

    }

    @GetMapping("/search/email/{adm_email}")
    public ResponseEntity<?> isAdmEmailHash(@PathVariable("adm_email") String admEmail) {
        Map<String, Object> rst = null;

        try {
            rst = userService.isAdmEmailHash(DigestUtils.sha256Hex(admEmail.trim()));
        } catch(Exception e) {
            log.error("chkEmail crypto ::: {}", e.getMessage());
        }

        return Response.of(rst);
    }

    @PostMapping("/search/id/find")
    public String findSearchId(@ModelAttribute("user") AdminInfoDto adminInfoDto, BindingResult bindingResult, Model model) {
        AdminInfo adminInfo = userService.findSearchId(adminInfoDto.getAdmName().trim(), DigestUtils.sha256Hex(adminInfoDto.getAdmEmail().trim()));
        //AdminInfo adminInfo = userService.findSearchId("11111", "11111");

        if(adminInfo == null) {
            bindingResult.addError(new ObjectError("adminInfoDto", "데이터가 존재 하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "login/search.id";
        }

        int len = adminInfo.getAdmId().length();
        String admId = String.format("%-" + len + ".4s", adminInfo.getAdmId()).replace(" ", "*");

        model.addAttribute("admId", admId);

        return "login/search.id.complete";
    }

    @GetMapping("/search/password")
    public String searchPassword(Model model){
        model.addAttribute("user",  AdminInfoDto.builder().build());

        return "login/search.password";
    }

    /**
     * 비밀번호변경
     *
     * @return
     */
    @GetMapping("/search/change/password")
    public String changePassword(@RequestParam("key") String key, @ModelAttribute("user") AdminInfoDto adminInfoDto, Model model, BindingResult bindingResult){
        String descKey;
        String keyAdmId;
        String keyDate;
        String rtnUrl = "";
        Long admNo;
        String[] keyAry;
        LocalDate toDate = LocalDate.now();
//        LocalDate toDate = LocalDate.of(2023,1,17);


        try {

            descKey  = crypto.descrypt(key.trim());

            keyAry = descKey.split("\\|");

            if(keyAry.length > 0){

                    keyAdmId = keyAry[0];
                    keyDate = keyAry[1];

                    Optional<AdminInfo> userInfo = userService.isChangePossible(keyAdmId, keyDate);

                    if(userInfo == null) {
                        bindingResult.addError(new ObjectError("adminInfoDto", "데이터가 존재하지 않습니다."));
                    }

                    //메일발송일자와 현재일자가 동일하여야 패스워드 변경 가능함
                    if(toDate.toString().equals(keyDate)){
                        admNo = userInfo.get().getId();
                        model.addAttribute("admNo", admNo);
                        model.addAttribute("user", userInfo);
                        rtnUrl = "/user/change.password";
                    }
                    else{
                        bindingResult.addError(new ObjectError("adminInfoDto", "변경가능 일자가 아닙니다. 재신청하세요."));
                        //model.addAttribute("user", userInfo);
                        rtnUrl = "/user/change.password";
                    }

            }

        } catch(Exception e) {
            log.error("crypto error >>> {}", e.getMessage());
        }
        return rtnUrl;
    }

    /**
     * 비밀번호변경
     *
     * @param admNo
     * @param passwd
     * @return
     */

    @PostMapping("/search/change/password")
    public String changePassword(Long admNo, String passwd) throws Exception {

        String EnPasswd = passwordEncoder.encode(passwd);
        userService.changePassword(admNo, EnPasswd);

        return "user/change.password.complete";
    }

    @GetMapping("/search/id/{adm_id}")
    public ResponseEntity<?> isAdmId(@PathVariable("adm_id") String admId) {
        if (!admId.isEmpty()) {
            admId = CommUtils.replaceXSS(admId);
        }

        return Response.of(userService.isAdmId(admId.trim()));

    }

    @PostMapping("/search/password/find")
    public String findSearchPassword(@ModelAttribute("user") AdminInfoDto adminInfoDto, BindingResult bindingResult) {
        AdminInfo adminInfo = userService.findSearchPassword(adminInfoDto.getAdmId().trim(), DigestUtils.sha256Hex(adminInfoDto.getAdmEmail().trim()));

        if(adminInfo == null) {
            bindingResult.addError(new ObjectError("adminInfoDto", "데이터가 존재 하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "login/search.password";
        }

        String enc = "";
        LocalDate nowDate = LocalDate.now();

        try {
            enc = crypto.encrypt(adminInfoDto.getAdmId() + "|" + nowDate);
        } catch(Exception e) {
            log.error("crypto error >>> {}", e.getMessage());
        }


        /* mail 전송 */
        String finalEnc = URLEncoder.encode(enc);
        mailService.send("비밀번호 재설정",
                adminInfoDto.getAdmEmail(),
                "/form/password.reset.mail.html",
                context -> {
                    context.setVariable("url", address + "/search/change/password?key=" + finalEnc);
                });

        return "login/search.password.complete";
    }

}
