package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.CampaignBaseTypeDto;
import com.skbroadband.doms.web.entity.CampaignBaseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : CodeDetailMapper
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface CampaignBaseTypeMapper {

    @Mapping(source = "baseNo", target = "id")
    CampaignBaseType toEntity(CampaignBaseTypeDto campaignBaseTypeDto);

    @Mapping(source = "id", target = "baseNo")
    CampaignBaseTypeDto toDto(CampaignBaseType campaignBaseType);

    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "groupNo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminInfoDto adminInfoDto, @MappingTarget AdminInfo adminInfo);*/
}
