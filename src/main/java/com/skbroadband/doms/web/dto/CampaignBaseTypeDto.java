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
public class CampaignBaseTypeDto {

    private Long baseNo;
    private String title;
    private String content;
    private String cssName;
    private String contentUri;
    private Long baseOrder;
    private String useTf;
    private String delTf;
    private Long regAdm;
    private Instant regDate;
    private Long upAdm;
    private Instant upDate;
    private Long delAdm;
    private Instant delDate;

}
