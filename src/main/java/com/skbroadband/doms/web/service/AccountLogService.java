package com.skbroadband.doms.web.service;


import com.skbroadband.doms.global.utils.ViewCommonUtils;
import com.skbroadband.doms.web.dto.AccountLogDto;
import com.skbroadband.doms.web.mapper.AccountLogMapper;
import com.skbroadband.doms.web.repository.AdminLogRepository;
import com.skbroadband.doms.web.repository.AdminLogSupportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : AccountLogService
 * @Program :
 * @Date : 2022-12-08
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountLogService {

    private final AdminLogRepository adminLogRepository;
    private final AccountLogMapper accountLogMapper;
    private final ViewCommonUtils viewCommonUtils;

    private final AdminLogSupportRepository adminLogSupportRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<AccountLogDto> findAdminLogs(String keyword, String startDate, String endDate, String taskFlag, Pageable pageable) {

        Page<AccountLogDto> accountLogDtos = adminLogSupportRepository.findAll(keyword, startDate, endDate, taskFlag, pageable)
                .map(accountLogDto -> accountLogMapper.toMarkedDto(accountLogDto));
        accountLogDtos.forEach(logDto -> {
                logDto.setAdmId(
                        logDto.getAdmId().replaceAll(keyword, "<mark class=\"em accent-01\">"+keyword+"</mark>")
                );
        });

        return accountLogDtos;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public long countAllAccounts() {
        return adminLogSupportRepository.totalCount();
    }


    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AccountLogDto> findExcelList(String keyword, String startDate, String endDate) {
//        return adminLogSupportRepository.findExcelList(keyword, startDate, endDate).stream().map(accountLogMapper::toAccLogDto).collect(Collectors.toList());
        return adminLogSupportRepository.findExcelList(keyword, startDate, endDate).stream().map(
                accountLogDto -> accountLogMapper.toMarkedDto(accountLogDto)
        ).collect(Collectors.toList());
    }



}
