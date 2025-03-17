package com.skbroadband.doms.global.component.log;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.config
 * @File : CacheEventLogger
 * @Program :
 * @Date : 2022-11-17
 * @Comment :
 */
@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object>{
    @Override
    public void onEvent(CacheEvent event) {
        log.debug("cache event logger message. getKey: {} / getOldValue: {} / getNewValue:{}",
                event.getKey(),
                event.getOldValue(),
                event.getNewValue());
    }
}
