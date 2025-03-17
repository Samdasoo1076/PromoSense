package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.dto.AcceptDto;
import com.skbroadband.doms.web.service.AcceptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : AcceptController
 * @Program :
 * @Date : 2023-01-25
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AcceptController {
    private final AcceptService acceptService;


    @Log(content = "접근 IP 관리 조회", action = WorkType.List)
    @GetMapping("/settings/accept/list.do")
    public String acceptList(@PageableDefault(size = 30) Pageable pageable
                            , Model model) {

        model.addAttribute("accepts", acceptService.findAllList("Y","N" , pageable));

        return "settings/accept.list";
    }

    @Log(content = "접근 IP 관리 등록", action = WorkType.Registration)
    @PostMapping("/settings/accept/write.do")
    public ResponseEntity<?> addAccept(@RequestBody AcceptDto acceptDto
                        , @RequestParam(value="acceptIp", defaultValue="") String acceptIp
                        , BindingResult bindingResult) throws Exception {


        //동일 아이피 체크
        Map<String, Object> id = acceptService.chkAcceptIp(acceptDto.getAcceptIp());
        if((Boolean)id.get("exists")) {
            throw new BadRequestException((String) id.get("message"));
        }

        if (bindingResult.hasErrors()) {
            return Response.ok();
        }
        acceptDto.setAcceptIp(acceptDto.getAcceptIp());
        acceptDto.setUseTf("Y");
        acceptDto.setDelTf("N");

        acceptService.addAccept(acceptDto);

        return Response.ok();
    }

    @Log(content = "접근 IP 관리 삭제", action = WorkType.Delete)
    @GetMapping("/settings/accept/delete.do")
    public ResponseEntity<?> delete(@RequestParam("seqNo") Long seqNo,
                                    @LoginUser Account user) {

        acceptService.deleteAcceptIp(seqNo, user.getAdmNo());

        return Response.ok();
    }

}