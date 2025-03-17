package com.skbroadband.doms.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband
 * @File : BdirectshopException
 * @Program :
 * @Date : 2022-11-23
 * @Comment :
 */

@Getter
public class GlobalException extends RuntimeException{
    private HttpStatus status;
    private Throwable throwable;

    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(HttpStatus httpStatus, String message) {
        this(message);
        this.status = httpStatus;
    }

    public GlobalException(HttpStatus httpStatus, String message, Throwable throwable) {
        this(httpStatus, message);
        this.throwable = throwable;
    }
}
