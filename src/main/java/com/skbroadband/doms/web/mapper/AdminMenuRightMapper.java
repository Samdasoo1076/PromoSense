package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.AdminMenuRightDto;
import com.skbroadband.doms.web.entity.AdminGroup;
import com.skbroadband.doms.web.entity.AdminMenu;
import com.skbroadband.doms.web.entity.AdminMenuRight;
import com.skbroadband.doms.web.entity.AdminMenuRightId;
import org.mapstruct.*;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : AdminMenuRightMapper
 * @Program :
 * @Date : 2023-02-06
 * @Comment :
 */

@Mapper(componentModel = "spring")
public interface AdminMenuRightMapper {

    @Mapping(target = "groupNo", ignore = true)
    @Mapping(target = "menuNo", ignore = true)
    default AdminMenuRight toSetIdEntity(AdminMenuRightDto adminMenuRightDto) {
        AdminMenuRight adminMenuRight = AdminMenuRight.builder().build();
        AdminMenuRightId adminMenuRightId = AdminMenuRightId.builder().build();

        adminMenuRightId.setGroupNo(adminMenuRightDto.getGroupNo().getGroupNo());
        adminMenuRightId.setMenuNo(adminMenuRightDto.getMenuNo().getId());

        adminMenuRight.setId(adminMenuRightId);
        adminMenuRight.setGroupNo(AdminGroup.builder().id(adminMenuRightDto.getGroupNo().getGroupNo()).build());
        adminMenuRight.setMenuNo(AdminMenu.builder().id(adminMenuRightDto.getMenuNo().getId()).build());
        adminMenuRight.setReadFlag(adminMenuRightDto.getReadFlag());
        adminMenuRight.setRegFlag(adminMenuRightDto.getRegFlag());
        adminMenuRight.setUpdFlag(adminMenuRightDto.getUpdFlag());
        adminMenuRight.setDelFlag(adminMenuRightDto.getDelFlag());
        adminMenuRight.setFileFlag(adminMenuRightDto.getFileFlag());

        return adminMenuRight;
    }

    /*@Mapping(source = "groupNo.groupNo", target = "adminMenuRightId.groupNo")
    @Mapping(source = "menuNo.id", target = "adminMenuRightId.id")*/
    AdminMenuRight toEntity(AdminMenuRightDto adminMenuRightDto);


    AdminMenuRightDto toDto(AdminMenuRight adminMenuRight);

    @Mapping(target = "groupNo", ignore = true)
    @Mapping(target = "menuNo", ignore = true)
    AdminMenuRightDto toDtoNoLazy(AdminMenuRight adminMenuRight);

    @Mapping(target = "groupNo", ignore = true)
    @Mapping(target = "menuNo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminMenuRightDto adminMenuRightDto, @MappingTarget AdminMenuRight adminMenuRight);
}
