package com.skbroadband.doms.global.component.security;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : Crypto
 * @Program :
 * @Date : 2022-12-16
 * @Comment :
 */
public interface Crypto {
    String encrypt(String text) throws Exception;
    String descrypt(String text) throws Exception;
}
