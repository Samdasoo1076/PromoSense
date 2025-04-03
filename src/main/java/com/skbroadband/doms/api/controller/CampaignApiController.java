package com.skbroadband.doms.api.controller;

import com.skbroadband.doms.api.reponse.CampaignApiResponse;
import com.skbroadband.doms.api.reponse.PreviewApiResponse;
import com.skbroadband.doms.api.request.CampaignApiRequest;
import com.skbroadband.doms.api.request.PreviewApiRequest;
import com.skbroadband.doms.api.service.CampaignApiService;
import com.skbroadband.doms.global.annotation.Customer;
import com.skbroadband.doms.global.constant.CustomerType;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.controller
 * @File : CampaignApiController
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CampaignApiController {
    private final CampaignApiService campaignService;

    /**
     * 대상 조회
     *
     * @param campaignRequest
     */
    @RequestMapping("/campaign")
    public ResponseEntity<?> campaign(@Valid CampaignApiRequest campaignRequest, @Customer CustomerType customer) {
        LocalDateTime now = LocalDateTime.now();
        int week = (now.getDayOfWeek().getValue());
        if (customer == null) {
            // 기본값 할당 혹은 적절한 예외 처리를 합니다.
            customer = CustomerType.BTVCABLE;
        }
        campaignRequest.setCamGubun(customer.getCode());
        campaignRequest.setNow(now);
        campaignRequest.setDayOfWeek(String.valueOf(week));
        campaignRequest.setHour(String.format("%02d", now.getHour()));

        String referUrl = campaignRequest.getREFER_URL();
        if(StringUtils.hasText(referUrl)) {
            if(referUrl.lastIndexOf("/")==referUrl.length()-1) {
                campaignRequest.setREFER_URL(referUrl.substring(0, referUrl.length()-1));
            }
        }

        List<CampaignApiResponse> campaignApiResponses = campaignService.getCampaign(campaignRequest);

        Map<String, Object> resMap = new HashMap<>();

        if (campaignApiResponses != null && !campaignApiResponses.isEmpty()) {
            List<CampaignMessageMulti> campaignMessageMultiList =
                    campaignService.getCampaignMessageMulti(campaignApiResponses.get(0).getCaNo());

            if (!campaignMessageMultiList.isEmpty()) {
                resMap.put("campaign", campaignApiResponses);
                resMap.put("multiList", campaignMessageMultiList);

                log.debug("============== campaignMessageMultiList ============== {}", campaignMessageMultiList);

                return Response.of(resMap);
            }
        }

        return Response.of(campaignService.getCampaign(campaignRequest));
    }

    /**
     * 캠페인 조회
     *
     * @param previewRequest
     */
    @RequestMapping("/preview")
    public ResponseEntity<?> preview(@Valid PreviewApiRequest previewRequest, @Customer CustomerType customer) {
        previewRequest.setCamGubun(customer.getCode());

        Map<String, Object> resMap = new HashMap<>();

        PreviewApiResponse responseEntity = campaignService.getPreview(previewRequest);

        List<CampaignMessageMulti> campaignMessageMultiList =
                campaignService.getCampaignMessageMulti(previewRequest.getCA_NO());

        if (campaignMessageMultiList != null && !campaignMessageMultiList.isEmpty()) {
            resMap.put("campaign", responseEntity);
            resMap.put("multiList", campaignMessageMultiList);

            log.debug("============== campaignMessageMultiList ============== {}", campaignMessageMultiList);

            return Response.of(resMap);
        }

        return Response.of(responseEntity);
    }
}
