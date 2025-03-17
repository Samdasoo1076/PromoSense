package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.annotation.LoginUser;
import com.skbroadband.doms.global.component.security.JceCryptoComponent;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.utils.CommCodeUtils;
import com.skbroadband.doms.web.dto.AdminInfoDto;
import com.skbroadband.doms.web.service.AccountService;
import com.skbroadband.doms.web.service.AuthService;
import com.skbroadband.doms.web.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : AccountController
 * @Program :
 * @Date : 2023-01-25
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final AuthService authService;

    private final JceCryptoComponent jceCryptoComponent;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;


    private static String COMPLEX_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[`~!@#$%^&*()])(?=.*\\d)(?=\\S+$).{8,16}$";
    private static String COMPLEX_REPEATING_CHAR_REGEX = "(\\w)\\1\\1";

    private static Pattern PASSWORD_PATTERN = Pattern.compile(COMPLEX_PASSWORD_REGEX);
    private static Pattern REPEATING_CHAR_PATTERN = Pattern.compile(COMPLEX_REPEATING_CHAR_REGEX);


    @Value("${server.etc-info.address}")
    private String address;

    @Log(content = "계정관리 조회", action = WorkType.List)
    @GetMapping("/settings/account/list.do")
    public String settingsAccountList(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                      @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                      @RequestParam(value = "admFlag", defaultValue = "") String admFlag,
                                      @PageableDefault(size = 10) Pageable pageable,
                                      Model model) {



        model.addAttribute("accounts", accountService.findAccounts(keyword, startDate, endDate, admFlag, pageable));
        model.addAttribute("accountTotal", accountService.countAllAccounts("Y", "N"));

        return "settings/account.list";
    }

    @Log(content = "계정관리 엑셀 다운로드", action = WorkType.Excel)
    @GetMapping("/settings/account/excel/download.do")
    public void excelDownload(HttpServletResponse response,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "startDate", defaultValue = "") String startDate,
                              @RequestParam(value = "endDate", defaultValue = "") String endDate) throws Exception {
        List<AdminInfoDto> list = accountService.findExcelList(keyword, startDate, endDate);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("사용자 정보");

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("아이디");
        cell = row.createCell(1);
        cell.setCellValue("이름");
        cell = row.createCell(2);
        cell.setCellValue("부서");
        cell = row.createCell(3);
        cell.setCellValue("그룹");
        cell = row.createCell(4);
        cell.setCellValue("휴대전화");
        cell = row.createCell(5);
        cell.setCellValue("이메일");
        cell = row.createCell(6);
        cell.setCellValue("등록일");
        cell = row.createCell(7);
        cell.setCellValue("상태");

        // body
        for(AdminInfoDto data : list) {
            row = sheet.createRow(rowNum++);

            cell = row.createCell(0);
            cell.setCellValue(data.getAdmId());
            cell = row.createCell(1);
            cell.setCellValue(data.getAdmName());
            cell = row.createCell(2);
            cell.setCellValue(data.getDept());
            cell = row.createCell(3);
            cell.setCellValue((data.getGroupNo() != null)? data.getGroupNo().getGroupName() : "-");
            cell = row.createCell(4);
            cell.setCellValue(data.getAdmHphone()==null?"":jceCryptoComponent.descrypt(data.getAdmHphone()));
            cell = row.createCell(5);
            cell.setCellValue(data.getAdmEmail()==null?"":jceCryptoComponent.descrypt(data.getAdmEmail()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
            String formattedInstant = formatter.format(data.getRegDate());
            cell = row.createCell(6);
            cell.setCellValue(formattedInstant);

            String status = "-";
            if("0".equals(data.getAdmFlag()))  status = "승인대기";
            if("1".equals(data.getAdmFlag()))  status = "승인완료";
            if("2".equals(data.getAdmFlag()))  status = "중지";
            if("3".equals(data.getAdmFlag()))  status = "승인거절";

            cell = row.createCell(7);
            cell.setCellValue(status);
        }

        LocalDateTime now = LocalDateTime.now();
        String formater = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=SKB_DOMS_account_" + formater + ".xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }

    @Log(content = "계정관리 상세조회", action = WorkType.Detail)
    @GetMapping("/settings/account/detail.do")
    public String settingAccountDetail(@RequestParam("id") Long id,
                                       Model model) {

        AdminInfoDto admInfo = accountService.findAccountDetail(id);

        model.addAttribute("admInfo", admInfo);
        model.addAttribute("authList", authService.findAllList("Y", "N"));
        model.addAttribute("upAdmName",
                (accountService.findUpAdmName(admInfo.getUpAdm()) == null)? "" : accountService.findUpAdmName(admInfo.getUpAdm()).getAdmName());

        return "settings/account.detail";
    }

    @Log(content = "계정정보 삭제", action = WorkType.Delete)
    @GetMapping("/settings/account/delete.do")
    public ResponseEntity<?> delete(@RequestParam("id") Long id,
                                    @LoginUser Account user) {

        accountService.deleteAmdinInfo(id, user.getAdmNo());

        return Response.ok();

    }

    @Log(content = "계정정보 수정", action = WorkType.Modification)
    @PostMapping("/settings/account/update.do")
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestBody HashMap<String, Object> param,
                                    AdminInfoDto adminInfoDto,
                                    @LoginUser Account user) throws Exception {

        if(!StringUtils.hasText(String.valueOf(param.get("admEmail")))) {
            throw new BadRequestException("이메일은 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(param.get("admHphone")))) {
            throw new BadRequestException("휴대폰번호는 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(param.get("groupNo")))) {
            throw new BadRequestException("사용자그룹 선택은 필수 입니다.");
        }
        if(!StringUtils.hasText(String.valueOf(param.get("dept")))) {
            throw new BadRequestException("부서는 필수 입니다.");
        }

        if(Boolean.valueOf((String) param.get("chkPassword"))){
            if (!PASSWORD_PATTERN.matcher(String.valueOf(param.get("password"))).matches()) {
                throw new BadRequestException("8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.");
            }

            Matcher passMatcher = REPEATING_CHAR_PATTERN.matcher(String.valueOf(param.get("password")));

            if (passMatcher.find()) {
                throw new BadRequestException("3자리 이상 반복되는 영문, 숫자는 비밀번호로 사용할 수 없습니다.");
            }


            if(Boolean.valueOf((String) param.get("chkPassword"))) {
                adminInfoDto.setPasswd(passwordEncoder.encode(String.valueOf(param.get("password"))));
                adminInfoDto.setLoginFailCnt(0);
            }
        }else{
            AdminInfoDto admInfo = accountService.findAccountDetail(id);
            adminInfoDto.setPasswd(admInfo.getPasswd());
        }

        if(Boolean.valueOf((String) param.get("chkEmail"))) {
            adminInfoDto.setAdmEmail(jceCryptoComponent.encrypt(String.valueOf(param.get("admEmail"))));
            adminInfoDto.setAdmEmailHash(DigestUtils.sha256Hex(String.valueOf(param.get("admEmail"))));
        }

        adminInfoDto.setAdmHphone(jceCryptoComponent.encrypt(String.valueOf(param.get("admHphone"))));
        adminInfoDto.setAdmHphoneHash(DigestUtils.sha256Hex(String.valueOf(param.get("admHphone"))));
        adminInfoDto.setParamGroupNo(Long.valueOf((String) param.get("groupNo")));
        adminInfoDto.setDept(String.valueOf(param.get("dept")));
        adminInfoDto.setMemo(String.valueOf(param.get("memo")));
        adminInfoDto.setAdmFlag(String.valueOf(param.get("admFlag")));
        adminInfoDto.setAdmInfo(String.valueOf(param.get("admInfo")));

        String oldStatus = accountService.getAdmFlag(id);

        /* 로그인 실패 카운트 초기화 */
        if(Boolean.parseBoolean((String) param.get("radLoginFailCnt"))) {
            adminInfoDto.setLoginFailCnt(0);
        }

        accountService.updateAdminInfo(id, adminInfoDto, user.getAdmNo());

        String statusName;
        if("0".equals(adminInfoDto.getAdmFlag())) {
            statusName = "승인대기";
        } else if("1".equals(adminInfoDto.getAdmFlag())) {
            statusName = "승인완료";
        } else if("2".equals(adminInfoDto.getAdmFlag())) {
            statusName = "중지";
        } else if("3".equals(adminInfoDto.getAdmFlag())) {
            statusName = "승인거절";
        } else {
            statusName = "";
        }

        if(!adminInfoDto.getAdmFlag().equals(oldStatus)) {
            mailService.send("접속권한 상태 변경",
                    String.valueOf(param.get("admEmail")),
                    "/form/account.status.change.mail.html",
                    context -> {
                        context.setVariable("url", address);
                        context.setVariable("statusName", statusName);
                        context.setVariable("admName", String.valueOf(param.get("admName")));
                    });
        }

        return Response.ok();

    }
}
