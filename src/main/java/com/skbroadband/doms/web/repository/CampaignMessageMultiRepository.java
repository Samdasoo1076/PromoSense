package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignMessageMultiRepository extends JpaRepository<CampaignMessageMulti, Long> {
    List<CampaignMessageMulti> findCampaignMessageMultiByCaNoOrderByMultiSeqAsc(Long caNo);
    void deleteCampaignMessageMultiByCaNo(Long caNo);

    Integer countByCaNo(Long id);
}
