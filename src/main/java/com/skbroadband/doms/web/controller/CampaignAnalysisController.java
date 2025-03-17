package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.utils.CommCodeUtils;
import com.skbroadband.doms.web.dto.*;
import com.skbroadband.doms.web.service.CampaignAnalysisService;
import com.skbroadband.doms.web.service.CampaignCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class CampaignAnalysisController {

    private final CampaignAnalysisService campaignAnalysisService;
    private final CampaignCreateService campaignCreateService;
    private final CommCodeUtils commCodeUtils;

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

    @GetMapping("/campaign/analysis/list.do")
    @Log(content = "캠패인 분석 목록 조회", action = WorkType.List)
    public String getAnalysisList(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                  @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                  @RequestParam(value = "sort_item", defaultValue = "ID") String sortItem,
                                  @RequestParam(value = "sort_gubun", defaultValue = "DESC") String sortGubun,
                                  @RequestParam(value = "caGubun", defaultValue = "") String caGubun,
                                  @LoginUser Account user,
                                  @PageableDefault(size = 30) Pageable pageable,
                                  Model model) {

        Sort sort = ("ASC".equals(sortGubun))? Sort.by(sortItem).ascending() : Sort.by(sortItem).descending();
        Pageable pageableAfter = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if("".equals(caGubun)) {
            if ("C".equals(user.getAdminInfo())) {
                caGubun = "TB";

            } else if ("B".equals(user.getAdminInfo())) {
                caGubun = "BD";

            } else if ("A".equals(user.getAdminInfo())) {
                caGubun = "BW";

            } else {
                caGubun = "";

            }
        }


        model.addAttribute("analysises", campaignAnalysisService.getCampaignAnalysisList(keyword, startDate, endDate, caGubun, pageableAfter));
        model.addAttribute("userAdmInfo", user.getAdminInfo());

        return "campaign/campaign.analysis.list";

    }

    @GetMapping("/campaign/analysis/detail.do")
    @Log(content = "캠패인 분석 상세 조회", action = WorkType.Detail)
    public String getAnalysisDetail(@RequestParam(value = "id") Long id,
                                    @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                    @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                    Model model) {

        CampaignDto campaignDto = campaignAnalysisService.getCampaignAnalysisDetail(id);
        CampaignMessageDto campaignMessageDto = campaignCreateService.getCampaignMessage(id);
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
        model.addAttribute("analysis", campaignDto);
        model.addAttribute("msgInfo", campaignMessageDto);
        model.addAttribute("pcPreviewUrl", pcPreviewUrl);
        model.addAttribute("moPreviewUrl", moPreviewUrl);
        model.addAttribute("searchAnalysis", campaignAnalysisService.getCampaignSearchAnalysisDetail(id, startDate, endDate));

        if (campaignMessageDto != null && "8".equals(campaignMessageDto.getMsgType())) {
            model.addAttribute("searchMultiAnalysis", campaignAnalysisService.getMultiCampaignSearchAnalysisDetail(id, startDate, endDate));
        }

        return "campaign/campaign.analysis.detail";
    }

    @GetMapping("/campaign/analysis/excel/download.do")
    @Log(content = "캠패인 분석 상세 엑셀 다운로드", action = WorkType.Excel)
    public void getAnalysisExcelDownload(HttpServletResponse response,
                                         @RequestParam(value = "ids") List<Long> ids) throws Exception {

        List<CampaignDto> campaignDtos = campaignAnalysisService.getCampaignAnalysisExcel(ids);
        Workbook wb = new XSSFWorkbook();
        Sheet totalSheet = wb.createSheet("전체");
        Sheet pcSheet = wb.createSheet("PC");
        Sheet mobileSheet = wb.createSheet("MO");

        String[] headerTitle = {"ID", "캠페인명", "캠페인 기간", "메시지 유형", "1차 타겟", "2차 타겟", "노출 요일", "노출 시간"
                , "노출 위치", "노출 시점", "노출 빈도", "Views", "메시지 평균 체류 시간", "CTA 버튼/링크 클릭수", "CTA 버튼/링크 클릭률"
                , "가입상담 신청 버튼 클릭수", "가입상담 신청 버튼 클릭률", "바로가입 버튼 클릭수", "바로가입 버튼 클릭률"
                , "가입상담 전화 버튼 클릭수", "가입상담 전화 버튼 클릭률", "전체 클릭수", "전체 클릭률", "목표 전환 지점", "전환수"
                , "전환율", "닫기 버튼 클릭수", "닫기 버튼 클릭률", "메시지 이탈수", "메시지 이탈율"};

        makeData(wb, campaignDtos, totalSheet, headerTitle, "T");
        makeData(wb, campaignDtos, pcSheet, headerTitle, "P");
        makeData(wb, campaignDtos, mobileSheet, headerTitle, "M");

        LocalDateTime now = LocalDateTime.now();
        String formater = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=SKB_DOMS_analysis_" + formater + ".xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();

    }

    private void makeData(Workbook wb, List<CampaignDto> campaignDtos, Sheet sheet
            , String[] headerTitle, String gubun) {

        Row row = null;
        Cell bodyCell = null;
        Cell titleCell = null;
        Font font = wb.createFont();
        font.setBold(true);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        int rowNum = 1;

        row = sheet.createRow(rowNum++);

        for(int i = 0; i < headerTitle.length; i++) {
            titleCell = row.createCell(i);
            titleCell.setCellValue(headerTitle[i]);
            titleCell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 512);
        }

        for(CampaignDto campaignDto : campaignDtos) {
            row = sheet.createRow(rowNum++);

            bodyCell = row.createCell(0);
            bodyCell.setCellValue(campaignDto.getId());

            bodyCell = row.createCell(1);
            bodyCell.setCellValue(campaignDto.getCaName());

            String result = "";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withZone(ZoneId.systemDefault());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    .withZone(ZoneId.systemDefault());
            if("Y".equals(campaignDto.getCaAlldayYn())) {
                result = dateFormatter.format(campaignDto.getCaStartDate()) + " ~ " +
                        dateFormatter.format(campaignDto.getCaEndDate());
            } else {
                result = timeFormatter.format(campaignDto.getCaStartDate()) + " ~ " +
                        timeFormatter.format(campaignDto.getCaEndDate());
            }
            bodyCell = row.createCell(2);
            bodyCell.setCellValue(result);

            bodyCell = row.createCell(3);
            bodyCell.setCellValue(campaignDto.getCaMsgTypeNm());

            String target1 = campaignDto.getTgType01Nm() +
                    ((!StringUtils.isEmpty(campaignDto.getTg1SubNm()))? "\n" + campaignDto.getTg1SubNm() : "");
            if("RETURN".equals(campaignDto.getTgType01()) && "TAR1_FIXED".equals(campaignDto.getTg1Sub())) {
                List<CampaignRevisitUrlDto> tmpList;
                if("T".equals(gubun)) {
                    tmpList = campaignDto.getCampaignRevisitUrlDtos();
                } else {
                    tmpList = campaignDto.getCampaignRevisitUrlDtos().stream().filter(data ->
                            data.getGubun().equals(gubun)).collect(toList());
                }
                for(CampaignRevisitUrlDto campaignRevisitUrlDto : tmpList) {
                    target1 += "\n" + campaignRevisitUrlDto.getRevisitUrl();
                }
            } else if("FIXED".equals(campaignDto.getTgType01())) {
                if("OUTLINK".equals(campaignDto.getTg1Sub())) {
                    List<CampaignSpecUrlDto> tmpList;
                    if("T".equals(gubun)) {
                        tmpList = campaignDto.getCampaignSpecUrlDtos();
                    } else {
                        tmpList = campaignDto.getCampaignSpecUrlDtos().stream().filter(data ->
                                data.getGubun().equals(gubun)).collect(toList());
                    }
                    for(CampaignSpecUrlDto campaignSpecUrlDto : tmpList) {
                        target1 += "\n" + campaignSpecUrlDto.getSpecUrl();
                    }
                } else {
                    target1 += "\n" + campaignDto.getTgFixedWord();
                }
            }
            bodyCell = row.createCell(4);
            bodyCell.setCellValue(target1);

            String target2 = "";
            if("Y".equals(campaignDto.getTgType02Yn())) {
                target2 = campaignDto.getTgType02Nm() +
                        ((!StringUtils.isEmpty(campaignDto.getTg1SubNm()))?
                                (!StringUtils.isEmpty(campaignDto.getTgType02Nm())) ? "\n" : "" + campaignDto.getTg1SubNm() : "");
                if ("FIXED".equals(campaignDto.getTgType02())) {
                    if ("OUTLINK".equals(campaignDto.getTg1Sub())) {
                        List<CampaignSpecUrlDto> tmpList;
                        if("T".equals(gubun)) {
                            tmpList = campaignDto.getCampaignSpecUrlDtos();
                        } else {
                            tmpList = campaignDto.getCampaignSpecUrlDtos().stream().filter(data ->
                                    data.getGubun().equals(gubun)).collect(toList());
                        }
                        for (CampaignSpecUrlDto campaignSpecUrlDto : tmpList) {
                            target2 += "\n" + campaignSpecUrlDto.getSpecUrl();
                        }
                    } else {
                        target2 += "\n" + campaignDto.getTgFixedWord();
                    }
                } else {
                    if ("RETURN".equals(campaignDto.getTgType02()) && "TAR1_FIXED".equals(campaignDto.getTg2Sub())) {
                        List<CampaignRevisitUrlDto> tmpList;
                        if("T".equals(gubun)) {
                            tmpList = campaignDto.getCampaignRevisitUrlDtos();
                        } else {
                            tmpList = campaignDto.getCampaignRevisitUrlDtos().stream().filter(data ->
                                    data.getGubun().equals(gubun)).collect(toList());
                        }
                        for (CampaignRevisitUrlDto campaignRevisitUrlDto : tmpList) {
                            target2 += "\n" + campaignRevisitUrlDto.getRevisitUrl();
                        }
                    }
                }
            }
            bodyCell = row.createCell(5);
            bodyCell.setCellValue(target2);

            List<String> weeks = new ArrayList<String>();
            List<CodeDetailDto> codeDetailDtos = commCodeUtils.getCodeList("CAM_WEEK");
            for(CodeDetailDto codeDetailDto : codeDetailDtos) {
                for(String str : campaignDto.getCaWeek().split(",")) {
                    if(codeDetailDto.getDcode().equals(str)) {
                        weeks.add(codeDetailDto.getDcodeNm());
                    }
                }
            }
            bodyCell = row.createCell(6);
            bodyCell.setCellValue(String.join(", ", weeks));

            String times = "";
            times += campaignDto.getCaTimeNm();
            if("DETAIL_TIME".equals(campaignDto.getCaTime())) {
                for(String time : campaignDto.getCaTimes()) {
                    times += "\n" + time;
                }
            }

            bodyCell = row.createCell(7);
            bodyCell.setCellValue(times);

            String place = "";
            place += campaignDto.getCaPlaceNm();
            if("FIXED_PAGE".equals(campaignDto.getCaPlace())) {
                List<CampaignExposureUrlDto> tmpList;
                if("T".equals(gubun)) {
                    tmpList = campaignDto.getCampaignExposureUrlDtos();
                } else {
                    tmpList = campaignDto.getCampaignExposureUrlDtos().stream().filter(data ->
                            data.getGubun().equals(gubun)).collect(toList());
                }
                for(CampaignExposureUrlDto campaignExposureUrlDto : tmpList) {
                    place += "\n" + (("Y".equals(campaignExposureUrlDto.getIncludeYn()))? "포함, " : "제외, ") +
                            campaignExposureUrlDto.getExposureUrl();
                }
            } else {
                if("Y".equals(campaignDto.getCaPlaceYn())) {
                    List<CampaignExposureUrlDto> tmpList;
                    if("T".equals(gubun)) {
                        tmpList = campaignDto.getCampaignExposureUrlDtos();
                    } else {
                        tmpList = campaignDto.getCampaignExposureUrlDtos().stream().filter(data ->
                                data.getGubun().equals(gubun)).collect(toList());
                    }
                    for(CampaignExposureUrlDto campaignExposureUrlDto : tmpList) {
                        place += "\n" + "제외, " + campaignExposureUrlDto.getExposureUrl();
                    }
                }
            }
            bodyCell = row.createCell(8);
            bodyCell.setCellValue(place);

            bodyCell = row.createCell(9);
            bodyCell.setCellValue(campaignDto.getCaViewPointNm() + "\n" + campaignDto.getViewPointSubNm());

            bodyCell = row.createCell(10);
            bodyCell.setCellValue(campaignDto.getExposureLimitCntNm());

            bodyCell = row.createCell(11);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getVisibleCnt()
                        + campaignDto.getVisibleMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getVisibleCnt() :
                        campaignDto.getVisibleMoCnt()));
            }

            bodyCell = row.createCell(12);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getTotalExpAvg()) + "초");
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getPcExpAvg() :
                        campaignDto.getMoExpAvg()));
            }

            bodyCell = row.createCell(13);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getClickCnt() + campaignDto.getClickMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getClickCnt() :
                        campaignDto.getClickMoCnt()));
            }

            bodyCell = row.createCell(14);
            Float ctaResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totClick = campaignDto.getClickCnt() + campaignDto.getClickMoCnt();
                if(totVisible != 0) {
                    ctaResult = (float) (totClick / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totClick = ("P".equals(gubun))? campaignDto.getClickCnt() : campaignDto.getClickMoCnt();
                if(totVisible != 0) {
                    ctaResult = (float) (totClick / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(ctaResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(15);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getLeaveCnt() :
                        campaignDto.getLeaveMoCnt()));
            }

            bodyCell = row.createCell(16);
            Float leaveResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totLeave = campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt();
                if(totVisible != 0) {
                    leaveResult = (float) (totLeave / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totLeave = ("P".equals(gubun))? campaignDto.getLeaveCnt() : campaignDto.getLeaveMoCnt();
                if(totVisible != 0) {
                    leaveResult = (float) (totLeave / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(leaveResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(17);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getRegistCnt() + campaignDto.getRegistMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getRegistCnt() :
                        campaignDto.getRegistMoCnt()));
            }

            bodyCell = row.createCell(18);
            Float registResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totRegist = campaignDto.getRegistCnt() + campaignDto.getRegistMoCnt();
                if(totVisible != 0) {
                    registResult = (float) (totRegist / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totRegist = ("P".equals(gubun))? campaignDto.getRegistCnt() : campaignDto.getRegistMoCnt();
                if(totVisible != 0) {
                    registResult = (float) (totRegist / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(registResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(19);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getPhoneCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? 0 :
                        campaignDto.getPhoneCnt()));
            }

            bodyCell = row.createCell(20);
            Float phoneResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleMoCnt();
                Integer totPhone = campaignDto.getPhoneCnt();
                if(totVisible != 0) {
                    phoneResult = (float) (totPhone / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? 0 : campaignDto.getVisibleMoCnt();
                Integer totPhone = ("P".equals(gubun))? 0 : campaignDto.getPhoneCnt();
                if(totVisible != 0) {
                    phoneResult = (float) (totPhone / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(phoneResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(21);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getClickCnt() +
                        campaignDto.getClickMoCnt() +
                        campaignDto.getPhoneCnt() + campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt() +
                        campaignDto.getRegistCnt() + campaignDto.getRegistMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getClickCnt() +
                        campaignDto.getLeaveCnt() + campaignDto.getRegistCnt() :
                        campaignDto.getClickMoCnt() + campaignDto.getPhoneCnt() + campaignDto.getLeaveMoCnt() +
                        campaignDto.getRegistMoCnt()));
            }

            bodyCell = row.createCell(22);
            Float totalResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer total = campaignDto.getClickCnt() + campaignDto.getClickMoCnt() +
                        campaignDto.getPhoneCnt() + campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt() +
                        campaignDto.getRegistCnt() + campaignDto.getRegistMoCnt();
                if(totVisible != 0) {
                    totalResult = (float) (total / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer total = ("P".equals(gubun))? campaignDto.getClickCnt() +
                        campaignDto.getLeaveCnt() + campaignDto.getRegistCnt() : campaignDto.getClickMoCnt() +
                        campaignDto.getPhoneCnt() + campaignDto.getLeaveMoCnt() + campaignDto.getRegistMoCnt();
                if(totVisible != 0) {
                    totalResult = (float) (total / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(totalResult * 100) / 100.0) + "%");

            String purpose = campaignDto.getCaPurposeNm();
            bodyCell = row.createCell(23);
            if("ETC".equals(campaignDto.getCaPurpose())) {
                for(CampaignTargetUrlDto campaignTargetUrlDto : campaignDto.getCampaignTargetUrlDtos()) {
                    purpose += "\n" + campaignTargetUrlDto.getTargetUrl();
                }
            }
            bodyCell.setCellValue(purpose);

            bodyCell = row.createCell(24);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getTargetVisitCnt() +
                        campaignDto.getTargetVisitMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getTargetVisitCnt() :
                        campaignDto.getTargetVisitMoCnt()));
            }

            bodyCell = row.createCell(25);
            Float targetResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totTarget = campaignDto.getTargetVisitCnt() + campaignDto.getTargetVisitMoCnt();
                if(totVisible != 0) {
                    targetResult = (float) (totTarget / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totTarget = ("P".equals(gubun))? campaignDto.getTargetVisitCnt() : campaignDto.getTargetVisitMoCnt();
                if(totVisible != 0) {
                    targetResult = (float) (totTarget / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(targetResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(26);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format(campaignDto.getCloseCnt() +
                        campaignDto.getCloseMoCnt()));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getCloseCnt() :
                        campaignDto.getCloseMoCnt()));
            }

            bodyCell = row.createCell(27);
            Float closeResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totClose = campaignDto.getCloseCnt() + campaignDto.getCloseMoCnt();
                if(totVisible != 0) {
                    closeResult = (float) (totClose / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totClose = ("P".equals(gubun))? campaignDto.getCloseCnt() : campaignDto.getCloseMoCnt();
                if(totVisible != 0) {
                    closeResult = (float) (totClose / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(closeResult * 100) / 100.0) + "%");

            bodyCell = row.createCell(28);
            if("T".equals(gubun)) {
                bodyCell.setCellValue(NumberFormat.getInstance().format((campaignDto.getVisibleCnt() +
                        campaignDto.getVisibleMoCnt()) - (campaignDto.getClickCnt() + campaignDto.getClickMoCnt() +
                        campaignDto.getPhoneCnt() + campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt() +
                        campaignDto.getRegistCnt() + campaignDto.getRegistMoCnt() + campaignDto.getCloseCnt() +
                        campaignDto.getCloseMoCnt())));
            } else {
                bodyCell.setCellValue(NumberFormat.getInstance().format(("P".equals(gubun))? campaignDto.getVisibleCnt() -
                        (campaignDto.getClickCnt() + campaignDto.getLeaveCnt() + campaignDto.getRegistCnt() + campaignDto.getCloseCnt()) :
                        campaignDto.getVisibleCnt() - (campaignDto.getClickMoCnt() + campaignDto.getPhoneCnt() +
                        campaignDto.getLeaveMoCnt() + campaignDto.getRegistMoCnt() + campaignDto.getCloseMoCnt())));
            }

            bodyCell = row.createCell(29);
            Float outResult = (float) 0;
            if("T".equals(gubun)) {
                Integer totVisible = campaignDto.getVisibleCnt() + campaignDto.getVisibleMoCnt();
                Integer totOut = campaignDto.getClickCnt() + campaignDto.getClickMoCnt() + campaignDto.getPhoneCnt() +
                        campaignDto.getLeaveCnt() + campaignDto.getLeaveMoCnt() + campaignDto.getRegistCnt() +
                        campaignDto.getRegistMoCnt() + campaignDto.getCloseCnt() + campaignDto.getCloseMoCnt();
                if(totVisible != 0) {
                    outResult = (float) ((totVisible - totOut) / (totVisible * 1.0)) * 100;
                }
            } else {
                Integer totVisible = ("P".equals(gubun))? campaignDto.getVisibleCnt() : campaignDto.getVisibleMoCnt();
                Integer totOut = ("P".equals(gubun))? campaignDto.getClickCnt() + campaignDto.getLeaveCnt() +
                        campaignDto.getRegistCnt() + campaignDto.getCloseCnt() : campaignDto.getClickMoCnt() +
                        campaignDto.getPhoneCnt() + campaignDto.getLeaveMoCnt() + campaignDto.getRegistMoCnt() +
                        campaignDto.getCloseMoCnt();
                if(totVisible != 0) {
                    outResult = (float) ((totVisible - totOut) / (totVisible * 1.0)) * 100;
                }
            }
            bodyCell.setCellValue(NumberFormat.getInstance().format(Math.round(outResult * 100) / 100.0) + "%");

        }
    }

}
