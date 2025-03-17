package com.skbroadband.doms.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TBL_CAMPAIGN")
public class CampaignApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CA_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @Column(name = "CA_NAME")
    private String caName;

    @Column(name = "CA_STATE")
    private String caState;

    @Size(max = 20)
    @Column(name = "TG_TYPE01", length = 20)
    private String tgType01;

    @Size(max = 20)
    @Column(name = "TG_TYPE02", length = 20)
    private String tgType02;

    @Column(name = "CA_START_TF")
    private String caStartTf;

    @Column(name = "CA_START_DATE")
    private LocalDateTime caStartDate;

    @Size(max = 2)
    @Column(name = "CA_START_HOUR", length = 2)
    private String caStartHour;

    @Size(max = 2)
    @Column(name = "CA_START_MIN", length = 2)
    private String caStartMin;

    @Column(name = "CA_END_TF")
    private String caEndTf;

    @Column(name = "CA_END_DATE")
    private LocalDateTime caEndDate;

    @Size(max = 2)
    @Column(name = "CA_END_HOUR", length = 2)
    private String caEndHour;

    @Size(max = 2)
    @Column(name = "CA_END_MIN", length = 2)
    private String caEndMin;

    @Size(max = 20)
    @Column(name = "CA_WEEK", length = 20)
    private String caWeek;

    @Size(max = 20)
    @Column(name = "CA_TIME", length = 20)
    private String caTime;

    @Column(name = "CA_STAY_SEC")
    private Integer caStaySec;

    @Column(name = "EXPOSURE_URL_TF")
    private String exposureUrlTf;

    @Size(max = 20)
    @Column(name = "EXPOSURE_LIMIT_CNT", length = 20)
    private String exposureLimitCnt;

    @Column(name = "SCROLL_PERCENT")
    private Integer scrollPercent;

    @Size(max = 20)
    @Column(name = "CA_PURPOSE", length = 20)
    private String caPurpose;

    @Column(name = "VISIBLE_CNT")
    private Integer visibleCnt;

    @Column(name = "VISIBLE_MO_CNT")
    private Integer visibleMoCnt;

    @Column(name = "CLICK_CNT")
    private Integer clickCnt;

    @Column(name = "CLICK_MO_CNT")
    private Integer clickMoCnt;

    @Column(name = "CLOSE_CNT")
    private Integer closeCnt;

    @Column(name = "CLOSE_MO_CNT")
    private Integer closeMoCnt;

    @Column(name = "PHONE_CNT")
    private Integer phoneCnt;

    @Column(name = "TARGET_VISIT_CNT")
    private Integer targetVisitCnt;

    @Column(name = "TARGET_VISIT_MO_CNT")
    private Integer targetVisitMoCnt;

    @Column(name = "LEAVE_CNT")
    private Integer leaveCnt;

    @Column(name = "LEAVE_MO_CNT")
    private Integer leaveMoCnt;

    @Column(name = "REGIST_CNT")
    private Integer registCnt;

    @Column(name = "REGIST_MO_CNT")
    private Integer registMoCnt;

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

    @Size(max = 20)
    @Column(name = "CA_GUBUN", length = 20)
    private String caGubun;

    @Size(max = 500)
    @Column(name = "CA_MEMO", length = 500)
    private String caMemo;

    @Column(name = "CA_ALLDAY_YN")
    private String caAlldayYn;

    @Size(max = 20)
    @Column(name = "TG1_SUB", length = 20)
    private String tg1Sub;

    @Size(max = 20)
    @Column(name = "TG2_SUB", length = 20)
    private String tg2Sub;

    @Size(max = 200)
    @Column(name = "TG_FIXED_WORD", length = 200)
    private String tgFixedWord;

    @Size(max = 20)
    @Column(name = "CA_PLACE", length = 20)
    private String caPlace;

    @Size(max = 20)
    @Column(name = "CA_VIEW_POINT", length = 20)
    private String caViewPoint;

    @Size(max = 20)
    @Column(name = "VIEW_POINT_SUB", length = 20)
    private String viewPointSub;

    @Column(name = "CA_MSG_TYPE")
    private Integer caMsgType;

    @Column(name = "TG_TYPE02_YN")
    private String tgType02Yn;

    @Column(name = "CA_PLACE_YN")
    private String caPlaceYn;

    @Column(name = "VIEW_ORDER")
    private Integer viewOrder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private List<CampaignMessageApi> campaignMessageApis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private Set<CampaignExposureTimeApi>  campaignExposureTimeApis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private Set<CampaignExposureUrlApi> campaignExposureUrlApis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private Set<CampaignRevisitUrlApi> campaignRevisitUrlApis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private Set<CampaignSpecUrlApi> campaignSpecUrlApis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caNo")
    private Set<CampaignTargetUrlApi> campaignTargetUrlApis;
}