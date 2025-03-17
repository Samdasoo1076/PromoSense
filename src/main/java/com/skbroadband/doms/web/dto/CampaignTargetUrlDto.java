package com.skbroadband.doms.web.dto;

import com.skbroadband.doms.web.entity.Campaign;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignTargetUrlDto {

    private Long id;
    private Campaign caNo;
    private String targetUrl;
    private String gubun;

}