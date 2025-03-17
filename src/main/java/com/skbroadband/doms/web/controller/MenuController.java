package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.ImageUri;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.utils.CommUtils;
import com.skbroadband.doms.web.dto.AdminMenuDto;
import com.skbroadband.doms.web.service.FileService;
import com.skbroadband.doms.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : MenuController
 * @Program :
 * @Date : 2023-01-16
 * @Comment :
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/settings/menu")
public class MenuController {
    private final MenuService menuService;
    private final FileService fileService;

    @Value("${application.upload.path.menu-icon}")
    String uploadPath;
    /**
     * 메뉴목록조회
     *
     * @param model
     * @return
     */
    @GetMapping("/list.do")
    @Log(content = "메뉴목록 조회", action = WorkType.List)
    public String list(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        model.addAttribute("divImg", ImageUri.MENU_ICON.getDivision());
        return "settings/menu.list";
    }

    /**
     * 대분류등록 펍업
     *
     * @return
     */
    @GetMapping("/popup/major/detail.view")
    public String majorCategory(@RequestParam(value = "menu_id", required = false) Long menuId, Model model) {
        model.addAttribute("menu", Objects.isNull(menuId)?AdminMenuDto.builder().build():menuService.getMenu(menuId));
        return "settings/popup/major.menu.save.popup";
    }

    /**
     * 중분류등록 팝업
     *
     * @return
     */
    @GetMapping("/popup/middle/detail.view")
    public String middleCategory(@RequestParam(value = "menu_id", required = false) Long menuId, Model model) {
        model.addAttribute("menu", Objects.isNull(menuId)?AdminMenuDto.builder().build():menuService.getMenu(menuId));
        return "settings/popup/middle.menu.save.popup";
    }

    /**
     * 메뉴 순서변경 화면
     *
     * @return
     */
    @GetMapping("/popup/order/detail.view")
    @Log(content = "메뉴 조회", action = WorkType.Detail)
    public String majorOrder(String type, @RequestParam(value = "menu_id", required = false) Long menuId, Model model) {
        if("major".equals(type)) {
            model.addAttribute("menus", menuService.get1DepthMenus());
        } else if("middle".equals(type) && !Objects.isNull(menuId)) {
            model.addAttribute("menus", menuService.get2DepthMenus(menuId));
        } else {
            throw new BadRequestException("파라미터가 유효하지 않습니다.");
        }

        return "settings/popup/menu.order.popup";
    }

    /**
     * 메뉴 순서변경
     *
     * @param menuIds
     * @return
     */
    @PostMapping("/order/update.do")
    @Log(content = "메뉴순서 변경", action = WorkType.Modification)
    public String changeOrder(@RequestParam(value = "menuId") Long[] menuIds, @LoginUser Account account, String acl) {
        if(menuIds != null && menuIds.length != 0) {
            menuService.changeOrder(menuIds, account.getAdmNo());
        }

        return "redirect:/settings/menu/list.do?acl="+acl;
    }

    /**
     * 대분류 메뉴 등록/수정
     * @return
     */
    @PostMapping("/major/save.do")
    @Log(content = "메뉴 등록", action = WorkType.Registration)
    public ResponseEntity<?> addMajorCategory(AdminMenuDto adminMenuDto, @RequestPart MultipartFile icon) {
        if(Objects.isNull(adminMenuDto.getId()) && menuService.chkDupMenuCode(adminMenuDto.getMenuCode())) {
            throw new BadRequestException("동일한 코드가 있습니다.");
        }
        if(!StringUtils.hasText(adminMenuDto.getMenuName())) {
            throw new BadRequestException("메뉴명은 필수입니다.");
        }
        if(!StringUtils.hasText(adminMenuDto.getMenuCode())) {
            throw new BadRequestException("코드는 필수입니다.");
        }
        if(StringUtils.hasText(adminMenuDto.getMenuUrl()) && CommUtils.isInValidUri(adminMenuDto.getMenuUrl())) {
            throw new BadRequestException("메뉴 URL은 유효하지 않습니다.");
        }

        if(!icon.isEmpty()) {
            if(!icon.getContentType().startsWith("image")) {
                throw new BadRequestException("업로드한 파일은 이미지가 아닙니다.");
            }

            if(!Objects.isNull(adminMenuDto.getId())) {
                AdminMenuDto menuDto = menuService.getMenu(adminMenuDto.getId());
                if(StringUtils.hasText(menuDto.getMenuImg())) {
                    fileService.deleteFile(
                            Paths.get(ImageUri.MENU_ICON.getPath()
                                    + File.separator + menuDto.getMenuImg()));
                }
            }
            adminMenuDto.setMenuImg(fileService.fileUpload(icon, uploadPath));
        }

        if(Objects.isNull(adminMenuDto.getId())) { // 등록
            adminMenuDto.setMenuCode(adminMenuDto.getMenuCode().toUpperCase());
            adminMenuDto.setMenuDepth(1);
            adminMenuDto.setUseTf("Y");
            adminMenuDto.setDelTf("N");
            adminMenuDto.setMenuOrder(Integer.MAX_VALUE);

            menuService.addMenu(adminMenuDto);
        } else { // 수정
            menuService.modifyMenu(adminMenuDto);
        }
        
        return Response.ok();
    }

    /**
     * 중분류 메뉴 등록/수정
     *
     * @param adminMenuDto
     * @return
     */
    @PostMapping("/middle/save.do")
    @Log(content = "메뉴 등록", action = WorkType.Registration)
    public ResponseEntity<?> addMiddleCategory(AdminMenuDto adminMenuDto) {
        if(Objects.isNull(adminMenuDto.getId()) && menuService.chkDupMenuCode(adminMenuDto.getMenuCode())) {
            throw new BadRequestException("동일한 코드가 있습니다.");
        }
        if(Objects.isNull(adminMenuDto.getMenuParentNo())) {
            throw new BadRequestException("상위 메뉴를 선택하세요.");
        }
        if(StringUtils.isEmpty(adminMenuDto.getMenuName())) {
            throw new BadRequestException("메뉴명은 필수입니다.");
        }
        if(StringUtils.isEmpty(adminMenuDto.getMenuCode())) {
            throw new BadRequestException("코드는 필수입니다.");
        }
        if(CommUtils.isInValidUri(adminMenuDto.getMenuUrl())) {
            throw new BadRequestException("메뉴 URL은 유효하지 않습니다.");
        }

        if(Objects.isNull(adminMenuDto.getId())) { // 등록
            adminMenuDto.setMenuCode(adminMenuDto.getMenuCode().toUpperCase());
            adminMenuDto.setMenuDepth(2);
            adminMenuDto.setUseTf("Y");
            adminMenuDto.setDelTf("N");
            adminMenuDto.setMenuOrder(Integer.MAX_VALUE);

            menuService.addMenu(adminMenuDto);
        } else {
            menuService.modifyMenu(adminMenuDto);
        }

        return Response.ok();
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId
     * @return
     */
    @PostMapping("/delete.do")
    @Log(content = "메뉴 삭제", action = WorkType.Delete)
    public ResponseEntity<?> deleteMiddleCategory(Long menuId) {
        menuService.deleteMenu(menuId);

        return Response.ok();
    }
}
