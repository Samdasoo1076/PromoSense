package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.web.dto.AdminMenuDto;
import com.skbroadband.doms.web.entity.AdminMenu;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.mapper
 * @File : AdminMenuMapper
 * @Program :
 * @Date : 2023-01-30
 * @Comment :
 */
@Mapper(componentModel = "spring")
public interface AdminMenuMapper {

    default List<AdminMenuDto> toDtoList(List<AdminMenu> menus) {
        return menus.stream()
                .filter(adminMenu -> "Y".equals(adminMenu.getUseTf()) && "N".equals(adminMenu.getDelTf()))
                .sorted(Comparator.comparing(AdminMenu::getMenuOrder))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default AdminMenuDto toDtoByNoLazy(AdminMenu menu){
        return AdminMenuDto.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .menuUrl(menu.getMenuUrl())
                .menuCode(menu.getMenuCode())
                .menuImg(menu.getMenuImg())
                .build();
    }

    AdminMenuDto toDto(AdminMenu menu);

    AdminMenu toEntity(AdminMenuDto adminMenuDto);

    @Mapping(target = "subMenu", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AdminMenuDto adminMenuDto, @MappingTarget AdminMenu adminInfo);
}
