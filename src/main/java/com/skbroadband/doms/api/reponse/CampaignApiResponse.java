package com.skbroadband.doms.api.reponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.reponse
 * @File : CampaignApiResponse
 * @Program :
 * @Date : 2023-03-03
 * @Comment :
 */
@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
public class CampaignApiResponse implements Serializable {
    private Long caNo;
    private String caPurpose;
    private String caPurposeUrl;
    private String caPlace;
    private Integer caMsgType;
    private String caViewPoint;
    private String viewPointSub;
    private String exposureLimitCnt;
    private String msgName;
    private String msgImgNm01;
    private String msgImgNm02;
    private String msgImgNm03;
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
    private String msgPcHtml;
    private String msgMoHtml;
    private String msgBanBgColor;
    private String msgBanTextColor;
    private String msgActionEffectType;
}
