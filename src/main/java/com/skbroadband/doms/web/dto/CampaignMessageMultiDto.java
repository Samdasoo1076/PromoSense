package com.skbroadband.doms.web.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignMessageMultiDto {
    private Long multiNo;
    private Long caNo;
    private Long multiSeq;
    private String msgImgNm01;
    private String msgImgRnm01;
    private String msgImgAlter01;
    private String msgButton;
    private String msgButtonTf;
    private String msgButtonColor;
    private String msgButtonBgColor;
    private String msgButtonPcUrl;
    private String msgButtonMoUrl;
    private String msgButtonUrlTargetP;
    private String msgButtonUrlTargetM;
}
