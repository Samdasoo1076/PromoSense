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
@Table(name = "TBL_CAMPAIGN_MESSAGE_MULTI")
public class CampaignMessageMulti extends CreateTimeBase {
    @Id
    @Column(name = "MULTI_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long multiNo;

    @Column(name = "CA_NO", nullable = false)
    private Long caNo;

    @Column(name = "MULTI_SEQ", nullable = false)
    private int multiSeq;

    @Size(max = 100)
    @Column(name = "MSG_IMG_NM_01", length = 100)
    private String msgImgNm01;

    @Size(max = 100)
    @Column(name = "MSG_IMG_RNM_01", length = 100)
    private String msgImgRnm01;

    @Size(max = 300)
    @Column(name = "MSG_IMG_ALTER_01", length = 300)
    private String msgImgAlter01;

    @Column(name = "MSG_BUTTON", length = 100)
    private String msgButton;

    @Column(name = "MSG_BUTTON_TF", columnDefinition = "char default 'N'")
    private char msgButtonTf;

    @Column(name = "MSG_BUTTON_COLOR", length = 20)
    private String msgButtonColor;

    @Column(name = "MSG_BUTTON_BG_COLOR", length = 20)
    private String msgButtonBgColor;

    @Column(name = "MSG_BUTTON_PC_URL", length = 300)
    private String msgButtonPcUrl;

    @Column(name = "MSG_BUTTON_MO_URL", length = 300)
    private String msgButtonMoUrl;

    @Column(name = "MSG_BUTTON_URL_TARGET_P", columnDefinition = "char default 'S'")
    private char msgButtonUrlTargetP;

    @Column(name = "MSG_BUTTON_URL_TARGET_M", columnDefinition = "char default 'S'")
    private char msgButtonUrlTargetM;

    @Column(name = "USE_TF", columnDefinition = "char default ' '")
    private char useTf;

    @Column(name = "DEL_TF", columnDefinition = "char default 'N'")
    private char delTf;

    @Column(name = "REG_ADM", columnDefinition = "int(11) unsigned")
    private Long regAdm;

    @Column(name = "REG_DATE", columnDefinition = "datetime default current_timestamp()")
    private Instant regDate;

    @Column(name = "UP_ADM", columnDefinition = "int(11) unsigned")
    private Long upAdm;

    @Column(name = "UP_DATE")
    private Instant upDate;

    @Column(name = "DEL_ADM", columnDefinition = "int(11) unsigned")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;
}