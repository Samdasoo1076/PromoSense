package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.CampaignBaseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CodeDetailRepository
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */
public interface CampaignBaseTypeRepository extends JpaRepository<CampaignBaseType, String> {

    List<CampaignBaseType> findAllByUseTfAndDelTfOrderByBaseOrder(String useTf, String delTf);

    CampaignBaseType findById(Long id);

}
