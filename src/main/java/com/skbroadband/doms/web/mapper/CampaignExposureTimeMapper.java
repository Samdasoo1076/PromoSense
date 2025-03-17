package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignExposureTimeDto;
import com.skbroadband.doms.web.entity.CampaignExposureTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignExposureTimeMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignExposureTimeMapper {

    CampaignExposureTime toEntity(CampaignExposureTimeDto campaignExposureTimeDto);

    CampaignExposureTimeDto toDto(CampaignExposureTime campaignExposureTime);

    @Mapping(target = "caNo", ignore = true)
    CampaignExposureTimeDto toDtoNoLazy(CampaignExposureTime campaignExposureTime);

}
