package com.skbroadband.doms.global.exception;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : CheckedForbiddenException
 * @Program :
 * @Date : 2023-01-12
 * @Comment :
 */
public class CheckedForbiddenException extends CheckedGlobalException{
    public CheckedForbiddenException() {
        super();
    }

    public CheckedForbiddenException(String msg) {
        super(msg);
    }

    public CheckedForbiddenException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
