package com.skbroadband.doms.web.mapper;

import com.skbroadband.doms.global.utils.CommUtils;
import com.skbroadband.doms.global.utils.ViewCommonUtils;
import com.skbroadband.doms.web.dto.AccountLogDto;
import com.skbroadband.doms.web.dto.AdminGroupDto;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AdminLog;
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
public interface AccountLogMapper {
    @Mapping(source = "seqNo", target = "id")
    AdminLog toEntity(AccountLogDto accountLogDto);


    @Mapping(source = "id", target = "seqNo")
    AccountLogDto toAccLogDto(AdminLog adminLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeToEntity(AccountLogDto accountLogDto, @MappingTarget AdminLog adminLog);

    default AccountLogDto toMarkedDto(AccountLogDto accountLogDto){
        ViewCommonUtils viewCommonUtils = CommUtils.getBean(ViewCommonUtils.class);
        return AccountLogDto.builder()
                .admNo(accountLogDto.getAdmNo())
                .groupName(accountLogDto.getGroupName())
                .admId(viewCommonUtils.getMaskAdmId(accountLogDto.getAdmId()))
                .task(accountLogDto.getTask())
                .taskType(accountLogDto.getTaskType())
                .logIp(viewCommonUtils.getMaskIp(null == accountLogDto.getLogIp() ? "" : accountLogDto.getLogIp()))
                .groupName(accountLogDto.getGroupName())
                .logDate(accountLogDto.getLogDate())
                .groupNo(accountLogDto.getGroupNo())
                .build();
    }
}
