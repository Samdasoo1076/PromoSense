package com.skbroadband.doms.api.service;

import com.skbroadband.doms.api.entity.CampaignApi;
import com.skbroadband.doms.api.entity.CampaignExposureUrlApi;
import com.skbroadband.doms.api.mapper.CampaignTargetApiMapper;
import com.skbroadband.doms.api.reponse.CampaignApiResponse;
import com.skbroadband.doms.api.reponse.PreviewApiResponse;
import com.skbroadband.doms.api.repository.CampaignSupportApiRepository;
import com.skbroadband.doms.api.request.CampaignApiRequest;
import com.skbroadband.doms.api.request.PreviewApiRequest;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import com.skbroadband.doms.web.mapper.CampaignMessageMultiMapper;
import com.skbroadband.doms.web.repository.CampaignMessageMultiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.service
 * @File : CampaignApiService
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignApiService {
    private final CampaignSupportApiRepository campaignSupportRepository;
    private final CampaignTargetApiMapper campaignTargetMapper;

    private final CampaignMessageMultiRepository campaignMessageMultiRepository;
    private final CampaignMessageMultiMapper campaignMessageMultiMapper;

    /**
     * 캠페인 조회
     *
     * @param campaignRequest
     * @return
     */
    @Transactional(value = "apiTransactionManager", readOnly = true)
    public List<CampaignApiResponse> getCampaign(CampaignApiRequest campaignRequest) {
        List<CampaignApi> CampaignApis =  campaignSupportRepository.findCampaign(campaignRequest);

        return CampaignApis.stream()
                .filter(campaign -> predicate4FirstTarget()
                        .and(predicate4SecondTarget())
                        .and(predicate4ExposureTarget()).test(campaign, campaignRequest))
                .limit(1)
                .map(campaignApis -> campaignTargetMapper.toResponse(campaignApis, campaignRequest))
                .collect(Collectors.toList());
    }

    /**
     * 미리보기 조회
     *
     * @param previewRequest
     * @return
     */
    @Transactional(value = "apiTransactionManager", readOnly = true)
    public PreviewApiResponse getPreview(PreviewApiRequest previewRequest) {
        return campaignSupportRepository.findPreview(previewRequest)
                .orElseThrow(() -> new BadRequestException("캠페인이 존재하지 않습니다."));
    }

    /**
     * 멀티 조회
     *
     * @return
     */
    @Transactional(value = "apiTransactionManager", readOnly = true)
    public List<CampaignMessageMulti> getCampaignMessageMulti(Long caNo) {
        return campaignMessageMultiRepository.findCampaignMessageMultiByCaNoOrderByMultiSeqAsc(caNo);
    }

    /**
     * 1차타겟
     */
    private BiPredicate<CampaignApi, CampaignApiRequest> predicate4FirstTarget() {
        return (campaign, request) -> {
            List<String> paramsSubList = Arrays.asList((StringUtils.hasText(request.getTG1_SUB())?request.getTG1_SUB():"TAR1_ALL").split(","));
            String tg1Sub = campaign.getTg1Sub();

            if("RETURN".equals(campaign.getTgType01())) { // 재방문일 경우
                if(!StringUtils.hasText(tg1Sub)) {
                    return false;
                }

                if(tg1Sub.equals("TAR1_FIXED") && paramsSubList.contains("TAR1_FIXED")) { // 특정 페이지에 관심이 많은 고객 (최근 7일 동안 특정 페이지를 3번 이상 방문한 고객)
                    return campaign.getCampaignRevisitUrlApis().stream()
                            .filter(revisitUrl -> revisitUrl.getGubun().equals(request.getDEVICE_TYPE()))
                            .anyMatch(revisitUrl -> revisitUrl.getRevisitUrl().equalsIgnoreCase(request.getTHIS_URL()));
                }else{
                    return paramsSubList.contains(tg1Sub);
                }
            }else if("FIXED".equals(campaign.getTgType01())) { // 특정경로 유입
                String referUrl = (StringUtils.hasText(request.getREFER_URL())?request.getREFER_URL():"").toLowerCase();

                // 1차 타겟이 특정경로로 유입한 고객 중 외부링크를 통해 유입한 고객
                if("OUTLINK".equals(tg1Sub) && paramsSubList.contains("OUTLINK")) {
                    if(!StringUtils.hasText(referUrl)) {
                        return false;
                    }

                    return campaign.getCampaignSpecUrlApis().stream()
                            .filter(campaignSpecUrl -> campaignSpecUrl.getGubun().equals(request.getDEVICE_TYPE()))
                            .anyMatch(campaignSpecUrl -> referUrl.startsWith(campaignSpecUrl.getSpecUrl().toLowerCase()));
                }
                // 1차 타겟이 특정경로로 유입한 고객 중 특정 검색어를 통해 유입한 고객
                if("FIXEDWORD".equals(tg1Sub) && paramsSubList.contains("FIXEDWORD")) {
                    String keyWordUrl = request.getKEYWORD();

                    if(!StringUtils.hasText(keyWordUrl) || !StringUtils.hasText(campaign.getTgFixedWord())) {
                        return false;
                    }

                    return Arrays.stream(campaign.getTgFixedWord().split(",")).anyMatch(word ->
                            keyWordUrl.contains(word.toLowerCase()));
                }
            }

            return true;
        };
    }

    /**
     * 2차타겟 조건
     */
    private BiPredicate<CampaignApi, CampaignApiRequest> predicate4SecondTarget() {
        return (campaign, request) -> {
            List<String> paramTargetList = Arrays.asList((StringUtils.hasText(request.getCAM_TARGET())?request.getCAM_TARGET():"ALL").split(","));
            List<String> paramsSubList = Arrays.asList((StringUtils.hasText(request.getTG1_SUB())?request.getTG1_SUB():"TAR1_ALL").split(","));
            String tg2Sub = campaign.getTg2Sub();

            if("N".equals(campaign.getTgType02Yn()) && paramTargetList.contains("ALL")) {
                return true;
            }

            if("FIXED".equals(campaign.getTgType02()) && paramTargetList.contains("FIXED")) { // 2차 타겟이 특정 경로로 유입한 고객
                String referUrl = (StringUtils.hasText(request.getREFER_URL())?request.getREFER_URL():"").toLowerCase();
                // 2차타겟이 외부링크를 통해 유입한 고객
                if ("OUTLINK".equals(tg2Sub) && paramsSubList.contains("OUTLINK")) {
                    if (!StringUtils.hasText(referUrl)) {
                        return false;
                    }

                    return campaign.getCampaignSpecUrlApis().stream()
                            .filter(specUrl -> specUrl.getGubun().equals(request.getDEVICE_TYPE()))
                            .anyMatch(specUrl -> referUrl.startsWith(specUrl.getSpecUrl().toLowerCase()));
                }
                // 2차 타겟이 특정경로로 유입한 고객 중 특정 검색어를 통해 유입한 고객
                if ("FIXEDWORD".equals(tg2Sub) && paramsSubList.contains("FIXEDWORD")) {
                    String keyWordUrl = request.getKEYWORD();

                    if (!StringUtils.hasText(keyWordUrl)) {
                        return false;
                    }
                    return Arrays.stream(campaign.getTgFixedWord().split(",")).anyMatch(word ->
                            keyWordUrl.contains(word.toLowerCase()));
                }
            }else if("RETURN".equals(campaign.getTgType02()) && paramTargetList.contains("RETURN")) { // 2차 타겟이 재방문 고객
                if(!StringUtils.hasText(tg2Sub)) {
                    return false;
                }

                if(tg2Sub.equals("TAR1_FIXED") && paramsSubList.contains("TAR1_FIXED")) { // 특정 페이지에 관심이 많은 고객 (최근 7일 동안 특정 페이지를 3번 이상 방문한 고객)
                    return campaign.getCampaignRevisitUrlApis().stream()
                            .filter(revisitUrl -> revisitUrl.getGubun().equals(request.getDEVICE_TYPE()))
                            .anyMatch(revisitUrl -> revisitUrl.getRevisitUrl().equalsIgnoreCase(request.getTHIS_URL()));
                }else{
                    return paramsSubList.contains(tg2Sub);
                }
            }

            return paramTargetList.contains(campaign.getTgType02());
        };
    }

    private BiPredicate<CampaignApi, CampaignApiRequest> predicate4ExposureTarget() {
        return (campaign, request) -> {
            String caPlace = campaign.getCaPlace();
            Set<CampaignExposureUrlApi> campaignExposureUrlApi = campaign.getCampaignExposureUrlApis();
            // 노출위차가 특정페이지 지정
            if("FIXED_PAGE".equals(caPlace)) {
                return campaignExposureUrlApi.stream()
                        .filter(exposureUrl -> exposureUrl.getGubun().equals(request.getDEVICE_TYPE()))
                        .noneMatch(exposureUrl -> "N".equals(exposureUrl.getIncludeYn())
                                && request.getTHIS_URL().toLowerCase().startsWith(exposureUrl.getExposureUrl().toLowerCase())) // 제외
                        && (campaignExposureUrlApi.stream()
                        .filter(exposureUrl -> exposureUrl.getGubun().equals(request.getDEVICE_TYPE()))
                        .noneMatch(exposureUrl -> "Y".equals(exposureUrl.getIncludeYn()))
                        ||
                        campaignExposureUrlApi.stream()
                                .filter(exposureUrl -> exposureUrl.getGubun().equals(request.getDEVICE_TYPE()))
                                .anyMatch(exposureUrl -> "Y".equals(exposureUrl.getIncludeYn())
                                        && request.getTHIS_URL().toLowerCase().startsWith(exposureUrl.getExposureUrl().toLowerCase()))); // 포함
            }

            if(Arrays.asList("ALL_PAGE", "MOBILE_ONLY", "PC_ONLY").contains(caPlace)){
                return  comparePageType(caPlace, request.getDEVICE_TYPE())
                        && campaignExposureUrlApi.stream()
                        .filter(exposureUrl -> exposureUrl.getGubun().equals(request.getDEVICE_TYPE()))
                        .noneMatch(exposureUrl -> "N".equals(exposureUrl.getIncludeYn())
                                && request.getTHIS_URL().toLowerCase().startsWith(exposureUrl.getExposureUrl().toLowerCase()));
            }

            return false;
        };
    }

    /**
     * 접속기기별 허용
     *
     * @param caPlace
     * @param deviceType
     * @return
     */
    private boolean comparePageType(String caPlace, String deviceType) {
        if(caPlace.equals("ALL_PAGE")) {
            return true;
        }
        if(caPlace.equals("MOBILE_ONLY") && "M".equals(deviceType)) {
            return true;
        }
        if(caPlace.equals("PC_ONLY") && "P".equals(deviceType)) {
            return true;
        }

        return false;
    }
}
