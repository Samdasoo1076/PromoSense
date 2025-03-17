package com.skbroadband.doms.web.dto;

import com.skbroadband.doms.global.annotation.PasswordConstraint;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : AdminInfoDto
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminInfoDto {
    private Long admNo;
    private AdminGroupDto groupNo;
    private Long paramGroupNo;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "4~20자리의 영문, 숫자만 사용 가능합니다.")
    private String admId;
    @PasswordConstraint
    private String passwd;
    @NotBlank(message = "이름은 필수입니다.")
    private String admName;
    private String admInfo;
    @Pattern(regexp = "^[0-9]{10,11}$", message = "잘못된 휴대전화번호입니다.")
    @NotBlank(message = "휴대폰번호는 숫자만 사용가능합니다.")
    private String admHphone;
    private String admHphoneHash;
    private String admPhone;
    @NotBlank(message = "이메일 주소는 필수입니다.")
    @Email(message = "잘못된 이메일 주소입니다. 이메일 주소를 정확하게 입력해 주세요.")
    private String admEmail;
    private String admEmailHash;
    private String admFlag;
    private String positionCode;
    @NotBlank(message = "부서는 필수입니다.")
    private String dept;
    private String memo;
    private String useTf;
    private String delTf;
    private Instant loginDate;
    private Integer loginFailCnt;
    private Instant passwdDate;
    private String regAdm;
    private Instant regDate;
    private String upAdm;
    private Instant upDate;
    private String delAdm;
    private Instant delDate;
    private Boolean chkEmail;
    private Boolean chkPassword;
    private String oldPassword;
}
