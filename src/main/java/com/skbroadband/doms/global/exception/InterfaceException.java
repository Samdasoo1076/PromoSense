package com.skbroadband.doms.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : ServerException
 * @Program :
 * @Date : 2022-11-23
 * @Comment :
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InterfaceException extends GlobalException{
    public InterfaceException() {
    }

    public InterfaceException(String msg) {
        super(msg);
    }

    public InterfaceException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
