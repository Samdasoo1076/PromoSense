package com.skbroadband.doms.web.dto;

import lombok.*;

import java.time.Instant;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : CodeParentDto
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CodeParentDto {

    private String pcode;
    private String pcodeNm;
    private String pcodeMemo;
    private Long pcodeSeqNo;
    private String useTf;
    private String delTf;
    private Long delAdm;
    private Instant delDate;

}
