package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.exception.UnauthorizedException;
import com.skbroadband.doms.web.dto.AdminGroupDto;
import com.skbroadband.doms.web.dto.AdminMenuDto;
import com.skbroadband.doms.web.dto.AdminMenuRightDto;
import com.skbroadband.doms.web.entity.AdminGroup;
import com.skbroadband.doms.web.entity.AdminMenuRight;
import com.skbroadband.doms.web.mapper.AdminGroupMapper;
import com.skbroadband.doms.web.mapper.AdminMenuRightMapper;
import com.skbroadband.doms.web.repository.AdminGroupRepository;
import com.skbroadband.doms.web.repository.AdminMenuRightRepository;
import com.skbroadband.doms.web.repository.AdminMenuRightSupportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : AuthService
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AdminMenuRightRepository adminMenuRightRepository;
    private final AdminMenuRightMapper adminMenuRightMapper;
    private final AdminGroupMapper adminGroupMapper;
    private final AdminGroupRepository adminGroupRepository;
    private final AdminMenuRightSupportRepository adminMenuRightSupportRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminGroupDto> findAllList(String useTf, String delTf) {

        return adminGroupRepository.findByUseTfAndDelTfOrderByRegDate(useTf, delTf).stream().map(adminGroupMapper::toDto).collect(Collectors.toList());

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<AdminGroupDto> findList(Pageable pageable) {

        return adminGroupRepository.findByUseTfAndDelTf("Y", "N", pageable).map(adminGroupMapper::toDto);
        //return adminGroupSupportRepository.findList(pageable).map(adminGroupMapper::toDto);

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public boolean isGroupName(String groupName) {
        return adminGroupRepository.existsByGroupName(groupName);
    }

    @Transactional(value = "webTransactionManager")
    public void saveGroup(AdminGroupDto adminGroupDto) {
        adminGroupRepository.save(AdminGroup.builder()
                .groupName(adminGroupDto.getGroupName())
                .useTf("Y")
                .delTf("N")
                .build());
    }

    @Transactional(value = "webTransactionManager")
    public void deleteGroup(Long id, Long delAdm) {
        AdminGroup adminGroup = adminGroupRepository.findById(id).orElse(null);

        if(adminGroup == null) {
            throw new BadRequestException("삭제 가능한 권한그룹이 없습니다.");
        }

        adminMenuRightRepository.deleteByGroupNo(adminGroup);
        adminGroup.deleteGroup(delAdm);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminMenuRightDto> findMenuAuthList(@RequestParam("groupNo") Long groupNo) {

        return adminMenuRightSupportRepository.getAuthMenuList(groupNo);

    }

    @CacheEvict(cacheNames = {"aclCache", "menuCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void saveAllAuth(List<AdminMenuRightDto> adminMenuRightDtos) {
        List<AdminMenuRight> paramList = new ArrayList<AdminMenuRight>();
        for(AdminMenuRightDto data : adminMenuRightDtos) {
            AdminMenuRight adminMenuRight = adminMenuRightMapper.toSetIdEntity(data);

            paramList.add(adminMenuRight);
        }

        adminMenuRightRepository.saveAll(paramList);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminMenuDto> findMenusByGroupNo() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String acl = request.getParameter("acl");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }

        Long groupNo = ((Account)authentication.getPrincipal()).getGroupNo();
        if(Objects.isNull(groupNo)) {
            return Collections.emptyList();
        }

        List<AdminMenuDto> adminMenus = adminMenuRightSupportRepository.menuByRight(groupNo);
        List<AdminMenuDto> root = adminMenus.stream()
                .filter(menuDto -> Objects.isNull(menuDto.getMenuParentNo()))
                .sorted(Comparator.comparing(AdminMenuDto::getMenuOrder))
                .collect(Collectors.toList());

        Map<Long, List<AdminMenuDto>> childrenMap = adminMenus.stream()
                .filter(menuDto -> !Objects.isNull(menuDto.getMenuParentNo()))
                .collect(Collectors.groupingBy(AdminMenuDto::getMenuParentNo));

        root.forEach(rootDto -> {
            if(rootDto.getMenuCode().equals(acl)) {
                rootDto.setSelected(true);
            }

            List<AdminMenuDto> children = childrenMap.get(rootDto.getId());
            if(!Objects.isNull(children)) {
                children = children.stream()
                        .map(dto -> {
                            if (dto.getMenuCode().equals(acl)) {
                                dto.setSelected(true);
                                rootDto.setSelected(true);
                            }
                            return dto;
                        })
                        .sorted(Comparator.comparing(AdminMenuDto::getMenuOrder))
                        .collect(Collectors.toList());

                rootDto.setSubMenu(children);
            }
        });

        return root;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Optional<AdminMenuRightDto> findAccessControl(String menuCode, Long groupNo) {
        return Optional.ofNullable(adminMenuRightMapper
                .toDtoNoLazy(adminMenuRightRepository
                        .findByMenuNo_MenuCodeAndMenuNo_UseTfAndMenuNo_DelTfAndGroupNo_IdAndGroupNo_UseTfAndGroupNo_DelTf(
                                menuCode,
                                "Y",
                                "N",groupNo,
                                "Y",
                                "N")));
    }
}
