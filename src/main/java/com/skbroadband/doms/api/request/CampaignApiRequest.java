package com.skbroadband.doms.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.request
 * @File : CampaignApiRequest
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Getter
@Setter
@Builder
public class CampaignApiRequest implements Serializable {
    @NotBlank(message = "인증키는 필수입니다.")
    private String DOMS_KEY;
    @NotBlank(message = "타겟정보는 필수입니다.")
    private String CAM_TARGET;
    @NotBlank(message = "현재 페이지 URL은 필수입니다.")
    private String THIS_URL;
    @NotBlank(message = "접속기기 정보는 필수입니다.")
    private String DEVICE_TYPE;
    private String TG1_SUB;
    private String REFER_URL;
    private String KEYWORD;
    private String EXCEPT_CA_NO;
    private LocalDateTime now;
    private String dayOfWeek;
    private String hour;
    private String camGubun;
}
