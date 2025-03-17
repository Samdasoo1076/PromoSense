package com.skbroadband.doms.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.request
 * @File : PreviewApiRequest
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
@Getter
@Setter
@Builder
public class PreviewApiRequest implements Serializable {
    @NotBlank(message = "인증키는 필수입니다.")
    private String DOMS_KEY;
    @NotNull(message = "일련번호는 필수입니다.")
    private Long CA_NO;
    private String camGubun;
}
