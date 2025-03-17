package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.AdminGroupDto;
import com.skbroadband.doms.web.entity.AdminGroup;
import org.mapstruct.*;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : AdminInfoMapper
 * @Program :
 * @Date : 2023-01-31
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface AdminGroupMapper {
    @Mapping(source = "groupNo", target = "id")
    AdminGroup toEntity(AdminGroupDto adminGroupDto);

    @Mapping(source = "id", target = "groupNo")
    AdminGroupDto toDto(AdminGroup adminGroup);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminGroupDto adminGroupDto, @MappingTarget AdminGroup adminGroup);
}
