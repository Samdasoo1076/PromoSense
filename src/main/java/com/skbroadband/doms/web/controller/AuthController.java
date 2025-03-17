package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.dto.AdminGroupDto;
import com.skbroadband.doms.web.dto.AdminMenuRightDto;
import com.skbroadband.doms.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : AuthController
 * @Program :
 * @Date : 2023-02-02
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Log(content = "권한 그룹 조회", action = WorkType.List)
    @GetMapping("/settings/auth/list.do")
    public String authList(@PageableDefault(sort = "regDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
                           Model model) {

        model.addAttribute("auths", authService.findList(pageable));

        return "settings/auth.list";
    }

    @Log(content = "권한 그룹 존재 여부 조회", action = WorkType.Detail)
    @GetMapping("/settings/auth/group/check/{groupName}")
    public ResponseEntity<?> isGroupName(@PathVariable("groupName") String groupName) {
        Boolean bool = authService.isGroupName(groupName);

        if(bool) {
            throw new BadRequestException("같은 권한 그룹명이 존재 합니다.");
        }

        return Response.ok();
    }

    @Log(content = "권한 그룹 생성", action = WorkType.Registration)
    @PostMapping("/settings/auth/write.do")
    public ResponseEntity<?> save(@RequestBody AdminGroupDto adminGroupDto) {
        authService.saveGroup(adminGroupDto);

        return Response.ok();
    }

    @Log(content = "권한 그룹 삭제", action = WorkType.Delete)
    @GetMapping("/settings/auth/delete.do")
    public ResponseEntity<?> delete(@RequestParam("id") Long id,
                                    @LoginUser Account user) {

        authService.deleteGroup(id, user.getAdmNo());

        return Response.ok();
    }

    @Log(content = "메뉴 권한 조회", action = WorkType.List)
    @GetMapping("/settings/auth/popup/detail.view")
    public String authPopup(@RequestParam("groupNo") Long groupNo, Model model) {
        model.addAttribute("groupNo", groupNo);
        model.addAttribute("menus", authService.findMenuAuthList(groupNo));

        return "settings/popup/auth.save.popup";
    }

    @Log(content = "메뉴 권한 생성/수정", action = WorkType.Modification)
    @PostMapping("/settings/auth/popup/save.do")
    public ResponseEntity<?> popSave(@RequestBody AdminMenuRightDto[] adminMenuRightDtos) {
        authService.saveAllAuth(Arrays.asList(adminMenuRightDtos));

        return Response.ok();
    }

}
