package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignMessageMultiDto;
import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CampaignMessageMultiMapper {
    CampaignMessageMulti toEntity(CampaignMessageMultiDto campaignMessageMultiDto);
    CampaignMessageMultiDto toDto(CampaignMessageMulti campaignMessageMulti);
}
