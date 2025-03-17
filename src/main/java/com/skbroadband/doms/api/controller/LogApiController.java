package com.skbroadband.doms.api.controller;

import com.skbroadband.doms.api.request.LogApiRequest;
import com.skbroadband.doms.api.service.CampaignLogApiService;
import com.skbroadband.doms.global.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.controller
 * @File : LogApiController
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@RestController
@RequiredArgsConstructor
public class LogApiController {
    private final CampaignLogApiService logService;

    @RequestMapping("/api/v1/log")
    public ResponseEntity<?> writeLog(@Valid LogApiRequest log) {
        logService.logging(log);
        return Response.ok();
    }
}
