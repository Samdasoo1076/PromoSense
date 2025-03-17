package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.dto.CodeDetailDto;
import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.entity.CodeDetail;
import org.mapstruct.*;

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
public interface CodeDetailMapper {

    CodeDetail toEntity(CodeDetailDto codeDetailDto);

    //@Mapping(source = "codeParent.id", target = "CodeParentDto.pcode")
    //@Mapping(target = "dcode", ignore = true)
    //@Mapping(target = "codeParentDto.pcode", ignore = true)
    CodeDetailDto toDto(CodeDetail codeDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupNo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminInfoDto adminInfoDto, @MappingTarget AdminInfo adminInfo);
}
