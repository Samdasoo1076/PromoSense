package com.skbroadband.doms.global.constant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.constant
 * @File : CustomerType
 * @Program :
 * @Date : 2023-03-08
 * @Comment :
 */
public enum CustomerType {
    BDIRECTSHOP("BD"),
    BTVCABLE("TB"),
    BWORLD("BW"),
    TEST("TEST");

    final String code;

    CustomerType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
