package com.skbroadband.doms.global.exception;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : ForbiddenException
 * @Program :
 * @Date : 2022-12-27
 * @Comment :
 */
public class ForbiddenException extends GlobalException{
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
