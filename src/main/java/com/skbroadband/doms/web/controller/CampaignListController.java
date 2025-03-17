package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.web.dto.CampaignDto;
import com.skbroadband.doms.web.dto.CampaignMessageDto;
import com.skbroadband.doms.web.service.CampaignCreateService;
import com.skbroadband.doms.web.service.CampaignListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : CampaignListController
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class CampaignListController {
    private final CampaignListService campaignListService;
    private final CampaignCreateService campaignCreateService;

    @Value("${server.etc-info.bd-pc-address}")
    private String bdPcAddress;
    @Value("${server.etc-info.bd-mo-address}")
    private String bdMoAddress;
    @Value("${server.etc-info.tb-pc-address}")
    private String tbPcAddress;
    @Value("${server.etc-info.tb-mo-address}")
    private String tbMoAddress;
    @Value("${server.etc-info.bw-pc-address}")
    private String bwPcAddress;
    @Value("${server.etc-info.bw-mo-address}")
    private String bwMoAddress;

    @Log(content = "캠패인 목록 조회", action = WorkType.List)
    @GetMapping("/campaign/list/list.do")
    public String campaignList(   @RequestParam(value = "keyword", defaultValue = "") String keyword
                                , @RequestParam(value = "startDate", defaultValue = "") String startDate
                                , @RequestParam(value = "endDate", defaultValue = "") String endDate
                                , @RequestParam(value = "caGubun", defaultValue = "") String caGubun
                                , @RequestParam(value = "taskFlag", defaultValue = "upDate") String taskFlag
                                , @RequestParam(value = "orderFlag", defaultValue = "DESC") String orderFlag
                                , @RequestParam(value = "caFlag", defaultValue = "3") String caFlag
                                , @PageableDefault(size = 30) Pageable pageable
                                , @LoginUser Account userAdmInfo
                                , Model model) {

        if("".equals(caGubun)) {
            if ("C".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "TB";
            } else if ("B".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BD";
            } else if ("A".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BW";
            } else if ("D".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BW";
            } else {
                caGubun = "";
            }
        }
        //커스텀 정렬조건
        Sort sort;
        if(!taskFlag.equals("") && !orderFlag.equals("ASC")){
            sort = Sort.by(taskFlag).descending();
        }else if(taskFlag.equals("") && !orderFlag.equals("ASC")){
            sort = Sort.by("upDate").descending();
        }else if(taskFlag.equals("") && orderFlag.equals("ASC")){
            sort = Sort.by("upDate").ascending();
        }else if(taskFlag.equals("") && orderFlag.equals("")){
            sort = Sort.by("upDate").descending();
        }else if(!taskFlag.equals("") && orderFlag.equals("ASC")){
            sort = Sort.by(taskFlag).ascending();
        }else{
            sort = Sort.by(taskFlag).descending();
        }

//        //시작일 현재날짜의 30일 이전
//        if(!StringUtils.hasText(startDate)) {
//            startDate = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        }
//
//        //종료일 현재날짜
//        if(!StringUtils.hasText(endDate)) {
//            endDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
//        }

        Pageable pageableAfter = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        model.addAttribute("campaigns", campaignListService.findCampaigns(keyword, startDate, endDate, caGubun, caFlag, pageableAfter));
        campaignListService.findCampaigns(keyword, startDate, endDate, caGubun, caFlag, pageableAfter).stream().forEach(campaign -> {
            log.info(campaign.toString());
        });

        model.addAttribute("tabCount", campaignListService.tabCount(keyword, startDate, endDate, caGubun));
        model.addAttribute("userAdmInfo", userAdmInfo.getAdminInfo());

        return "campaign/campaign.list";
    }

    @Log(content = "캠패인 목록 상태 변경", action = WorkType.Modification)
    @GetMapping("/campaign/campaign/switch.do")
    public ResponseEntity<?> state(@RequestParam("caNo") Long caNo,
                                   @RequestParam("useTf") String useTf,
                                   @LoginUser Account user) {

       campaignListService.changeCaState(caNo, useTf, user.getAdmNo());
        return Response.ok();
    }

    @Log(content = "캠패인 목록 삭제", action = WorkType.Delete)
    @GetMapping("/campaign/list/delete.do")
    public ResponseEntity<?> delete(@RequestParam("caNo") Long caNo,
                                    @LoginUser Account user) {

        campaignListService.deleteCampaign(caNo, user.getAdmNo());
        return Response.ok();
    }

    @Log(content = "캠패인 요약 상세화면", action = WorkType.Detail)
    @GetMapping("/campaign/popup/base.do")
    public String campaignPopupBase(@RequestParam(value = "caNo", defaultValue = "") Long caNo
            , Model model) {
        CampaignDto campaignDto = campaignListService.findBasePopup(caNo);

        String moPreviewUrl;
        String pcPreviewUrl;
        switch (campaignDto.getCaGubun()) {
            case "BD":
                moPreviewUrl = bdMoAddress;
                pcPreviewUrl = bdPcAddress;
                break;
            case "TB":
                moPreviewUrl = tbMoAddress;
                pcPreviewUrl = tbPcAddress;
                break;
            case "BW":
            default:
                moPreviewUrl = bwMoAddress;
                pcPreviewUrl = bwPcAddress;
                break;
        }
//        String moPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdMoAddress + caNo : tbMoAddress + caNo);
//        String pcPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdPcAddress + caNo : tbPcAddress + caNo);

        model.addAttribute("campaign", campaignDto);
        model.addAttribute("caNo", caNo);
        model.addAttribute("pcPreviewUrl", pcPreviewUrl);
        model.addAttribute("moPreviewUrl", moPreviewUrl);

        return "campaign/popup/campaign.base.popup";
    }

    @Log(content = "노출순위 설정 팝업", action = WorkType.Detail)
    @GetMapping("/campaign/popup/view/order.do")
    public String campaignPopupViewOrder(@RequestParam(value = "keyword", defaultValue = "") String keyword
                                        , @RequestParam(value = "startDate", defaultValue = "") String startDate
                                        , @RequestParam(value = "endDate", defaultValue = "") String endDate
                                        , @RequestParam(value = "orderFlag", defaultValue = "ASC") String orderFlag
                                        , @RequestParam(value = "caFlag", defaultValue = "2") String caFlag
                                        , @RequestParam(value = "caGubun", defaultValue = "") String caGubun
                                        , @RequestParam(value = "count", defaultValue = "30") String count
                                        , @RequestParam(value = "size", defaultValue = "30") String size
                                        , @RequestParam(value = "page", defaultValue = "1") String page
                                        , @PageableDefault(size = 30) Pageable pageable
                                        , @LoginUser Account userAdmInfo
                                        , Model model){



//        //시작일 현재날짜의 30일 이전
//        if(!StringUtils.hasText(startDate)) {
//            startDate = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        }
//
//        //종료일 현재날짜
//        if(!StringUtils.hasText(endDate)) {
//            endDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
//        }


        if("".equals(caGubun)) {
            if ("C".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "TB";
            } else if ("B".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BD";
            } else if ("A".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BW";
            } else if ("D".equals(userAdmInfo.getAdminInfo())) {
                caGubun = "BW";
            } else {
                caGubun = "";
            }
        }


        Sort sort;
        sort = Sort.by("viewOrder").ascending();
        sort.and(Sort.by("upDate").descending());
        int pageSize = Integer.parseInt(count);

        Pageable pageableAfter = PageRequest.of(0, pageSize , sort);

        model.addAttribute("campaigns", campaignListService.findActiveCampaigns(keyword, startDate, endDate, caGubun, caFlag, pageableAfter));

        return "campaign/popup/campaign.view.order.popup";
    }

    @PostMapping("/campaign/popup/view/order/update.do")
    @Log(content = "노출순위 설정 변경", action = WorkType.Modification)
    public String changeOrder(@RequestParam(value = "caNo") Long[] caNos
                            , @LoginUser Account account
                            , @RequestParam(value = "acl") String acl
                            , @RequestParam(value = "keyword", defaultValue = "") String keyword
                            , @RequestParam(value = "startDate", defaultValue = "") String startDate
                            , @RequestParam(value = "endDate", defaultValue = "") String endDate
                            , @RequestParam(value = "taskFlag", defaultValue = "upDate") String taskFlag
                            , @RequestParam(value = "orderFlag", defaultValue = "ASC") String orderFlag
                            , @RequestParam(value = "caGubun", defaultValue = "") String caGubun
                            ) {
        if(caNos != null && caNos.length != 0) {
            campaignListService.changeViewOrder(caNos, account.getAdmNo());
        }

        String uri = UriComponentsBuilder
                .fromUriString("/campaign/list/list.do?acl="+acl+"&caFlag=2&keyword="+keyword+"&startDate="+startDate+"&endDate="+endDate+"&taskFlag="+taskFlag+"&orderFlag="+orderFlag+"&caGubun="+caGubun)
                .toUriString();

        return "redirect:" + uri;
    }

    @Log(content = "캠패인 상세화면", action = WorkType.Detail)
    @GetMapping("/campaign/list/detail.do")
    public String campaignListDetail(@RequestParam(value = "caNo"
            , defaultValue = "") Long caNo
            , @RequestParam(value = "caGubun", defaultValue = "") String caGubun
            , Model model) {

        CampaignDto campaignDto = campaignListService.getCampaignInfo(caNo);
        String moPreviewUrl = "";
        String pcPreviewUrl = "";
        switch (campaignDto.getCaGubun()) {
            case "BD":
                moPreviewUrl = bdMoAddress;
                pcPreviewUrl = bdPcAddress;
                break;
            case "TB":
                moPreviewUrl = tbMoAddress;
                pcPreviewUrl = tbPcAddress;
                break;
            case "BW":
            default:
                moPreviewUrl = bwMoAddress;
                pcPreviewUrl = bwPcAddress;
                break;
        }
//        String moPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdMoAddress + caNo : tbMoAddress + caNo);
//        String pcPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdPcAddress + caNo : tbPcAddress + caNo);

        model.addAttribute("campaignInfo", campaignDto);
        model.addAttribute("pcPreviewUrl", pcPreviewUrl);
        model.addAttribute("moPreviewUrl", moPreviewUrl);

        return "campaign/campaign.list.detail";
    }


    @GetMapping("/campaign/list/preview/popup")
    @Log(content = "미리보기 팝업", action = WorkType.List)
    public String campaignListPreviewPopup(@RequestParam(value = "caNo", defaultValue = "") Long caNo
            , @RequestParam(value = "msgType", defaultValue = "") Long msgType
            , @RequestParam(value = "popupId", defaultValue = "") String popupId
            , Model model){


        CampaignMessageDto campaignMessageDto = campaignCreateService.getCampaignMessage(caNo);       // 수정시 조회

        model.addAttribute("msgInfo", (campaignMessageDto == null)? CampaignMessageDto.builder().build() : campaignMessageDto);
        model.addAttribute("msgType", msgType);
        model.addAttribute("popupId", popupId);

        return "campaign/popup/campaign.list.preview.popup";
    }



}
