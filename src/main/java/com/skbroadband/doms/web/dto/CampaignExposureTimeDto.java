package com.skbroadband.doms.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignExposureTimeDto {

    private Long id;
    private CampaignDto caNo;
    private String startTime;
    private String endTime;

}