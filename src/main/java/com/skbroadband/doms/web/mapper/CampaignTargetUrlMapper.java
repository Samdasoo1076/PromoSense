package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignTargetUrlDto;
import com.skbroadband.doms.web.entity.CampaignTargetUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignTargetUrlMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignTargetUrlMapper {

    CampaignTargetUrl toEntity(CampaignTargetUrlDto campaignTargetUrlDto);

    CampaignTargetUrlDto toDto(CampaignTargetUrl campaignTargetUrl);

    @Mapping(target = "caNo", ignore = true)
    CampaignTargetUrlDto toDtoNoLazy(CampaignTargetUrl campaignTargetUrl);

}
