package com.skbroadband.doms.api.reponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.reponse
 * @File : PreviewApiResponse
 * @Program :
 * @Date : 2023-03-03
 * @Comment :
 */
@Getter
@JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
public class PreviewApiResponse {
    private final Long caNo;
    private final String caPurpose;
    private final String caPurposeUrl;
    private final String caPlace;
    private final Integer caMsgType;
    private final String caViewPoint;
    private final String viewPointSub;
    private final String exposureLimitCnt;
    private final String msgName;
    private final String msgImgNm01;
    private final String msgImgNm02;
    private final String msgImgNm03;
    private final String msgImgAlter01;
    private final String msgImgAlter02;
    private final String msgImgAlter03;
    private final String msgTitleTf;
    private final String msgTitle;
    private final String msgContentsTf;
    private final String msgContents;
    private final String msgButtonTf;
    private final String msgButtonPcUrl;
    private final String msgButtonMoUrl;
    private final String msgButtonColor;
    private final String msgButtonUrlTargetP;
    private final String PHONE_BUTTON_TF;
    private final String msgTitleColor;
    private final String msgImgTf;
    private final String msgContentsColor;
    private final String msgButton;
    private final String msgButtonUrlTargetM;
    private final String msgButtonBgColor;
    private final String msgLeaveLineColor;
    private final String msgLeaveColor;
    private final String msgLeaveTf;
    private final String msgRegistTf;
    private final String msgRegistLineColor;
    private final String msgRegistColor;
    private final String msgMobileCallingTf;
    private final String msgMobileCallingLineColor;
    private final String msgMobileCallingColor;
    private final String msgBackDimTf;
    private final String msgReviewTf;
    private final String msgReviewType;
    private final String msgReviewDays;
    private final String msgPcHtml;
    private final String msgMoHtml;
    private final String msgBanBgColor;
    private final String msgBanTextColor;
    private final String msgActionEffectType;

    @QueryProjection
    public PreviewApiResponse(Long caNo, String caPurpose, String caPurposeUrl, String caPlace, Integer caMsgType,
                              String caViewPoint, String viewPointSub, String exposureLimitCnt, String msgName,
                              String msgImgNm01, String msgImgNm02, String msgImgNm03, String msgImgAlter01,
                              String msgImgAlter02, String msgImgAlter03, String msgTitleTf, String msgTitle,
                              String MSG_CONTENTS_TF, String msgContents, String msgButtonTf, String msgButtonPcUrl,
                              String msgButtonMoUrl, String msgButtonColor, String msgButtonUrlTargetP,
                              String PHONE_BUTTON_TF, String msgTitleColor, String msgImgTf, String msgContentsColor,
                              String msgButton, String msgButtonUrlTargetM, String msgButtonBgColor,
                              String msgLeaveLineColor, String msgLeaveColor, String msgLeaveTf, String msgRegistTf,
                              String msgRegistLineColor, String msgRegistColor, String msgMobileCallingTf,
                              String msgMobileCallingLineColor, String msgMobileCallingColor, String msgBackDimTf,
                              String msgReviewTf, String msgReviewType, String msgReviewDays, String msgPcHtml,
                              String msgMoHtml, String msgBanBgColor, String msgBanTextColor, String msgActionEffectType) {
        this.caNo = caNo;
        this.caPurpose = caPurpose;
        this.caPurposeUrl = caPurposeUrl;
        this.caPlace = caPlace;
        this.caMsgType = caMsgType;
        this.caViewPoint = caViewPoint;
        this.viewPointSub = viewPointSub;
        this.exposureLimitCnt = exposureLimitCnt;
        this.msgName = msgName;
        this.msgImgNm01 = msgImgNm01;
        this.msgImgNm02 = msgImgNm02;
        this.msgImgNm03 = msgImgNm03;
        this.msgImgAlter01 = msgImgAlter01;
        this.msgImgAlter02 = msgImgAlter02;
        this.msgImgAlter03 = msgImgAlter03;
        this.msgTitleTf = msgTitleTf;
        this.msgTitle = msgTitle;
        this.msgContentsTf = MSG_CONTENTS_TF;
        this.msgContents = msgContents;
        this.msgButtonTf = msgButtonTf;
        this.msgButtonPcUrl = msgButtonPcUrl;
        this.msgButtonMoUrl = msgButtonMoUrl;
        this.msgButtonColor = msgButtonColor;
        this.msgButtonUrlTargetP = msgButtonUrlTargetP;
        this.PHONE_BUTTON_TF = PHONE_BUTTON_TF;
        this.msgTitleColor = msgTitleColor;
        this.msgImgTf = msgImgTf;
        this.msgContentsColor = msgContentsColor;
        this.msgButton = msgButton;
        this.msgButtonUrlTargetM = msgButtonUrlTargetM;
        this.msgButtonBgColor = msgButtonBgColor;
        this.msgLeaveLineColor = msgLeaveLineColor;
        this.msgLeaveColor = msgLeaveColor;
        this.msgLeaveTf = msgLeaveTf;
        this.msgRegistTf = msgRegistTf;
        this.msgRegistLineColor = msgRegistLineColor;
        this.msgRegistColor = msgRegistColor;
        this.msgMobileCallingTf = msgMobileCallingTf;
        this.msgMobileCallingLineColor = msgMobileCallingLineColor;
        this.msgMobileCallingColor = msgMobileCallingColor;
        this.msgBackDimTf = msgBackDimTf;
        this.msgReviewTf = msgReviewTf;
        this.msgReviewType = msgReviewType;
        this.msgReviewDays = msgReviewDays;
        this.msgPcHtml = msgPcHtml;
        this.msgMoHtml = msgMoHtml;
        this.msgBanBgColor = msgBanBgColor;
        this.msgBanTextColor = msgBanTextColor;
        this.msgActionEffectType = msgActionEffectType;
    }
}
