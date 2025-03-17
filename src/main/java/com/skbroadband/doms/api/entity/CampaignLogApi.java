package com.skbroadband.doms.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CAMPAIGN_LOG")
public class CampaignLogApi {
    @Id
    @Column(name = "SEQ_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "CA_NO")
    private Long caNo;

    @Size(max = 5)
    @Column(name = "DEVICE_TYPE", length = 5)
    private String deviceType;

    @Size(max = 15)
    @Column(name = "EVENT_TYPE", length = 15)
    private String eventType;

    @Size(max = 15)
    @Column(name = "MSG_TYPE", length = 15)
    private String msgType;

    @Size(max = 500)
    @Column(name = "CA_REFERER", length = 500)
    private String caReferer;

    @Column(name = "COOKIE_CNT")
    private Integer cookieCnt;

    @Size(max = 30)
    @Column(name = "CA_YMD", length = 30)
    private String caYmd;

    @Size(max = 4)
    @Column(name = "CA_YEAR", length = 4)
    private String caYear;

    @Size(max = 30)
    @Column(name = "CA_MONTH", length = 30)
    private String caMonth;

    @Size(max = 2)
    @Column(name = "CA_DAY", length = 2)
    private String caDay;

    @Size(max = 2)
    @Column(name = "CA_HOUR", length = 2)
    private String caHour;

    @Size(max = 2)
    @Column(name = "CA_MIN", length = 2)
    private String caMin;

    @Size(max = 2)
    @Column(name = "CA_WEEK", length = 2)
    private String caWeek;

    @Column(name = "EXP_TIME_START")
    private LocalDateTime expTimeStart;

    @Column(name = "EXP_TIME_END")
    private LocalDateTime expTimeEnd;

    @Column(name = "EXP_TIME")
    private Integer expTime;

    @Size(max = 255)
    @Column(name = "CA_SESSION_ID")
    private String caSessionId;

    @Size(max = 255)
    @Column(name = "CA_COOKIE_ID")
    private String caCookieId;

    @Size(max = 2000)
    @Column(name = "CA_FULL_URL", length = 2000)
    private String caFullUrl;

    @Size(max = 150)
    @Column(name = "CA_TITLE", length = 150)
    private String caTitle;

    @Size(max = 20)
    @Column(name = "CA_IP", length = 20)
    private String caIp;

    @Size(max = 20)
    @Column(name = "CA_MENU_NO", length = 20)
    private String caMenuNo;

    @Size(max = 20)
    @Column(name = "CA_C_CODE", length = 20)
    private String caCCode;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "MULTI_NO")
    private Integer multiNo;
}