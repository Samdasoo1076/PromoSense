package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.dto.CampaignMessageHistoryDto;
import com.skbroadband.doms.web.entity.CampaignMessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CampaignRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface CampaignMessageHistoryRepository extends JpaRepository<CampaignMessageHistory, Long> {
    List<CampaignMessageHistory> findByMsgTypeAndAndUseTfAndDelTf(String msgType, String useTf, String delTf);

    @Modifying(clearAutomatically = true)
    @Query("update CampaignMessageHistory cms set cms.useTf=:#{#param.useTf}, cms.delTf=:#{#param.delTf}, cms.delAdm=:#{#param.delAdm}" +
            ", cms.delDate=:#{#param.delDate} where cms.id=:#{#param.id}")
    void deleteHistory(@Param("param") CampaignMessageHistoryDto param);

}
