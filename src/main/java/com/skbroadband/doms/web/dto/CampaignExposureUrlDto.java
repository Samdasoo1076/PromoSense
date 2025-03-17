package com.skbroadband.doms.web.dto;

import com.skbroadband.doms.web.entity.Campaign;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignExposureUrlDto implements Comparable<CampaignExposureUrlDto> {

    private Long id;
    private Campaign caNo;
    private String exposureUrl;
    private String gubun;
    private String includeYn;

    @Override
    public int compareTo(CampaignExposureUrlDto other) {
        return gubun.compareTo(other.gubun);
    }

}