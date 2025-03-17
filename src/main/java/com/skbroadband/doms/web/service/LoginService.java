package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.component.security.auth.JwtService;
import com.skbroadband.doms.global.component.security.auth.JwtWhiteListRepository;
import com.skbroadband.doms.global.exception.CheckedUnauthorizedException;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AccessTokenWhiteList;
import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.mapper.AdminInfoMapper;
import com.skbroadband.doms.web.repository.AdminInfoRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : LoginService
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminInfoMapper adminInfoMapper;
    private final AdminInfoRepository userRepository;
    private final JwtWhiteListRepository accessTokenWhiteListRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * 계정신청
     * @param adminInfoDto
     */
    @Transactional(value = "webTransactionManager")
    public void addUser(AdminInfoDto adminInfoDto) {
        AdminInfo adminInfo = userRepository.save(adminInfoMapper.toEntity(adminInfoDto));
        adminInfo.upadateRegAdmAndUpAdm(adminInfo.getId());
    }

    /**
     * 로그인처리
     *
     * @param admId
     * @param password
     * @return
     * @throws Exception
     */
    @Transactional(value = "webTransactionManager")
    public String login(String admId, String password) throws Exception {
        String resMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";

        AdminInfo user = userRepository.findByAdmIdAndUseTfAndDelTf(admId, "Y", "N")
                .orElseThrow(() -> new CheckedUnauthorizedException(resMsg));
        // 승인대기 상태일 경우
        if("0".equals(user.getAdmFlag())) {
            throw new CheckedUnauthorizedException(resMsg);
        }

        // 로그인 실패횟수 체크, 회원상태가 정지일 궁우
        if(user.getLoginFailCnt() > 4 || "2".equals(user.getAdmFlag())) {
            /*throw new CheckedUnauthorizedException("아이디가 정지 or 5회 비밀번호가 잘못 입력\n" +
                    "되어 접속이 불가능합니다. 관리자에게 문의해 주세요.");*/

            throw new CheckedUnauthorizedException(resMsg);
        }

        String token = "";
        if(passwordEncoder.matches(password, user.getPasswd())) {
            // 토큰 발행
            token = jwtService.generateToken(new Account(user));

            // 로그인일자 변경
            user.updateLoginDate();

            // white list 등록
            accessTokenWhiteListRepository.deleteById(user.getId());
            accessTokenWhiteListRepository.save(AccessTokenWhiteList.builder()
                    .admNo(user.getId())
                    .accessToken(token)
                    .issuedDate(jwtService.extractClaim(token, Claims::getIssuedAt).toInstant())
                    .expiresIn(jwtService.extractClaim(token, Claims::getExpiration).toInstant())
                    .build());
        } else {
            // 로그인 실패 처리
            user.updateLoginFailCnt();
            throw new CheckedUnauthorizedException(resMsg);
        }

        return token;
    }

    /**
     * 아이디 중복체크
     *
     * @param userId
     * @return
     */
    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> chkUserId(String userId) {
        Boolean exists = userRepository.existsByAdmId(userId);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "이미 사용중인 아이디 입니다.");
        } else {
            rtnMap.put("message", "사용 가능한 아이디 입니다.");
        }

        return rtnMap;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> chkEmail(String emailHash) {
        Boolean exists = userRepository.existsByAdmEmailHash(emailHash);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "이미지 사용중인 이메일 입니다.");
        } else {
            rtnMap.put("message", "사용 가능한 이메일 입니다.");
        }

        return rtnMap;
    }
}
