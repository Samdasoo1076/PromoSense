package com.skbroadband.doms.global.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : CheckedGlobalException
 * @Program :
 * @Date : 2023-01-12
 * @Comment :
 */
public class CheckedGlobalException extends Exception{
    private HttpStatus status;
    private Throwable throwable;

    public CheckedGlobalException() {
        super();
    }

    public CheckedGlobalException(String message) {
        super(message);
    }

    public CheckedGlobalException(HttpStatus httpStatus, String message) {
        this(message);
        this.status = httpStatus;
    }

    public CheckedGlobalException(HttpStatus httpStatus, String message, Throwable throwable) {
        this(httpStatus, message);
        this.throwable = throwable;
    }
}
