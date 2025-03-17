package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignDto;
import com.skbroadband.doms.web.entity.Campaign;
import org.mapstruct.Mapper;

import java.util.HashSet;

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
public interface CampaignMapper {

    Campaign toEntity(CampaignDto campaignDto);

    default Campaign toEntities(CampaignDto campaignDto) {
        Campaign campaign = toEntity(campaignDto);

        if(campaignDto.getCampaignExposureTimeDtos() != null && campaignDto.getCampaignExposureTimeDtos().size() > 0) {
            campaign.setCampaignExposureTime(new HashSet<>());
        }
        if(campaignDto.getCampaignExposureUrlDtos() != null && campaignDto.getCampaignExposureUrlDtos().size() > 0) {
            campaign.setCampaignExposureUrl(new HashSet<>());
        }
        if(campaignDto.getCampaignRevisitUrlDtos() != null && campaignDto.getCampaignRevisitUrlDtos().size() > 0) {
            campaign.setCampaignRevisitUrl(new HashSet<>());
        }
        if(campaignDto.getCampaignSpecUrlDtos() != null && campaignDto.getCampaignSpecUrlDtos().size() > 0) {
            campaign.setCampaignSpecUrl(new HashSet<>());
        }
        if(campaignDto.getCampaignTargetUrlDtos() != null && campaignDto.getCampaignTargetUrlDtos().size() > 0) {
            campaign.setCampaignTargetUrl(new HashSet<>());
        }

        return campaign;
    }

    CampaignDto toDto(Campaign campaign);

}
