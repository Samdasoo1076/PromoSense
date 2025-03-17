package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignExposureTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CampaignRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface CampaignExposureTimeRepository extends JpaRepository<CampaignExposureTime, Long> {
    long countAllBy();
    Optional<CampaignExposureTime> findById(Long caNo);

    List<CampaignExposureTime> findByCaNo(Campaign campaign);

    List<CampaignExposureTime> findByCaNoOrderByStartTime(Campaign campaign);

    void deleteByCaNo(Campaign campaign);
}
