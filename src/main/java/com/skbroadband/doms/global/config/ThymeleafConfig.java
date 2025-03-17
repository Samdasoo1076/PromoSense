package com.skbroadband.doms.global.config;

import com.skbroadband.doms.global.component.thyemeleaf.DomsHrefDialect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.config
 * @File : ThyemeleafConfig
 * @Program :
 * @Date : 2022-12-20
 * @Comment :
 */
@Configuration
@RequiredArgsConstructor
public class ThymeleafConfig {
    private final ApplicationContext applicationContext;
    @Bean
    public DomsHrefDialect linkDialect() {
        return new DomsHrefDialect("UTF-8");
    }
}
