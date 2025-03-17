package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CAMPAIGN")
public class Campaign extends CreateTimeBase {
    @Id
    @Column(name = "CA_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Size(max = 200)
    @Column(name = "TG_FIXED_WORD", length = 200)
    private String tgFixedWord;

    @Column(name = "CA_START_TF")
    private String caStartTf;

    @Column(name = "CA_START_DATE")
    private Instant caStartDate;

    @Size(max = 2)
    @Column(name = "CA_START_HOUR", length = 2)
    private String caStartHour;

    @Size(max = 2)
    @Column(name = "CA_START_MIN", length = 2)
    private String caStartMin;

    @Column(name = "CA_END_TF")
    private String caEndTf;

    @Column(name = "CA_END_DATE")
    private Instant caEndDate;

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

    @Column(name = "CA_PURPOSE")
    private String caPurpose;

    @Size(max = 20)
    @Column(name = "EXPOSURE_LIMIT_CNT", length = 20)
    private String exposureLimitCnt;

    @Column(name = "SCROLL_PERCENT")
    private Integer scrollPercent;

    /*@Column(name = "TAGET_URL_TF")
    private String tagetUrlTf;*/

    @Column(name = "VISIBLE_CNT", updatable = false)
    private Integer visibleCnt;

    @Column(name = "CLICK_CNT", updatable = false)
    private Integer clickCnt;

    @Column(name = "CLOSE_CNT", updatable = false)
    private Integer closeCnt;

    @Column(name = "PHONE_CNT", updatable = false)
    private Integer phoneCnt;

    @Column(name = "TARGET_VISIT_CNT", updatable = false)
    private Integer targetVisitCnt;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Column(name = "DEL_ADM")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

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
    private Long caMsgType;

    @Column(name = "TG_TYPE02_YN")
    private String tgType02Yn;

    @Column(name = "CA_PLACE_YN")
    private String caPlaceYn;

    @Column(name = "UP_ADM")
    private Long upAdm;

    @Column(name = "UP_DATE")
    private Instant upDate;

    @Column(name = "VIEW_ORDER")
    private Integer viewOrder;

    @Column(name = "LEAVE_CNT", updatable = false)
    private Integer leaveCnt;

    @Column(name = "REGIST_CNT", updatable = false)
    private Integer registCnt;

    @Column(name = "VISIBLE_MO_CNT", updatable = false)
    private Integer visibleMoCnt;

    @Column(name = "CLICK_MO_CNT", updatable = false)
    private Integer clickMoCnt;

    @Column(name = "CLOSE_MO_CNT", updatable = false)
    private Integer closeMoCnt;

    @Column(name = "TARGET_VISIT_MO_CNT", updatable = false)
    private Integer targetVisitMoCnt;

    @Column(name = "LEAVE_MO_CNT", updatable = false)
    private Integer leaveMoCnt;

    @Column(name = "REGIST_MO_CNT", updatable = false)
    private Integer registMoCnt;

    @Size(max = 20)
    @Column(name = "TG2_SUB", length = 20)
    private String tg2Sub;

    @OneToMany(mappedBy = "caNo", cascade = CascadeType.ALL)
    private Set<CampaignExposureTime> campaignExposureTimes;
    public void setCampaignExposureTime(HashSet<CampaignExposureTime> param) {
        campaignExposureTimes = param;
    }

    public void addCampaignExposureTime(CampaignExposureTime campaignExposureTime) {
        this.getCampaignExposureTimes().add(campaignExposureTime);
        campaignExposureTime.setCaNo(this);
    }

    @OneToMany(mappedBy = "caNo", cascade = CascadeType.ALL)
    private Set<CampaignExposureUrl> campaignExposureUrls;
    public void setCampaignExposureUrl(HashSet<CampaignExposureUrl> param) {
        campaignExposureUrls = param;
    }

    public void addCampaignExposureUrl(CampaignExposureUrl campaignExposureUrl) {
        this.getCampaignExposureUrls().add(campaignExposureUrl);
        campaignExposureUrl.setCaNo(this);
    }

    @OneToMany(mappedBy = "caNo", cascade = CascadeType.ALL)
    private Set<CampaignRevisitUrl> campaignRevisitUrls;
    public void setCampaignRevisitUrl(HashSet<CampaignRevisitUrl> param) {
        campaignRevisitUrls = param;
    }

    public void addCampaignRevisitUrl(CampaignRevisitUrl campaignRevisitUrl) {
        this.getCampaignRevisitUrls().add(campaignRevisitUrl);
        campaignRevisitUrl.setCaNo(this);
    }

    @OneToMany(mappedBy = "caNo", cascade = CascadeType.ALL)
    private Set<CampaignSpecUrl> campaignSpecUrls;
    public void setCampaignSpecUrl(HashSet<CampaignSpecUrl> param) {
        campaignSpecUrls = param;
    }

    public void addCampaignSpecUrl(CampaignSpecUrl campaignSpecUrl) {
        this.getCampaignSpecUrls().add(campaignSpecUrl);
        campaignSpecUrl.setCaNo(this);
    }

    @OneToMany(mappedBy = "caNo", cascade = CascadeType.ALL)
    private Set<CampaignTargetUrl> campaignTargetUrls;
    public void setCampaignTargetUrl(HashSet<CampaignTargetUrl> param) {
        campaignTargetUrls = param;
    }

    public void addCampaignTargetUrl(CampaignTargetUrl campaignTargetUrl) {
        this.getCampaignTargetUrls().add(campaignTargetUrl);
        campaignTargetUrl.setCaNo(this);
    }

    /**
     * 캠패인 onOff 변경
     * 등록중인 캠패인은 제외하고 변경
     */
    public void changeCaState(String useTf, Long upAdm){
        if(!this.caState.equals("1")){
            this.useTf = useTf;
            this.upAdm = upAdm;
            this.upDate = Instant.now();
        }
    }

    /**
     * 캠패인 삭제
     */
    public void deleteCampaign(Long delAdm) {
        this.delTf = "Y";
        this.delAdm = delAdm;
        this.delDate = Instant.now();
    }

    public void updateMsgStatus(String caStatus, Long upAdm) {
        this.caState = caStatus;
        this.upAdm = upAdm;
        this.upDate = Instant.now();
    }

}