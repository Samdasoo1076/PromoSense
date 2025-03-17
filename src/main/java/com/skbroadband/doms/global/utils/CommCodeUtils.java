package com.skbroadband.doms.global.utils;

import com.skbroadband.doms.web.dto.CampaignBaseTypeDto;
import com.skbroadband.doms.web.dto.CodeDetailDto;
import com.skbroadband.doms.web.mapper.CampaignBaseTypeMapper;
import com.skbroadband.doms.web.mapper.CodeDetailMapper;
import com.skbroadband.doms.web.repository.CampaignBaseTypeRepository;
import com.skbroadband.doms.web.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommCodeUtils {

    private final CodeDetailMapper codeDetailMapper;

    private final CampaignBaseTypeMapper campaignBaseTypeMapper;

    private final CodeDetailRepository codeDetailRepository;

    private final CampaignBaseTypeRepository campaignBaseTypeRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CodeDetailDto> getCodeList(String pcode) {

        return codeDetailRepository.findByUseTfAndDelTfAndCodeParent_IdOrderByDcodeSeqNo("Y", "N", pcode).stream().map(codeDetailMapper::toDto).collect(Collectors.toList());

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CampaignBaseTypeDto> getCampaignBaseList() {

        return campaignBaseTypeRepository.findAllByUseTfAndDelTfOrderByBaseOrder("Y", "N").stream().map(campaignBaseTypeMapper::toDto).collect(Collectors.toList());

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CodeDetailDto getCodeInfo(String pcode, String dcode) {

        CodeDetailDto codeDetailDto = codeDetailMapper.toDto(
                codeDetailRepository.findByUseTfAndDelTfAndCodeParent_IdAndDcode("Y", "N", pcode, dcode));

        return (codeDetailDto == null)? CodeDetailDto.builder().build() : codeDetailDto;

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignBaseTypeDto getCampaignBaseInfo(Long id) {

        CampaignBaseTypeDto campaignBaseTypeDto = campaignBaseTypeMapper.toDto(campaignBaseTypeRepository.findById(id));

        return (campaignBaseTypeDto == null)? CampaignBaseTypeDto.builder().build() : campaignBaseTypeDto;

    }

}
