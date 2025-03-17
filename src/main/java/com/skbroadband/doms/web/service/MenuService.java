package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.dto.AdminMenuDto;
import com.skbroadband.doms.web.entity.AdminMenu;
import com.skbroadband.doms.web.mapper.AdminMenuMapper;
import com.skbroadband.doms.web.repository.AdminMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : MenuService
 * @Program :
 * @Date : 2023-01-16
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class MenuService {
    private final AdminMenuRepository adminMenuRepository;
    private final AdminMenuMapper adminMenuMapper;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminMenuDto> getAllMenus() {
        return adminMenuMapper.toDtoList(
                adminMenuRepository.findByUseTfAndDelTfAndMenuParentNoIsNullOrderByMenuOrderAsc("Y", "N")
        );
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminMenuDto> get1DepthMenus() {
        return adminMenuRepository.findByUseTfAndDelTfAndMenuParentNoIsNullOrderByMenuOrderAsc("Y", "N").stream()
                .map(adminMenuMapper::toDtoByNoLazy)
                .collect(Collectors.toList());
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<AdminMenuDto> get2DepthMenus(Long menuId) {
        return adminMenuRepository.findByUseTfAndDelTfAndMenuParentNoOrderByMenuOrderAsc("Y", "N", menuId).stream()
                .map(adminMenuMapper::toDtoByNoLazy)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = {"aclCache", "menuCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void addMenu(AdminMenuDto adminMenuDto) {
        adminMenuRepository.save(adminMenuMapper.toEntity(adminMenuDto));
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Boolean chkDupMenuCode(String menuCode) {
        return adminMenuRepository.existsAdminMenuByMenuCodeIgnoreCaseAndUseTfAndDelTf(menuCode, "Y", "N");
    }

    @CacheEvict(cacheNames = {"aclCache", "menuCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void changeOrder(Long[] menuIds, Long admNo) {
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(menuIds).forEach(menuId -> adminMenuRepository.changeOrder(menuId, index.getAndIncrement(), admNo));
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public AdminMenuDto getMenu(Long menuId) {
        return adminMenuMapper.toDtoByNoLazy(adminMenuRepository.findById(menuId)
                .orElseThrow(() -> new BadRequestException("조회 데이타가 없습니다.")));
    }

    @CacheEvict(cacheNames = {"aclCache", "menuCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void modifyMenu(AdminMenuDto adminMenuDto) {
        AdminMenu adminMenu = adminMenuRepository.findById(adminMenuDto.getId())
                .orElseThrow(() -> new BadRequestException("조회 데이타가 없습니다."));

        adminMenuMapper.mergeToEntity(adminMenuDto, adminMenu);
    }

    @CacheEvict(cacheNames = {"aclCache", "menuCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void deleteMenu(Long menuId) {
        AdminMenu adminMenu = adminMenuRepository.findById(menuId).orElseThrow(() -> new BadRequestException("데이타가 존재하지 않습니다."));
        List<AdminMenu> adminMenus = adminMenuRepository.findByUseTfAndDelTfAndMenuParentNoOrderByMenuOrderAsc("Y", "N", adminMenu.getId());
        if(adminMenus.isEmpty()) {
            adminMenu.delete();
            return;
        }

        throw new BadRequestException("하위 메뉴가 존재하여 삭제할 수 없습니다.");
    }
}
