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
public class CampaignMessageDto {

    private Long id;
    private CampaignDto caNo;
    private String msgType;
    private String msgState;
    private String msgName;
    private String msgImgNm01;
    private String msgImgRnm01;
    private String msgImgNm02;
    private String msgImgRnm02;
    private String msgImgNm03;
    private String msgImgRnm03;
    private String msgImgAlter01;
    private String msgImgAlter02;
    private String msgImgAlter03;
    private String msgTitleTf;
    private String msgTitle;
    private String msgContentsTf;
    private String msgContents;
    private String msgButtonTf;
    private String msgButtonPcUrl;
    private String msgButtonMoUrl;
    private String msgButtonColor;
    private String msgButtonUrlTargetP;
    private String phoneButtonTf;
    private String msgThumbPcImg;
    private String msgThumbMoImg;
    private String useTf;
    private String delTf;
    private Long delAdm;
    private Instant delDate;
    private Long upAdm;
    private Instant upDate;
    private String msgTitleColor;
    private String msgImgTf;
    private String msgContentsColor;
    private String msgButton;
    private String msgButtonUrlTargetM;
    private String msgButtonBgColor;
    private String msgLeaveLineColor;
    private String msgLeaveColor;
    private String msgLeaveTf;
    private String msgRegistTf;
    private String msgRegistLineColor;
    private String msgRegistColor;
    private String msgMobileCallingTf;
    private String msgMobileCallingLineColor;
    private String msgMobileCallingColor;
    private String msgBackDimTf;
    private String msgReviewTf;
    private String msgReviewType;
    private String msgReviewDays;
    private String msgSaveTf;
    private String msgPcHtml;
    private String msgMoHtml;
    private String msgCaptureName;
    private String msgCapture;
    private String msgBanBgColor;
    private String msgBanTextColor;
    private String msgActionEffectType;

    private String multiList;
    private List<String> msgImgRnm01List;
    private List<String> msgImgNm01List;
    private List<Integer> changeImages;
}
