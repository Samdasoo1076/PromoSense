package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.utils.HtmlParseUtils;
import com.skbroadband.doms.web.dto.*;
import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import com.skbroadband.doms.web.service.CampaignBaseTypeService;
import com.skbroadband.doms.web.service.CampaignCreateService;
import com.skbroadband.doms.web.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : CampaignCreateController
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class CampaignCreateController {
    private final CampaignCreateService campaignCreateService;
    private final CampaignBaseTypeService campaignBaseTypeService;
    private final FileService fileService;
    @Value("${application.upload.path.campaign}")
    private String uploadPath;
    @Value("${application.upload.path.capture}")
    private String capturePath;
    @Value("${server.etc-info.rending-url}")
    private String rendingUrl;
    @Value("${server.etc-info.direct-url}")
    private String directUrl;
    @Value("${server.etc-info.leave-url}")
    private String leaveUrl;
    @Value("${server.etc-info.calling-num}")
    private String callingNum;
    @Value("${server.etc-info.address}")
    private String address;
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
    @Value("${application.upload.image-type}")
    private String imageType;

    @GetMapping("/campaign/base/regist.view")
    @Log(content = "캠페인 저장 화면 조회", action = WorkType.Detail)
    public String getBaseRegistView(@LoginUser Account user, Model model) {

        model.addAttribute("userAdmInfo", user.getAdminInfo());

        return "campaign/campaign.base.create";

    }

    @GetMapping("/campaign/base/modify.view")
    @Log(content = "캠페인 저장 화면 조회", action = WorkType.Detail)
    public String getBaseModifyView(@RequestParam("id") Long id,
                                    @LoginUser Account user,
                                    Model model) {

        CampaignDto campaignDto = campaignCreateService.getCampaignInfo(id);
        CampaignMessageDto campaignMessageDto = campaignCreateService.getCampaignMessage(id);

        model.addAttribute("campaignInfo", campaignDto);
        // 다중으로 등록 되어진 url들의 pc, mobile을 구분 하기 위한 pc건수
        model.addAttribute("specDivider", campaignDto.getCampaignSpecUrlDtos().stream().filter(data -> data.getGubun().equals("P")).collect(toList()).size());
        model.addAttribute("exposureDivider", campaignDto.getCampaignExposureUrlDtos().stream().filter(data -> data.getGubun().equals("P")).collect(toList()).size());
        // 노출 시간을 문자열로 교체 하여 화면에서 contains로 비교 하여 체크함.
        model.addAttribute("timeStr", campaignDto.getCampaignExposureTimeDtos().stream().map(data -> data.getStartTime()).collect(Collectors.joining(",")));
        model.addAttribute("messageInfo", (campaignMessageDto == null)? CampaignMessageDto.builder().build() : campaignMessageDto);
        model.addAttribute("caId", id);
        model.addAttribute("userAdmInfo", user.getAdminInfo());

        return "campaign/campaign.base.modify";
    }

    @GetMapping("/campaign/message/regist.view")
    @Log(content = "캠페인 메시지 저장 화면 조회", action = WorkType.Detail)
    public String getMessageRegistView(@RequestParam("id") Long id,
                                       @RequestParam(value = "msgHisNo", required = false) Long msgHisNo,
                                       Model model) {

        Long caMsgTypeId = campaignCreateService.getCaMsgTypeId(id);
        CampaignBaseTypeDto campaignBaseTypeDto = campaignBaseTypeService.getCaMsgType(caMsgTypeId);
        CampaignMessageDto campaignMessageDto = campaignCreateService.getCampaignMessage(id);       // 수정시 조회
        CampaignDto campaignDto = campaignCreateService.getCampaignInfo(id);

        if(msgHisNo != null && msgHisNo != 0L) {  // 템플릿 조회적용시
            Long msgId = (campaignMessageDto != null)? campaignMessageDto.getId() : null;
            campaignMessageDto = campaignCreateService.getCampaignMessageHistory(msgHisNo);
            campaignMessageDto.setId(msgId);
        }

        // 멀티형 이미지 플로팅 이면 하위 테이블 데이터 조회
        if (campaignMessageDto != null && "8".equals(campaignMessageDto.getMsgType())) {
            log.info("========== multiList ==========");

            List<CampaignMessageMultiDto> multiList = campaignCreateService.getCampaignMessageMulti(id);

            if (multiList != null && !multiList.isEmpty()) {
                log.debug("========== multiList ========== {}", multiList);

                model.addAttribute("multiList", multiList);
            }
        }

        String moPreviewUrl = "";
        String pcPreviewUrl = "";
        switch (campaignDto.getCaGubun()) {
            case "BD":
                moPreviewUrl = bdMoAddress + id;
                pcPreviewUrl = bdPcAddress + id;
                break;
            case "TB":
                moPreviewUrl = tbMoAddress + id;
                pcPreviewUrl = tbPcAddress + id;
                break;
            case "BW":
            default:
                moPreviewUrl = bwMoAddress + id;
                pcPreviewUrl = bwPcAddress + id;
                break;
        }

//        String moPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdMoAddress + id : tbMoAddress + id);
//        String pcPreviewUrl = ("BD".equals(campaignDto.getCaGubun())? bdPcAddress + id : tbPcAddress + id);
        model.addAttribute("msgInfo", (campaignMessageDto == null)? CampaignMessageDto.builder().build() : campaignMessageDto);
        model.addAttribute("msgType", campaignBaseTypeDto.getBaseNo());
        model.addAttribute("caNo", id);
        model.addAttribute("pcPreviewUrl", pcPreviewUrl);
        model.addAttribute("moPreviewUrl", moPreviewUrl);

        return campaignBaseTypeDto.getContentUri();
    }

    @PostMapping("/campaign/base/save.do")
    @Log(content = "캠페인 기본정보 저장", action = WorkType.Registration)
    public ResponseEntity<?> saveBaseCampaign(@RequestBody CampaignDto campaignDto,
                                              @LoginUser Account user) {

        campaignDto.setVisibleCnt(0);
        campaignDto.setClickCnt(0);
        campaignDto.setCloseCnt(0);
        campaignDto.setPhoneCnt(0);
        campaignDto.setTargetVisitCnt(0);
        campaignDto.setLeaveCnt(0);
        campaignDto.setRegistCnt(0);
        campaignDto.setVisibleMoCnt(0);
        campaignDto.setClickMoCnt(0);
        campaignDto.setCloseMoCnt(0);
        campaignDto.setTargetVisitMoCnt(0);
        campaignDto.setLeaveMoCnt(0);
        campaignDto.setRegistMoCnt(0);
        campaignDto.setUseTf("N");
        campaignDto.setDelTf("N");
        campaignDto.setCaState("1");
        campaignDto.setUpAdm(user.getAdmNo());
        campaignDto.setUpDate(Instant.now());
        if("FIXED".equals(campaignDto.getTgType01()) || "FIXED".equals(campaignDto.getTgType02())) {
            campaignDto.setViewOrder(1);
        } else {
            campaignDto.setViewOrder(Integer.MAX_VALUE);
        }
        if("Y".equals(campaignDto.getCaAlldayYn())) {
            campaignDto.setCaStartDate(LocalDateTime.parse(
                    campaignDto.getCaStartDateStr().toString() + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant());
            campaignDto.setCaEndDate(LocalDateTime.parse(
                    campaignDto.getCaEndDateStr().toString() + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant());
        } else {
            campaignDto.setCaStartDate(LocalDateTime.parse(
                    campaignDto.getCaStartDateStr() + "T" +
                            campaignDto.getCaStartHour() + ":" +
                            campaignDto.getCaStartMin() + ":00").atZone(ZoneId.of("Asia/Seoul")).toInstant());
            campaignDto.setCaEndDate(LocalDateTime.parse(
                    campaignDto.getCaEndDateStr() + "T" +
                            campaignDto.getCaEndHour() + ":" +
                            campaignDto.getCaEndMin() + ":59").atZone(ZoneId.of("Asia/Seoul")).toInstant());
        }

        Long id = campaignCreateService.saveBaseCampaign(campaignDto);

        return Response.of(id);

    }

    @PostMapping("/campaign/base/modify.do")
    @Log(content = "캠페인 수정", action = WorkType.Modification)
    public ResponseEntity<?> modifyBaseCampaign(@RequestBody CampaignDto campaignDto,
                                                @LoginUser Account user) {

        campaignDto.setUpAdm(user.getAdmNo());
        campaignDto.setUpDate(Instant.now());
        if("Y".equals(campaignDto.getCaAlldayYn())) {
            campaignDto.setCaStartDate(LocalDateTime.parse(
                    campaignDto.getCaStartDateStr().toString() + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant());
            campaignDto.setCaEndDate(LocalDateTime.parse(
                    campaignDto.getCaEndDateStr().toString() + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant());
        } else {
            campaignDto.setCaStartDate(LocalDateTime.parse(
                    campaignDto.getCaStartDateStr() + "T" +
                            campaignDto.getCaStartHour() + ":" +
                            campaignDto.getCaStartMin() + ":00").atZone(ZoneId.of("Asia/Seoul")).toInstant());
            campaignDto.setCaEndDate(LocalDateTime.parse(
                    campaignDto.getCaEndDateStr() + "T" +
                            campaignDto.getCaEndHour() + ":" +
                            campaignDto.getCaEndMin() + ":59").atZone(ZoneId.of("Asia/Seoul")).toInstant());
        }

        campaignCreateService.modifyBaseCampaign(campaignDto);

        return Response.of(campaignDto.getId());
    }

    @PostMapping("/campaign/message/save.do")
    @Log(content = "캠페인 메시지 저장", action = WorkType.Registration)
    public ResponseEntity<?> saveMessageCampaign(CampaignMessageDto campaignMessageDto,
                                                 @RequestPart(value = "file", required = false) MultipartFile[] multipartFile,
                                                 @LoginUser Account user) {
        if(multipartFile != null) {
            for (MultipartFile file : multipartFile) {
                int filenameLen = file.getOriginalFilename().lastIndexOf(".");
                if(imageType.indexOf(file.getOriginalFilename().substring(filenameLen + 1).toLowerCase()) == -1) {
                    return Response.fail();
                }
            }
        }

        log.debug("+++ files +++ {}", multipartFile);

        String captureName = "";
        campaignMessageDto.setUseTf("Y");
        campaignMessageDto.setDelTf("N");

        if(campaignMessageDto.getMsgReviewType() != null && "T".equals(campaignMessageDto.getMsgReviewType())) {
            campaignMessageDto.setMsgReviewDays("1");
        }

        if (multipartFile != null) {
            if ("8".equals(campaignMessageDto.getMsgType())) {
                int rowNum = 0;
                for (MultipartFile file : multipartFile) {
                    if (file != null && !file.isEmpty()) {
                        String tmpFileName = file.getOriginalFilename();
                        int filenameLen = tmpFileName.lastIndexOf(".");
                        String tmpFile = tmpFileName.substring(0, filenameLen).replaceAll("[%;\\\\/.]", "");
                        String tmpFileExt = tmpFileName.substring(filenameLen + 1);

                        String fileName = fileService.fileUpload(file, uploadPath);

                        log.debug(" +++ fileName +++ {}", fileName);

                        Integer fileIdx = campaignMessageDto.getChangeImages().get(rowNum);

                        campaignMessageDto.getMsgImgNm01List().set(fileIdx, fileName);
                        campaignMessageDto.getMsgImgRnm01List().set(fileIdx, tmpFile + "." + tmpFileExt);

                        rowNum++;
                    }
                }
            } else {
                String tmpFileName = multipartFile[0].getOriginalFilename();
                int filenameLen = multipartFile[0].getOriginalFilename().lastIndexOf(".");
                String tmpFile = tmpFileName.substring(0, filenameLen).replaceAll("[%;\\\\/.]", "");
                String tmpFileExt = tmpFileName.substring(filenameLen + 1);

                String fileName = fileService.fileUpload(multipartFile[0], uploadPath);
                campaignMessageDto.setMsgImgRnm01(tmpFile + "." + tmpFileExt);
                campaignMessageDto.setMsgImgNm01(fileName);
            }
        }

        if("Y".equals(campaignMessageDto.getMsgSaveTf())) {
            captureName = fileService.fileCaptureUpload(campaignMessageDto.getMsgCapture(), capturePath);
            campaignMessageDto.setMsgCaptureName(captureName);
        }
        campaignMessageDto.setUpAdm(user.getAdmNo());
        campaignMessageDto.setUpDate(Instant.now());

        campaignMessageDto.setMsgPcHtml(HtmlParseUtils.getChangePcHtml(campaignMessageDto, rendingUrl, directUrl, leaveUrl, address));
        campaignMessageDto.setMsgMoHtml(HtmlParseUtils.getChangeMoHtml(campaignMessageDto, rendingUrl, directUrl, leaveUrl, callingNum, address));

        campaignCreateService.saveMessageCampaign(campaignMessageDto);

        return Response.ok();

    }

    @PostMapping("/campaign/message/temp/save.do")
    @Log(content = "캠페인 메시지 임시 저장", action = WorkType.Registration)
    public ResponseEntity<?> saveMessageCampaignTemp(CampaignMessageDto campaignMessageDto,
                                                 @RequestPart(value = "file", required = false) MultipartFile[] multipartFile,
                                                 @LoginUser Account user) {

        campaignMessageDto.setUseTf("Y");
        campaignMessageDto.setDelTf("N");

        log.debug("+++ files +++ {}", multipartFile);

        if(multipartFile != null) {
            if ("8".equals(campaignMessageDto.getMsgType())) {
                int rowNum = 0;
                for (MultipartFile file : multipartFile) {
                    if (file != null && !file.isEmpty()) {
                        String tmpFileName = file.getOriginalFilename();
                        int filenameLen = tmpFileName.lastIndexOf(".");
                        String tmpFile = tmpFileName.substring(0, filenameLen).replaceAll("[%;\\\\/.]", "");
                        String tmpFileExt = tmpFileName.substring(filenameLen + 1);

                        String fileName = fileService.fileUpload(file, uploadPath);

                        log.debug(" +++ fileName +++ {}", fileName);

                        Integer fileIdx = campaignMessageDto.getChangeImages().get(rowNum);

                        campaignMessageDto.getMsgImgNm01List().set(fileIdx, fileName);
                        campaignMessageDto.getMsgImgRnm01List().set(fileIdx, tmpFile + "." + tmpFileExt);

                        rowNum++;
                    }
                }
            } else {
                String fileName = fileService.fileUpload(multipartFile[0], uploadPath);
                campaignMessageDto.setMsgImgRnm01(multipartFile[0].getOriginalFilename());
                campaignMessageDto.setMsgImgNm01(fileName);
            }
        }

        campaignMessageDto.setUpAdm(user.getAdmNo());
        campaignMessageDto.setUpDate(Instant.now());

        campaignMessageDto.setMsgPcHtml(HtmlParseUtils.getChangePcHtml(campaignMessageDto, rendingUrl, directUrl, leaveUrl, address));
        campaignMessageDto.setMsgMoHtml(HtmlParseUtils.getChangeMoHtml(campaignMessageDto, rendingUrl, directUrl, leaveUrl, callingNum, address));

        campaignCreateService.saveMessageCampaign(campaignMessageDto);

        return Response.ok();

    }

    @GetMapping("/campaign/popup/historyMessage")
    @Log(content = "캠페인(message) 템플릿 리스트 조회", action = WorkType.List)
    public String getHistoryMessage(@RequestParam("msgType") String msgType,
                                    Model model) {

        String pageUri = "";
        List<CampaignMessageHistoryDto> historys = campaignCreateService.getHistoryMessage(msgType);

        model.addAttribute("historys", historys);

        if("1".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.vertical.popup";
        } else if("2".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.horizontal.popup";
        } else if("3".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.image.popup";
        } else if("4".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.text.popup";
        } else if("5".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.mini.popup";
        } else if("6".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.banner.popup";
        } else if("7".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.action.popup";
        } else if("8".equals(msgType)) {
            pageUri = "campaign/popup/campaign.message.multi.popup";
        }

        return pageUri;

    }

    @PostMapping("/campaign/popup/history/delete")
    @Log(content = "캠페인(message) 템플릿 삭제", action = WorkType.Delete)
    public ResponseEntity<?> deleteHistoryMessage(@RequestBody List<CampaignMessageHistoryDto> campaignMessageHistoryDtos,
                                                  @LoginUser Account user) {

        for(CampaignMessageHistoryDto campaignMessageHistoryDto : campaignMessageHistoryDtos) {
            campaignMessageHistoryDto.setDelAdm(user.getAdmNo());
            campaignMessageHistoryDto.setDelDate(Instant.now());
            campaignMessageHistoryDto.setUseTf("N");
            campaignMessageHistoryDto.setDelTf("Y");
        }

        campaignCreateService.deleteHistory(campaignMessageHistoryDtos);

        return Response.ok();

    }

}
