package com.skbroadband.doms.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : NotFoundException
 * @Program :
 * @Date : 2022-11-23
 * @Comment :
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends GlobalException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
