package com.skbroadband.doms.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : BadRequestException
 * @Program :
 * @Date : 2022-11-23
 * @Comment :
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends GlobalException{
    public BadRequestException() {
    }

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
