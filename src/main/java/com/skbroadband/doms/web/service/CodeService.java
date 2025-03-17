package com.skbroadband.doms.web.service;

import com.skbroadband.doms.web.dto.CodeDetailDto;
import com.skbroadband.doms.web.mapper.CodeDetailMapper;
import com.skbroadband.doms.web.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : CodeService
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeDetailMapper codeDetailMapper;
    private final CodeDetailRepository codeDetailRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CodeDetailDto> getCodeList(String pcode) {
        return codeDetailRepository.findByUseTfAndDelTfAndCodeParent_IdOrderByDcodeSeqNo("Y", "N", pcode).stream().map(codeDetailMapper::toDto).collect(Collectors.toList());
    }
}
