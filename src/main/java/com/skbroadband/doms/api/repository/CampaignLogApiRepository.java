package com.skbroadband.doms.api.repository;

import com.skbroadband.doms.api.entity.CampaignLogApi;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.repository
 * @File : CampaignLogApiRepository
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public interface CampaignLogApiRepository extends JpaRepository<CampaignLogApi, Long> {
}
