package com.skbroadband.doms.web.dto;

import lombok.*;

import java.time.Instant;

/**
 * @author : 홍수민
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
public class AdminGroupDto {
    private Long groupNo;
    private String groupName;
    /*@Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "4~20자리의 영문, 숫자만 사용 가능합니다.")
    private String admId;*/
    private String groupFlag;
    private String useTf;
    private String delTf;
    private Long regAdm;
    private Instant regDate;
    private Long upAdm;
    /*@Pattern(regexp = "^([0-9a-zA-Z_\\.-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$", message = "잘못된 이메일 주소입니다. 이메일 주소를 정확하게 입력해 주세요.")
    @NotBlank(message = "이메일 주소는 필수입니다.")
    private String admEmail;*/
    private Instant upDate;
    private Long delAdm;
    private Instant delDate;
}
