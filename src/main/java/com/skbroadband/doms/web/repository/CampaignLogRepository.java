package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.CampaignLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CampaignRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface CampaignLogRepository extends JpaRepository<CampaignLog, Long> {

    @Query(value = "select avg(campaignLog.expTime) from CampaignLog campaignLog inner join Campaign campaign " +
            "on campaign = campaignLog.caNo " +
            "where campaign.id = :caId and campaignLog.deviceType = :deviceType")
    Float getAvg(@Param("caId") Long caId, @Param("deviceType") String deviceType);

    @Query(value = "select avg(campaignLog.expTime) from CampaignLog campaignLog inner join Campaign campaign " +
            "on campaign = campaignLog.caNo " +
            "where campaign.id = :caId")
    Float getTotalAvg(@Param("caId") Long caId);

}
