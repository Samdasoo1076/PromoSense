package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CAMPAIGN_MESSAGE_HISTORY")
public class CampaignMessageHistory extends CreateTimeBase {
    @Id
    @Column(name = "MSG_HIS_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "MSG_TYPE", length = 25)
    private String msgType;

    @Column(name = "MSG_STATE")
    private String msgState;

    @Size(max = 30)
    @Column(name = "MSG_NAME", length = 30)
    private String msgName;

    @Size(max = 100)
    @Column(name = "MSG_IMG_NM_01", length = 100)
    private String msgImgNm01;

    @Size(max = 100)
    @Column(name = "MSG_IMG_RNM_01", length = 100)
    private String msgImgRnm01;

    @Size(max = 100)
    @Column(name = "MSG_IMG_NM_02", length = 100)
    private String msgImgNm02;

    @Size(max = 100)
    @Column(name = "MSG_IMG_RNM_02", length = 100)
    private String msgImgRnm02;

    @Size(max = 100)
    @Column(name = "MSG_IMG_NM_03", length = 100)
    private String msgImgNm03;

    @Size(max = 100)
    @Column(name = "MSG_IMG_RNM_03", length = 100)
    private String msgImgRnm03;

    @Size(max = 300)
    @Column(name = "MSG_IMG_ALTER_01", length = 300)
    private String msgImgAlter01;

    @Size(max = 300)
    @Column(name = "MSG_IMG_ALTER_02", length = 300)
    private String msgImgAlter02;

    @Size(max = 300)
    @Column(name = "MSG_IMG_ALTER_03", length = 300)
    private String msgImgAlter03;

    @Column(name = "MSG_TITLE_TF")
    private String msgTitleTf;

    @Size(max = 50)
    @Column(name = "MSG_TITLE", length = 50)
    private String msgTitle;

    @Column(name = "MSG_CONTENTS_TF")
    private String msgContentsTf;

    @Size(max = 300)
    @Column(name = "MSG_CONTENTS", length = 300)
    private String msgContents;

    @Column(name = "MSG_BUTTON_TF")
    private String msgButtonTf;

    @Size(max = 300)
    @Column(name = "MSG_BUTTON_PC_URL", length = 300)
    private String msgButtonPcUrl;

    @Size(max = 300)
    @Column(name = "MSG_BUTTON_MO_URL", length = 300)
    private String msgButtonMoUrl;

    @Size(max = 20)
    @Column(name = "MSG_BUTTON_COLOR", length = 20)
    private String msgButtonColor;

    @Column(name = "MSG_BUTTON_URL_TARGET_P")
    private String msgButtonUrlTargetP;

    @Column(name = "PHONE_BUTTON_TF")
    private String phoneButtonTf;

    @Size(max = 50)
    @Column(name = "MSG_THUMB_PC_IMG", length = 50)
    private String msgThumbPcImg;

    @Size(max = 50)
    @Column(name = "MSG_THUMB_MO_IMG", length = 50)
    private String msgThumbMoImg;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Column(name = "DEL_ADM")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

    @Column(name = "UP_ADM")
    private Long upAdm;

    @Column(name = "UP_DATE")
    private Instant upDate;

    @Column(name = "MSG_TITLE_COLOR")
    private String msgTitleColor;

    @Column(name = "MSG_IMG_TF")
    private String msgImgTf;

    @Column(name = "MSG_CONTENTS_COLOR")
    private String getMsgContentsColor;

    @Column(name = "MSG_BUTTON")
    private String msgButton;

    @Column(name = "MSG_BUTTON_URL_TARGET_M")
    private String msgButtonUrlTargetM;

    @Column(name = "MSG_BUTTON_BG_COLOR")
    private String msgButtonBgColor;

    @Column(name = "MSG_LEAVE_LINE_COLOR")
    private String msgLeaveLineColor;

    @Column(name = "MSG_LEAVE_COLOR")
    private String msgLeaveColor;

    @Column(name = "MSG_LEAVE_TF")
    private String msgLeaveTf;

    @Column(name = "MSG_REGIST_TF")
    private String msgRegistTf;

    @Column(name = "MSG_REGIST_LINE_COLOR")
    private String msgRegistLineColor;

    @Column(name = "MSG_REGIST_COLOR")
    private String msgRegistColor;

    @Column(name = "MSG_MOBILE_CALLING_TF")
    private String msgMobileCallingTf;

    @Column(name = "MSG_MOBILE_CALLING_LINE_COLOR")
    private String msgMobileCallingLineColor;

    @Column(name = "MSG_MOBILE_CALLING_COLOR")
    private String msgMobileCallingColor;

    @Column(name = "MSG_BACK_DIM_TF")
    private String msgBackDimTf;

    @Column(name = "MSG_REVIEW_TF")
    private String msgReviewTf;

    @Column(name = "MSG_REVIEW_TYPE")
    private String msgReviewType;

    @Column(name = "MSG_REVIEW_DAYS")
    private String msgReviewDays;

    @Column(name = "MSG_SAVE_TF")
    private String msgSaveTf;

    @Column(name = "MSG_PC_HTML")
    private String msgPcHtml;

    @Column(name = "MSG_MO_HTML")
    private String msgMoHtml;

    @Column(name = "MSG_CAPTURE_NAME")
    private String msgCaptureName;

    @Size(max = 20)
    @Column(name = "MSG_BAN_BG_COLOR")
    private String msgBanBgColor;

    @Size(max = 20)
    @Column(name = "MSG_BAN_TEXT_COLOR")
    private String msgBanTextColor;

    @Column(name = "MSG_ACTION_EFFECT_TYPE")
    private String msgActionEffectType;
}