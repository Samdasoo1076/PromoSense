package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignSpecUrl;
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
public interface CampaignSpecUrlRepository extends JpaRepository<CampaignSpecUrl, Long> {
    long countAllBy();
    Optional<CampaignSpecUrl> findById(Long caNo);

    List<CampaignSpecUrl> findByCaNo(Campaign campaign);

    void deleteByCaNo(Campaign campaign);

}
