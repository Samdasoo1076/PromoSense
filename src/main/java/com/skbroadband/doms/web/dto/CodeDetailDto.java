package com.skbroadband.doms.web.dto;

import lombok.*;

import java.time.Instant;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : CodeDetailDto
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
public class CodeDetailDto {

    private String dcode;
    private CodeParentDto codeParentDto;
    private String dcodeNm;
    private String dcodeExt;
    private Long dcodeSeqNo;
    private String dcodeExtValue1;
    private String dcodeExtValue2;
    private String dcodeExtValue3;
    private String dcodeExtValue4;
    private String useTf;
    private String delTf;
    private String delAdm;
    private Instant delDate;
}
