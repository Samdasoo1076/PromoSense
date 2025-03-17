package com.skbroadband.doms.web.service;

import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.repository.AdminInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : UserService
 * @Program :
 * @Date : 2022-12-08
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AdminInfoRepository userRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> isAdmName(String admName) {
        Boolean exists = userRepository.existsByAdmName(admName);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "이름이 확인 되었습니다.");
        } else {
            rtnMap.put("message", "등록된 이름이 없습니다.");
        }

        return rtnMap;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> isAdmEmailHash(String admEmail) {
        Boolean exists = userRepository.existsByAdmEmailHash(admEmail);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "이메일이 확인 되었습니다.");
        } else {
            rtnMap.put("message", "등록된 이메일이 없습니다.");
        }

        return rtnMap;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> isAdmId(String admId) {
        Boolean exists = userRepository.existsByAdmId(admId);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "아이디가 확인 되었습니다.");
        } else {
            rtnMap.put("message", "등록된 아이디가 없습니다.");
        }

        return rtnMap;
    }

    /**
     * 비밀번호 변경
     *
     * @param admId
     * @param sendDate
     */
    @Transactional(value = "webTransactionManager", readOnly = true)
    public Optional<AdminInfo> isChangePossible(String admId, String sendDate) {
//        Optional<AdminInfo> adminInfo = userRepository.findByAdmId(admId);

        return userRepository.findByAdmId(admId);
    }

    /**
     * 비밀번호 변경
     * @param admNo
     * @param passwd
     */
    @Transactional(value = "webTransactionManager")
    public void changePassword(Long admNo, String passwd) throws Exception {

        AdminInfo admInfo = userRepository.findById(admNo)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        admInfo.updatePasswd(passwd);

    }


    @Transactional(value = "webTransactionManager", readOnly = true)
    public AdminInfo findSearchId(String admName, String admEmail) {

        return userRepository.findByAdmNameAndAdmEmailHash(admName, admEmail);

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public AdminInfo findSearchPassword(String admId, String admEmail) {
        return userRepository.findByAdmIdAndAdmEmailHash(admId, admEmail);
    }

}
