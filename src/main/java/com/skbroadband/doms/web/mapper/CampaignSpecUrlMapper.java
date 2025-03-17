package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignSpecUrlDto;
import com.skbroadband.doms.web.entity.CampaignSpecUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CampaignSpecUrlMapper
 * @Program :
 * @Date : 2023-02-15
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignSpecUrlMapper {

    CampaignSpecUrl toEntity(CampaignSpecUrlDto campaignSpecUrlDto);

    CampaignSpecUrlDto toDto(CampaignSpecUrl campaignSpecUrl);

    @Mapping(target = "caNo", ignore = true)
    CampaignSpecUrlDto toDtoNoLazy(CampaignSpecUrl campaignSpecUrl);

}
