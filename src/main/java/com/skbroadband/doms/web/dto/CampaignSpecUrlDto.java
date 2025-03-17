package com.skbroadband.doms.web.dto;

import com.skbroadband.doms.web.entity.Campaign;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignSpecUrlDto implements Comparable<CampaignSpecUrlDto> {

    private Long id;
    private Campaign caNo;
    private String specUrl;
    private String gubun;

    @Override
    public int compareTo(CampaignSpecUrlDto other) {
        return gubun.compareTo(other.gubun);
    }

}