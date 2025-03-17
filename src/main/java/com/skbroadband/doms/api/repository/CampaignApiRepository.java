package com.skbroadband.doms.api.repository;

import com.skbroadband.doms.api.entity.CampaignApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.repository
 * @File : CampaignApiRepository
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public interface CampaignApiRepository extends JpaRepository<CampaignApi, Long> {
    @Modifying
    @Query("update CampaignApi campaign set campaign.visibleCnt=campaign.visibleCnt+1 where campaign.id=:id")
    void updateViewCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.visibleMoCnt=campaign.visibleMoCnt+1 where campaign.id=:id")
    void updateMobileViewCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.clickCnt=campaign.clickCnt+1 where campaign.id=:id")
    void updateClickCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.clickMoCnt=campaign.clickMoCnt+1 where campaign.id=:id")
    void updateMobileClickCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.closeCnt=campaign.closeCnt+1 where campaign.id=:id")
    void updateCloseCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.closeMoCnt=campaign.closeMoCnt+1 where campaign.id=:id")
    void updateMobileCloseCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.phoneCnt=campaign.phoneCnt+1 where campaign.id=:id")
    void updatePhoneCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.targetVisitCnt=campaign.targetVisitCnt+1 where campaign.id=:id")
    void updateVisitCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.targetVisitMoCnt=campaign.targetVisitMoCnt+1 where campaign.id=:id")
    void updateMobileVisitCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.leaveCnt=campaign.leaveCnt+1 where campaign.id=:id")
    void updateContactCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.leaveMoCnt=campaign.leaveMoCnt+1 where campaign.id=:id")
    void updateMobileContactCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.registCnt=campaign.registCnt+1 where campaign.id=:id")
    void updateSignUpCnt(@Param("id") Long caNo);

    @Modifying
    @Query("update CampaignApi campaign set campaign.registMoCnt=campaign.registMoCnt+1 where campaign.id=:id")
    void updateMobileSignUpCnt(@Param("id") Long caNo);
}
