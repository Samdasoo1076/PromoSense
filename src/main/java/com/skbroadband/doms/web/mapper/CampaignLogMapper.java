package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignAnalysisLogDto;
import com.skbroadband.doms.web.dto.CampaignDto;
import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignLog;
import org.mapstruct.Mapper;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface CampaignLogMapper {

    CampaignLog toEntity(CampaignAnalysisLogDto campaignAnalysisLogDto);

    CampaignAnalysisLogDto toDto(CampaignLog campaignLog);

}
