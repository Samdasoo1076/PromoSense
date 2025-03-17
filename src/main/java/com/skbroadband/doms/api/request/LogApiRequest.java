package com.skbroadband.doms.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.request
 * @File : LogApiRequest
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Getter
@Builder
public class LogApiRequest implements Serializable {
    @NotBlank(message = "인증키는 필수입니다.")
    private String DOMS_KEY;
    @NotNull(message = "캠페인번호는 필수입니다.")
    private Long CA_NO;
    @NotBlank(message = "접속기기 정보는 필수입니다.")
    private String DEVICE_TYPE;
    @NotBlank(message = "이벤트 구분은 필수입니다.")
    private String EVENT_TYPE;
    @NotBlank(message = "메세지 구분은 필수입니다.")
    private String MSG_TYPE;
    private String CA_REFERER;
    @NotNull(message = "누적 쿠기 방문수는 필수입니다.")
    private Integer COOKIE_CNT;
    @NotNull(message = "캠페인 노출 초는 필수입니다.")
    private Integer EXP_TIME;
    @NotBlank(message = "client 세션 ID는 필수입니다.")
    private String CA_SESSION_ID;
    @NotBlank(message = "client 쿠키 ID는 필수입니다.")
    private String CA_COOKIE_ID;
    @NotBlank(message = "client FULL URL은 필수입니다.")
    private String CA_FULL_URL;
    @NotBlank(message = "노출 페이지 타이틀은 필수입니다.")
    private String CA_TITLE;
    @NotBlank(message = "client IP는 필수입니다.")
    private String CA_IP;
    private String CA_MENU_NO;
    private String CA_C_CODE;
    private Integer MULTI_NO;
}
