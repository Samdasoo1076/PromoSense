package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.AcceptDto;
import com.skbroadband.doms.web.entity.Accept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : AcceptMapper
 * @Program :
 * @Date : 2023-01-31
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface AcceptMapper {
    @Mapping(source = "seqNo", target = "id")
    Accept toEntity(AcceptDto acceptDto);

    @Mapping(source = "id", target = "seqNo")
    AcceptDto toDto(Accept accept);

//    @Mapping(target = "id", ignore = true)
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void mergeToEntity(AcceptDto acceptDto, @MappingTarget Accept accept);
}
