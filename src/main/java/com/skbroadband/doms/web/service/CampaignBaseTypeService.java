package com.skbroadband.doms.web.service;

import com.skbroadband.doms.web.dto.CampaignBaseTypeDto;
import com.skbroadband.doms.web.mapper.CampaignBaseTypeMapper;
import com.skbroadband.doms.web.repository.CampaignBaseTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignBaseTypeService {
    private final CampaignBaseTypeMapper campaignBaseTypeMapper;

    private final CampaignBaseTypeRepository campaignBaseTypeRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignBaseTypeDto getCaMsgType(Long id) {

        return campaignBaseTypeMapper.toDto(campaignBaseTypeRepository.findById(id));

    }

}
