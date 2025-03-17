package com.skbroadband.doms.global.component.security.auth;

import com.skbroadband.doms.web.entity.AccessTokenWhiteList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security.auth
 * @File : JwtWhiteListService
 * @Program :
 * @Date : 2023-01-13
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class JwtWhiteListService {
    private final JwtWhiteListRepository tokenWhiteListRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Optional<AccessTokenWhiteList> findToken(Long admNo) {
        return  tokenWhiteListRepository.findById(admNo);
    }

    @Transactional(value = "webTransactionManager")
    public void upateTokens(AccessTokenWhiteList accessTokenWhiteList) {
        tokenWhiteListRepository.deleteById(accessTokenWhiteList.getAdmNo());
        tokenWhiteListRepository.save(accessTokenWhiteList);
    }
}
