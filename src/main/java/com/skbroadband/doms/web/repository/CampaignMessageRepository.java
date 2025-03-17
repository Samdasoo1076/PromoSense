package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CampaignRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface CampaignMessageRepository extends JpaRepository<CampaignMessage, Long> {

    CampaignMessage findByCaNoAndUseTfAndDelTf(Campaign caNo, String useTf, String delTf);

}
