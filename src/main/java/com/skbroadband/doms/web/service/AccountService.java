package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.utils.ViewCommonUtils;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.entity.AdminInfo;
import com.skbroadband.doms.web.mapper.AdminInfoMapper;
import com.skbroadband.doms.web.repository.AdminInfoRepository;
import com.skbroadband.doms.web.repository.AdminInfoSupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : AccountService
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AdminInfoMapper adminInfoMapper;
    private final AdminInfoRepository adminInfoRepository;
    private final AdminInfoSupportRepository adminInfoSupportRepository;
    private final ViewCommonUtils viewCommonUtils;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<AdminInfoDto> findAccounts(String keyword, String startDate, String endDate, String admFlag, Pageable pageable) {
//        return adminInfoSupportRepository.findList(keyword, startDate, endDate, admFlag, pageable).map(adminInfoMapper::toDto);

        Page<AdminInfoDto> adminInfoDto = adminInfoSupportRepository.findList(keyword, startDate, endDate, admFlag, pageable)
                .map(adminInfo -> adminInfoMapper.toMarkedDto(adminInfo, keyword));

        return adminInfoDto;

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminInfoDto> findExcelList(String keyword, String startDate, String endDate) {
        return adminInfoSupportRepository.findExcelList(keyword, startDate, endDate).stream().map(adminInfoMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public long countAllAccounts(String useTf, String delTf) {
        return adminInfoRepository.countAllByUseTfAndDelTf(useTf, delTf);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public AdminInfoDto findAccountDetail(Long id) {
        return adminInfoMapper.toDto(adminInfoRepository.findById(id).orElse(null));
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public AdminInfoDto findUpAdmName(String upAdm) {
        return adminInfoMapper.toDto(adminInfoRepository.findByAdmId(upAdm).orElse(null));
    }

    @Transactional(value = "webTransactionManager")
    public String getAdmFlag(Long id) {

        return adminInfoRepository.findById(id).orElse(null).getAdmFlag();

    }

    @Transactional(value = "webTransactionManager")
    public void deleteAmdinInfo(Long id, Long delAdm) {
        AdminInfo adminInfo = adminInfoRepository.findById(id).orElse(null);

        if(adminInfo == null) {
            throw new BadRequestException("삭제 가능한 계정이 없습니다.");
        }

        adminInfo.deleteAdminInfo(delAdm);
    }

    @Transactional(value = "webTransactionManager")
    public void updateAdminInfo(Long id, AdminInfoDto param, Long upAdm) {
        AdminInfo adminInfo = adminInfoRepository.findById(id).orElse(null);

        if(adminInfo == null) {
            throw new BadRequestException("삭제 가능한 계정이 없습니다.");
        }

        adminInfo.updateAdmInfo(param, upAdm);
    }

    @Override
    @Transactional(value = "webTransactionManager", readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo user = adminInfoSupportRepository.findByIdWithGroup(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("can not find Account : " + username));
        return new Account(user);
    }

    @Transactional(value = "webTransactionManager")
    public void updateMyInfo(AdminInfoDto adminInfoDto) {
        AdminInfo adminInfo = adminInfoRepository.findById(adminInfoDto.getAdmNo())
                .orElseThrow(() -> new BadRequestException("사용자가 존재하지 않습니다."));
        adminInfoMapper.mergeToEntity(adminInfoDto, adminInfo);
    }
}
