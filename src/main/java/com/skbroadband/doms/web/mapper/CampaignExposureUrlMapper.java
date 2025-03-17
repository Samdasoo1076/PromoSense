package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignExposureUrlDto;
import com.skbroadband.doms.web.entity.CampaignExposureUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignExposureUrlMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignExposureUrlMapper {

    CampaignExposureUrl toEntity(CampaignExposureUrlDto campaignExposureUrlDto);

    CampaignExposureUrlDto toDto(CampaignExposureUrl campaignExposureUrl);

    @Mapping(target = "caNo", ignore = true)
    CampaignExposureUrlDto toDtoNoLazy(CampaignExposureUrl campaignExposureUrl);

}
