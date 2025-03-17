package com.skbroadband.doms.global.exception;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.exception
 * @File : UnauthorizedException
 * @Program :
 * @Date : 2022-12-27
 * @Comment :
 */
public class UnauthorizedException extends GlobalException{
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable cause) {
        super(null, msg, cause);
    }
}
