package com.skbroadband.doms.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.thymeleaf.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignDto {
    private Long id;
    private String caName;
    private String caState;
    private String caOnOff;
    private String caStateNm;
    private String tgType01;
    private String tgType01Nm;
    private String tgType02;
    private String tgType02Nm;
    private String caGubun;
    private String caMemo;
    private String caAlldayYn;
    private String tg1Sub;
    private String tg1SubNm;
    private String tgFixedWord;
    private String caStartTf;
    private Instant caStartDate;
    private String caStartDateStr;
    private String caStartHour;
    private Integer caStartHourInt;
    private String caStartMin;
    private String caEndTf;
    private Instant caEndDate;
    private String caEndDateStr;
    private String caEndHour;
    private Integer caEndHourInt;
    private String caEndMin;
    private String caWeek;
    private String caTime;
    private String caTimeNm;
    private List<String> caTimes;
    private Integer caStaySec;
    private String caPurpose;
    private String caPurposeNm;
    private String exposureUrlTf;
    private String exposureLimitCnt;
    private String exposureLimitCntNm;
    private Integer scrollPercent;
    private String tagetUrlTf;
    private Integer visibleCnt;
    private Integer clickCnt;
    private Integer closeCnt;
    private Integer phoneCnt;
    private Integer targetVisitCnt;
    private String useTf;
    private String delTf;
    private Long delAdm;
    private Instant delDate;
    private String caPlace;
    private String caPlaceNm;
    private String caViewPoint;
    private String caViewPointNm;
    private String viewPointSub;
    private String viewPointSubNm;
    private Long caMsgType;
    private String caMsgTypeNm;
    private String tgType02Yn;
    private String caPlaceYn;
    private Long upAdm;
    private Instant upDate;
    private Instant regDate;
    private Integer viewOrder;
    private Integer leaveCnt;
    private Integer registCnt;
    private Integer visibleMoCnt;
    private Integer clickMoCnt;
    private Integer closeMoCnt;
    private Integer targetVisitMoCnt;
    private Integer leaveMoCnt;
    private Integer registMoCnt;
    private String tg2Sub;
    private String tg2SubNm;
    private Float totalExpAvg;
    private Float moExpAvg;
    private Float pcExpAvg;
    private CampaignMessageDto campaignMessageDto;
    private List<CampaignTargetUrlDto> campaignTargetUrlDtos;
    private List<CampaignExposureUrlDto> campaignExposureUrlDtos;
    private List<CampaignExposureTimeDto> campaignExposureTimeDtos;
    private List<CampaignRevisitUrlDto> campaignRevisitUrlDtos;
    private List<CampaignSpecUrlDto> campaignSpecUrlDtos;


    @QueryProjection
    public CampaignDto(Long caNo, String caName, String caState, String tgType01, String tgType02
            , String caStartTf, Instant caStartDate, String caStartHour, String caStartMin, String caEndTf
            , Instant caEndDate, String caEndHour, String caEndMin, String caWeek, String caTime
            , Integer caStaySec, String exposureUrlTf, String exposureLimitCnt, Integer scrollPercent
            , Integer visibleCnt, Integer clickCnt, Integer closeCnt, Integer phoneCnt
            , Integer targetVisitCnt, String useTf, String delTf, Long delAdm, Instant delDate
            , Integer viewOrder, String caMsgTypeNm, String caStateNm, String caPlace, String caViewPoint, Instant upDate
            , Instant regDate, String caMemo, String caPurpose, String viewPointSub, String caAlldayYn, Integer leaveCnt,Integer  registCnt
            , Integer visibleMoCnt, Integer clickMoCnt, Integer closeMoCnt, Integer targetVisitMoCnt, Integer leaveMoCnt, Integer registMoCnt
            , String tg1Sub, String tg2Sub, String tgType02Yn, String tgFixedWord, String caPlaceYn, String caGubun){

        this.id=caNo;
        this.caName=caName;
        this.caState=caState;
        this.tgType01=tgType01;
        this.tgType02=tgType02;
        this.caStartTf=caStartTf;
        this.caStartDate=caStartDate;
        this.caStartHour=caStartHour;
        this.caStartMin=caStartMin;
        this.caEndTf=caEndTf;
        this.caEndDate=caEndDate;
        this.caEndHour=caEndHour;
        this.caEndMin=caEndMin;
        this.caWeek=caWeek;
        this.caTime=caTime;
        this.caStaySec=caStaySec;
        this.exposureUrlTf=exposureUrlTf;
        this.exposureLimitCnt=exposureLimitCnt;
        this.scrollPercent=scrollPercent;
        this.visibleCnt=visibleCnt;
        this.clickCnt=clickCnt;
        this.closeCnt=closeCnt;
        this.phoneCnt=phoneCnt;
        this.targetVisitCnt=targetVisitCnt;
        this.useTf=useTf;
        this.delTf=delTf;
        this.delAdm=delAdm;
        this.delDate=delDate;
        this.viewOrder=viewOrder;
        this.caMsgTypeNm=caMsgTypeNm;
        this.caStateNm=caStateNm;
        this.caPlace=caPlace;
        this.caViewPoint=caViewPoint;
        this.upDate=upDate;
        this.regDate=regDate;
        this.caMemo=caMemo;
        this.caPurpose=caPurpose;
        this.viewPointSub=viewPointSub;
        this.caAlldayYn=caAlldayYn;
        this.caStartHourInt = (StringUtils.isEmpty(caStartHour))? 0 : Integer.parseInt(caStartHour);
        this.caEndHourInt = (StringUtils.isEmpty(caEndHour))? 0 : Integer.parseInt(caEndHour);
        this.leaveCnt = leaveCnt;
        this.registCnt = registCnt;
        this.visibleMoCnt = visibleMoCnt;
        this.clickMoCnt = clickMoCnt;
        this.closeMoCnt = closeMoCnt;
        this.targetVisitMoCnt = targetVisitMoCnt;
        this.leaveMoCnt = leaveMoCnt;
        this.registMoCnt = registMoCnt;
        this.tg1Sub =  tg1Sub;
        this.tg2Sub = tg2Sub;
        this.tgType02Yn = tgType02Yn;
        this.tgFixedWord = tgFixedWord;
        this.caPlaceYn = caPlaceYn;
        this.caGubun = caGubun;
    }
//    @QueryProjection
//    public CampaignDto(Long caNo, String caName, String caState, String caStateNm, Long caMsgType, String caMsgTypeNm
//            , Instant caStartDate, String caStartHour, String caStartMin, Instant caEndDate, String caEndHour, String caEndMin
//            , String exposureUrlTf, String exposureLimitCnt, Integer visibleCnt, Integer clickCnt, Integer closeCnt, Integer phoneCnt
//            , Integer targetVisitCnt, String useTf, String delTf, Instant upDate, Integer viewOrder
//            ){
//
//        this.id=caNo;
//        this.caName=caName;
//        this.caState=caState;
//        this.caStateNm= caStateNm;
//        this.caMsgType= caMsgType;
//        this.caMsgTypeNm= caMsgTypeNm;
//        this.caStartDate=caStartDate;
//        this.caStartHour=caStartHour;
//        this.caStartMin=caStartMin;
//        this.caEndDate=caEndDate;
//        this.caEndHour=caEndHour;
//        this.caEndMin=caEndMin;
//        this.exposureUrlTf=exposureUrlTf;
//        this.exposureLimitCnt=exposureLimitCnt;
//        this.visibleCnt=visibleCnt;
//        this.clickCnt=clickCnt;
//        this.closeCnt=closeCnt;
//        this.phoneCnt=phoneCnt;
//        this.targetVisitCnt=targetVisitCnt;
//        this.useTf=useTf;
//        this.delTf=delTf;
//        this.upDate=upDate;
//        this.viewOrder=viewOrder;
//    }



    @QueryProjection
    public CampaignDto(Long id, String caName, Integer visibleCnt, Integer clickCnt, Integer closeCnt, Integer phoneCnt,
                       Integer targetVisitCnt, Integer leaveCnt, Integer registCnt, Integer visibleMoCnt, Integer clickMoCnt,
                       Integer closeMoCnt, Integer targetVisitMoCnt, Integer leaveMoCnt, Integer registMoCnt, Long caMsgType,
                       String caMsgTypeNm, String caAlldayYn, Instant caStartDate, String caStartHour, String caStartMin,
                       Instant caEndDate, String caEndHour, String caEndMin, String caStateNm) {
        this.id = id;
        this.caName = caName;
        this.visibleCnt = visibleCnt;
        this.clickCnt = clickCnt;
        this.closeCnt = closeCnt;
        this.phoneCnt = phoneCnt;
        this.targetVisitCnt = targetVisitCnt;
        this.leaveCnt = leaveCnt;
        this.registCnt = registCnt;
        this.visibleMoCnt = visibleMoCnt;
        this.clickMoCnt = clickMoCnt;
        this.closeMoCnt = closeMoCnt;
        this.targetVisitMoCnt = targetVisitMoCnt;
        this.leaveMoCnt = leaveMoCnt;
        this.registMoCnt = registMoCnt;
        this.caMsgType = caMsgType;
        this.caMsgTypeNm = caMsgTypeNm;
        this.caAlldayYn = caAlldayYn;
        this.caStartDate = caStartDate;
        this.caStartHour = caStartHour;
        this.caStartHourInt = (StringUtils.isEmpty(caStartHour))? 0 : Integer.parseInt(caStartHour);
        this.caStartMin = caStartMin;
        this.caEndDate = caEndDate;
        this.caEndHour = caEndHour;
        this.caEndHourInt = (StringUtils.isEmpty(caEndHour))? 0 : Integer.parseInt(caEndHour);
        this.caEndMin = caEndMin;
        this.caStateNm = caStateNm;
    }

}