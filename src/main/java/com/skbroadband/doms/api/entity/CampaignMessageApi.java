package com.skbroadband.doms.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TBL_CAMPAIGN_MESSAGE")
public class CampaignMessageApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSG_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_NO", nullable = false)
    private CampaignApi caNo;

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

    @Size(max = 20)
    @Column(name = "MSG_BAN_BG_COLOR", length = 20)
    private String msgBanBgColor;

    @Size(max = 20)
    @Column(name = "MSG_BAN_TEXT_COLOR", length = 20)
    private String msgBanTextColor;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Column(name = "REG_ADM", columnDefinition = "INT UNSIGNED")
    private Long regAdm;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "UP_ADM", columnDefinition = "INT UNSIGNED")
    private Long upAdm;

    @Column(name = "UP_DATE")
    private LocalDateTime upDate;

    @Column(name = "DEL_ADM", columnDefinition = "INT UNSIGNED")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private LocalDateTime delDate;

    @Size(max = 10)
    @Column(name = "MSG_TITLE_COLOR", length = 10)
    private String msgTitleColor;

    @Column(name = "MSG_IMG_TF")
    private String msgImgTf;

    @Size(max = 10)
    @Column(name = "MSG_CONTENTS_COLOR", length = 10)
    private String msgContentsColor;

    @Size(max = 100)
    @Column(name = "MSG_BUTTON", length = 100)
    private String msgButton;

    @Column(name = "MSG_BUTTON_URL_TARGET_M")
    private String msgButtonUrlTargetM;

    @Size(max = 20)
    @Column(name = "MSG_BUTTON_BG_COLOR", length = 20)
    private String msgButtonBgColor;

    @Size(max = 20)
    @Column(name = "MSG_LEAVE_LINE_COLOR", length = 20)
    private String msgLeaveLineColor;

    @Size(max = 20)
    @Column(name = "MSG_LEAVE_COLOR", length = 20)
    private String msgLeaveColor;

    @Column(name = "MSG_LEAVE_TF")
    private String msgLeaveTf;

    @Column(name = "MSG_REGIST_TF")
    private String msgRegistTf;

    @Size(max = 20)
    @Column(name = "MSG_REGIST_LINE_COLOR", length = 20)
    private String msgRegistLineColor;

    @Size(max = 20)
    @Column(name = "MSG_REGIST_COLOR", length = 20)
    private String msgRegistColor;

    @Column(name = "MSG_MOBILE_CALLING_TF")
    private String msgMobileCallingTf;

    @Size(max = 20)
    @Column(name = "MSG_MOBILE_CALLING_LINE_COLOR", length = 20)
    private String msgMobileCallingLineColor;

    @Size(max = 20)
    @Column(name = "MSG_MOBILE_CALLING_COLOR", length = 20)
    private String msgMobileCallingColor;

    @Column(name = "MSG_BACK_DIM_TF")
    private String msgBackDimTf;

    @Column(name = "MSG_REVIEW_TF")
    private String msgReviewTf;

    @Size(max = 20)
    @Column(name = "MSG_REVIEW_TYPE", length = 20)
    private String msgReviewType;

    @Size(max = 3)
    @Column(name = "MSG_REVIEW_DAYS", length = 3)
    private String msgReviewDays;

    @Column(name = "MSG_SAVE_TF")
    private String msgSaveTf;

    @Column(name = "MSG_PC_HTML")
    private String msgPcHtml;

    @Column(name = "MSG_MO_HTML")
    private String msgMoHtml;

    @Column(name = "MSG_ACTION_EFFECT_TYPE")
    private String msgActionEffectType;
}