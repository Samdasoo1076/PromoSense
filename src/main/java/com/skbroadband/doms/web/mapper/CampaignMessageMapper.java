package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignMessageDto;
import com.skbroadband.doms.web.entity.CampaignMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignMessageMapper {

    CampaignMessage toEntity(CampaignMessageDto campaignMessageDto);

    @Mapping(target = "caNo", ignore = true)
    CampaignMessageDto toDto(CampaignMessage campaignMessage);

}
