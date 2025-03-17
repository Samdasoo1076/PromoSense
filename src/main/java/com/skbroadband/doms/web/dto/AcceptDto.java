package com.skbroadband.doms.web.dto;

import lombok.*;

import java.time.Instant;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : AcceptDto
 * @Program :
 * @Date : 2023-02-01
 * @Comment :
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AcceptDto {
    private Long seqNo;
    private String acceptIp;
    private String useTf;
    private String delTf;
    private Long regAdm;
    private Instant regDate;
    private Long upAdm;
    private Instant upDate;
    private Long delAdm;
    private Instant delDate;
}
