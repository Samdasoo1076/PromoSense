package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.dto.AcceptDto;
import com.skbroadband.doms.web.entity.Accept;
import com.skbroadband.doms.web.mapper.AcceptMapper;
import com.skbroadband.doms.web.repository.AcceptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : AcceptService
 * @Program :
 * @Date : 2023-02-07
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcceptService {

    private final AcceptRepository acceptRepository;

    private final AcceptMapper acceptMapper;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<AcceptDto> findAllList(String useTf, String delTf, Pageable pageable) {
        return acceptRepository.findByUseTfAndDelTfOrderByIdDesc( useTf, delTf, pageable).map(acceptMapper::toDto);
    }

    /**
     * Ip 등록
     * @param acceptDto
     */
    @Transactional(value = "webTransactionManager")
    public void addAccept(AcceptDto acceptDto) {
        acceptRepository.save(acceptMapper.toEntity(acceptDto));
    }

    /**
     * Ip 중복체크
     *
     * @param acceptIp
     * @return
     */
    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map<String, Object> chkAcceptIp(String acceptIp) {
        Boolean exists = acceptRepository.existsByAcceptIp(acceptIp);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("exists", exists);

        if(exists) {
            rtnMap.put("message", "이미 사용중인 아이피 입니다.");
        } else {
            rtnMap.put("message", "사용 가능한 아이피 입니다.");
        }

        return rtnMap;
    }

    /**
     * Ip 삭제
     *
     * @param seqNo
     * @param delAdm
     * @return
     */
    @Transactional(value = "webTransactionManager")
    public void deleteAcceptIp(Long seqNo, Long delAdm) {
        Accept accept = acceptRepository.findById(seqNo).orElse(null);

        if(accept == null) {
            throw new BadRequestException("삭제 가능한 IP가 없습니다.");
        }

        accept.deleteAcceptIp(delAdm);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Boolean chkAcceptIpByUseTfAndDelTf(String ip, String useTf, String delTf) {
        return acceptRepository.existsByAcceptIpAndUseTfAndDelTf(ip, useTf, delTf);
    }
}
