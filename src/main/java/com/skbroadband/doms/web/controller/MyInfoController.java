package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.JceCryptoComponent;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.exception.CheckedUnauthorizedException;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : MyInfoController
 * @Program :
 * @Date : 2023-04-04
 * @Comment :
 */
@Controller
@RequiredArgsConstructor
public class MyInfoController {
    private final AccountService accountService;
    private final JceCryptoComponent jceCryptoComponent;
    private final PasswordEncoder passwordEncoder;

    // 두 경우 모두 숫자와 알파벳이 필요한 4-8자 비밀번호
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";

    private static String COMPLEX_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[`~!@#$%^&*()])(?=.*\\d)(?=\\S+$).{8,16}$";
    private static String COMPLEX_REPEATING_CHAR_REGEX = "(\\w)\\1\\1";

    private static Pattern PASSWORD_PATTERN = Pattern.compile(COMPLEX_PASSWORD_REGEX);
    private static Pattern REPEATING_CHAR_PATTERN = Pattern.compile(COMPLEX_REPEATING_CHAR_REGEX);

    @Log(content = "내 정보 조회", action = WorkType.Detail)
    @PreAuthorize("hasPermission('', 'read')")
    @GetMapping("/myinfo.do")
    public String myInfo(@LoginUser Account user, Model model) {

        AdminInfoDto admInfo = accountService.findAccountDetail(user.getAdmNo());

        model.addAttribute("admInfo", admInfo);
        model.addAttribute("upAdmName",
                (accountService.findUpAdmName(admInfo.getUpAdm()) == null)? "" : accountService.findUpAdmName(admInfo.getUpAdm()).getAdmName());

        return "user/myinfo.detail";
    }

    @Log(content = "내 쟁보 수정", action = WorkType.Modification)
    @PostMapping("/myinfo/update.do")
    public ResponseEntity<?> update(@RequestBody AdminInfoDto adminInfoDto,
                                    @LoginUser Account user) throws Exception {
//        Pattern pwdCheck = Pattern.compile("^[a-zA-Z0-9\d`~!@#$%^&*()-_=+]{8,16}$");

        if(!StringUtils.hasText(String.valueOf(adminInfoDto.getAdmEmail()))) {
            throw new BadRequestException("이메일은 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(adminInfoDto.getAdmPhone()))) {
            throw new BadRequestException("휴대폰번호는 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(adminInfoDto.getDept()))) {
            throw new BadRequestException("부서는 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(adminInfoDto.getOldPassword()))) {
            throw new BadRequestException("기존 비밀번호는 필수 입니다.");
        }

        AdminInfoDto admInfo = accountService.findAccountDetail(user.getAdmNo());
        if(!passwordEncoder.matches(adminInfoDto.getOldPassword(), admInfo.getPasswd())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        if(!StringUtils.hasText(String.valueOf(adminInfoDto.getPasswd()))) {
            throw new BadRequestException("8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.");
        }

        if (!PASSWORD_PATTERN.matcher(String.valueOf(String.valueOf(adminInfoDto.getPasswd()))).matches()) {
            throw new BadRequestException("8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.");
        }

        Matcher passMatcher = REPEATING_CHAR_PATTERN.matcher(String.valueOf(String.valueOf(adminInfoDto.getPasswd())));

        if (passMatcher.find()) {
            throw new BadRequestException("3자리 이상 반복되는 영문, 숫자는 비밀번호로 사용할 수 없습니다.");
        }

        if(adminInfoDto.getChkPassword()) {
            adminInfoDto.setPasswd(passwordEncoder.encode(String.valueOf(adminInfoDto.getPasswd())));
        }

        if(adminInfoDto.getChkEmail()) {
            adminInfoDto.setAdmEmail(jceCryptoComponent.encrypt(String.valueOf(adminInfoDto.getAdmEmail())));
            adminInfoDto.setAdmEmailHash(DigestUtils.sha256Hex(String.valueOf(adminInfoDto.getAdmEmail())));
        }

        adminInfoDto.setAdmNo(user.getAdmNo());
        adminInfoDto.setUpAdm(String.valueOf(user.getAdmNo()));
        adminInfoDto.setUpDate(Instant.now());
        adminInfoDto.setAdmHphone(jceCryptoComponent.encrypt(adminInfoDto.getAdmHphone()));
        adminInfoDto.setAdmHphoneHash(DigestUtils.sha256Hex(adminInfoDto.getAdmHphone()));

        accountService.updateMyInfo(adminInfoDto);

        return Response.ok();
    }
}
