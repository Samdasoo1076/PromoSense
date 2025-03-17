package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignRevisitUrlDto;
import com.skbroadband.doms.web.entity.CampaignRevisitUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignRevisitUrlMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignRevisitUrlMapper {

    CampaignRevisitUrl toEntity(CampaignRevisitUrlDto campaignRevisitUrlDto);

    CampaignRevisitUrlDto toDto(CampaignRevisitUrl campaignRevisitUrl);

    @Mapping(target = "caNo", ignore = true)
    CampaignRevisitUrlDto toDtoNoLazy(CampaignRevisitUrl campaignRevisitUrl);

}
