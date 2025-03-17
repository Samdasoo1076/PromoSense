package com.skbroadband.doms.global.exception;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : CheckedUnauthorizedException
 * @Program :
 * @Date : 2023-01-12
 * @Comment :
 */
public class CheckedUnauthorizedException extends CheckedGlobalException{
    public CheckedUnauthorizedException() {
        super();
    }

    public CheckedUnauthorizedException(String msg) {
        super(msg);
    }

    public CheckedUnauthorizedException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
