package com.skbroadband.doms.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CampaignAnalysisLogDto {
    private Long id;
    private String deviceType;
    private String eventType;
    private Integer expTime;
    private Instant regDate;
    private Integer multiNo;


    private Integer viewCnt;
    private Integer viewMoCnt;
    private Integer linkClickCnt;
    private Integer linkClickMoCnt;
    private Integer contactCnt;
    private Integer contactMoCnt;
    private Integer signUpCnt;
    private Integer signUpMoCnt;
    private Integer phoneClickCnt;
    private Integer phoneClickMoCnt;
    private Integer closeClickCnt;
    private Integer closeClickMoCnt;
    private Integer visitCnt;
    private Integer visitMoCnt;
    private Integer expTimeAvg;
    private Integer expTimeMoAvg;
    private Integer msgLeaveCnt;
    private Integer msgLeaveMoCnt;
    private Integer totalCnt;
    private Integer totalMoCnt;

    @QueryProjection
    public CampaignAnalysisLogDto(Long id,
                             String deviceType,
                             String eventType,
                             Integer expTime,
                             Instant regDate,
                             Integer multiNo) {
        this.id = id;
        this.deviceType = deviceType;
        this.eventType = eventType;
        this.expTime = expTime;
        this.regDate = regDate;
        this.multiNo = multiNo;
    }

    public CampaignAnalysisLogDto(Integer num) {
        this.viewCnt = num;
        this.viewMoCnt = num;
        this.linkClickCnt = num;
        this.linkClickMoCnt = num;
        this.contactCnt = num;
        this.contactMoCnt = num;
        this.signUpCnt = num;
        this.signUpMoCnt = num;
        this.phoneClickCnt = num;
        this.phoneClickMoCnt = num;
        this.closeClickCnt = num;
        this.closeClickMoCnt = num;
        this.visitCnt = num;
        this.visitMoCnt = num;
        this.expTimeAvg = num;
        this.msgLeaveCnt = num;
        this.msgLeaveMoCnt = num;
        this.expTimeMoAvg = num;
        this.totalCnt = num;
        this.totalMoCnt = num;
        this.multiNo = num;
    }
}