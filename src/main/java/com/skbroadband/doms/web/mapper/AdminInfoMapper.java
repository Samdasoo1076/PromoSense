package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.global.utils.CommUtils;
import com.skbroadband.doms.global.utils.ViewCommonUtils;
import com.skbroadband.doms.web.dto.AdminGroupDto;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AdminInfo;
import org.mapstruct.*;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : AdminInfoMapper
 * @Program :
 * @Date : 2023-01-05
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface AdminInfoMapper {
    @Mapping(source = "admNo", target = "id")
    @Mapping(target = "groupNo", expression = "java(convertGroup(adminInfoDto.getGroupNo()))")
    @Mapping(target = "admFlag", constant = "1")
    AdminInfo toEntity(AdminInfoDto adminInfoDto);

    @Mapping(source = "id", target = "admNo")
    @Mapping(source = "groupNo.id", target = "groupNo.groupNo")
    @Mapping(target = "admHphoneHash", ignore = true)
    @Mapping(target = "admEmailHash", ignore = true)
    AdminInfoDto toDto(AdminInfo adminInfo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupNo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminInfoDto adminInfoDto, @MappingTarget AdminInfo adminInfo);



    default com.skbroadband.doms.web.entity.AdminGroup convertGroup(com.skbroadband.doms.web.dto.AdminGroupDto groupDto) {
        if (groupDto == null) {
            // 기본 그룹으로 id가 1인 AdminGroup을 생성
            return com.skbroadband.doms.web.entity.AdminGroup.builder()
                    .id(1L)
                    .build();
        } else {
            return com.skbroadband.doms.web.entity.AdminGroup.builder()
                    .id(groupDto.getGroupNo())
                    .groupName(groupDto.getGroupName())
                    .build();
        }
    }


    //    @Mapping(source = "id", target = "admNo")
//    @Mapping(source = "groupNo.id", target = "groupNo.groupNo")
//    @Mapping(target = "admHphoneHash", ignore = true)
//    @Mapping(target = "admEmailHash", ignore = true)
    default AdminInfoDto toMarkedDto(AdminInfo adminInfo, String keyword) {
        ViewCommonUtils viewCommonUtils = CommUtils.getBean(ViewCommonUtils.class);
        return AdminInfoDto.builder()
                .admNo(adminInfo.getId())
//                .admId(adminInfo.getAdmId().replaceAll(keyword, "<strong class=\"em accent-01\">"+keyword+"</strong>"))
//                .admName(adminInfo.getAdmName().replaceAll(keyword, "<strong class=\"em accent-01\">"+keyword+"</strong>"))
                .admId(viewCommonUtils.getMaskAdmId(adminInfo.getAdmId()))
                .admName(viewCommonUtils.getMaskName(adminInfo.getAdmName()))
                .admHphone(viewCommonUtils.getHypenMaskHphone(adminInfo.getAdmHphone()))
                .dept(adminInfo.getDept())
                .admFlag(adminInfo.getAdmFlag())
                .regDate(adminInfo.getRegDate())
                .upDate(adminInfo.getUpDate())
                .loginFailCnt(adminInfo.getLoginFailCnt())
                .groupNo(adminInfo.getGroupNo()==null?
                        null:
                        AdminGroupDto.builder()
                        .groupNo(adminInfo.getGroupNo().getId())
                        .groupName(adminInfo.getGroupNo().getGroupName())
                        .build())
                .build();
    }
}
