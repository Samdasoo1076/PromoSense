package com.skbroadband.doms.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.dto
 * @File : ErrorResponse
 * @Program :
 * @Date : 2022-12-26
 * @Comment :
 */
@Getter
@Builder
public class ErrorResponse {
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp = LocalDateTime.now();
    private int code;
    private String status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String stacktrace;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> fieldErrors;
    private String path;

    @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static class FieldError {
        private final String field;
        private final String message;
    }
}
