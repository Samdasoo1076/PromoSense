package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignMessageDto;
import com.skbroadband.doms.web.dto.CampaignMessageHistoryDto;
import com.skbroadband.doms.web.entity.CampaignMessageHistory;
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
public interface CampaignMessageHistoryMapper {

    CampaignMessageHistory toEntity(CampaignMessageHistoryDto campaignMessageHistoryDto);
    CampaignMessageHistory toHisEntity(CampaignMessageDto campaignMessageDto);
    CampaignMessageHistoryDto toDto(CampaignMessageHistory campaignMessageHistory);
    @Mapping(target = "id", ignore = true)
    CampaignMessageDto toMsgDto(CampaignMessageHistory campaignMessageHistory);

}
