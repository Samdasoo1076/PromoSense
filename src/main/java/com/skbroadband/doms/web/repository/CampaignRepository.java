package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    long countAllBy();
    Optional<Campaign> findById(Long caNo);
    List<Campaign> findByIdIn(List<Long> caNo);

    @Modifying(clearAutomatically = true)
    @Query("update Campaign campaign set campaign.viewOrder=:order, campaign.upAdm=:admNo, campaign.upDate=current_timestamp where campaign.id=:id")
    void changeViewOrder(@Param("id") Long id, @Param("order") Integer order, @Param("admNo") Long admNo);
}
