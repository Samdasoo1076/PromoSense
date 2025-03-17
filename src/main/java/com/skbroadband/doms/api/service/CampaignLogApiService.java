package com.skbroadband.doms.api.service;

import com.skbroadband.doms.api.mapper.CampaignLogApiMapper;
import com.skbroadband.doms.api.repository.CampaignApiRepository;
import com.skbroadband.doms.api.repository.CampaignLogApiRepository;
import com.skbroadband.doms.api.request.LogApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.service
 * @File : CampaignLogApiService
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class CampaignLogApiService {
    private final CampaignLogApiRepository campaignLogRepository;
    private final CampaignApiRepository campaignApiRepository;

    private final CampaignLogApiMapper campaignLogMapper;
    @Transactional(value = "apiTransactionManager")
    public void logging(LogApiRequest logRequest) {
        campaignLogRepository.save(campaignLogMapper.toEntity(logRequest));

        switch(logRequest.getEVENT_TYPE()) {
            case "SHOW":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateViewCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileViewCnt(logRequest.getCA_NO());
                }
                break;
            case "LINKCLICK":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateClickCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileClickCnt(logRequest.getCA_NO());
                }
                break;
            case "CLOSECLICK":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateCloseCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileCloseCnt(logRequest.getCA_NO());
                }
                break;
            case "PHONECLICK":
                    campaignApiRepository.updatePhoneCnt(logRequest.getCA_NO());
                break;
            case "VISIT":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateVisitCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileVisitCnt(logRequest.getCA_NO());
                }
                break;
            case "CONTACT":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateContactCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileContactCnt(logRequest.getCA_NO());
                }
                break;
            case "SIGNUP":
                if("P".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateSignUpCnt(logRequest.getCA_NO());
                }else if("M".equals(logRequest.getDEVICE_TYPE())) {
                    campaignApiRepository.updateMobileSignUpCnt(logRequest.getCA_NO());
                }
                break;
        }
    }
}
