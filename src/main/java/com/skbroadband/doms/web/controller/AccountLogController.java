package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.web.dto.AccountLogDto;
import com.skbroadband.doms.web.service.AccountLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : AccountLogController
 * @Program :
 * @Date : 2023-01-25
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountLogController {
    private final AccountLogService accountLogService;

    @Log(content = "계정로그관리 조회", action = WorkType.List)
    @GetMapping("/settings/account/log/list.do")
    public String accountLogs( @RequestParam(value = "keyword", defaultValue = "") String keyword
                                        , @RequestParam(value = "startDate", defaultValue = "") String startDate
                                        , @RequestParam(value = "endDate", defaultValue = "") String endDate
                                        , @RequestParam(value = "taskFlag", defaultValue = "") String taskFlag
                                        , @PageableDefault(size = 10) Pageable pageable
                                        , Model model) {


//        //시작일 현재날짜의 30일 이전
//        if(!StringUtils.hasText(startDate)) {
//            startDate = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);
//            model.addAttribute("startDate", startDate);
//        }
//
//        //종료일 현재날짜
//        if(!StringUtils.hasText(endDate)) {
//            endDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
//            model.addAttribute("endDate", endDate);
//        }

        model.addAttribute("accountLogs", accountLogService.findAdminLogs(keyword, startDate, endDate, taskFlag, pageable));
        model.addAttribute("totalCount", accountLogService.countAllAccounts());

        return "settings/account.log.list";
    }

    @Log(content = "계정로그관리 엑셀 다운로드", action = WorkType.Excel)
    @GetMapping("/settings/account/log/excel/download.do")
    public void excelDownload(HttpServletResponse response,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "startDate", defaultValue = "") String startDate,
                              @RequestParam(value = "endDate", defaultValue = "") String endDate) throws Exception {
        List<AccountLogDto> list = accountLogService.findExcelList(keyword, startDate, endDate);
        log.info("account_list >> {}", list);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("첫번째 시트");

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("부서");
        cell = row.createCell(1);
        cell.setCellValue("아이디");
        cell = row.createCell(2);
        cell.setCellValue("업무구분");
        cell = row.createCell(3);
        cell.setCellValue("업무내용");
        cell = row.createCell(4);
        cell.setCellValue("접속아이디");
        cell = row.createCell(5);
        cell.setCellValue("처리일시");


        // body
        for(AccountLogDto data : list) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(data.getGroupName());
            cell = row.createCell(1);
            cell.setCellValue(data.getAdmId());
            cell = row.createCell(2);
            cell.setCellValue(data.getTaskType());
            cell = row.createCell(3);
            cell.setCellValue(data.getTask());
            cell = row.createCell(4);
            cell.setCellValue(data.getLogIp());
            cell = row.createCell(5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
            String formattedInstant = formatter.format(data.getLogDate());
            cell.setCellValue(formattedInstant);

        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
