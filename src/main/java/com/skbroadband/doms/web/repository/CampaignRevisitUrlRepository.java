package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignRevisitUrl;
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
public interface CampaignRevisitUrlRepository extends JpaRepository<CampaignRevisitUrl, Long> {
    long countAllBy();
    Optional<CampaignRevisitUrl> findById(Long caNo);

    List<CampaignRevisitUrl> findByCaNo(Campaign campaign);

    void deleteByCaNo(Campaign campaign);

}
