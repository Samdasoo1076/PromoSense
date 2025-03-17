package com.skbroadband.doms.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.config
 * @File : QueryDslConfig
 * @Program :
 * @Date : 2022-11-18
 * @Comment :
 */
@Configuration
public class QueryDslConfig {
    @PersistenceContext(unitName = "web")
    private EntityManager webEntityManager;

    @PersistenceContext(unitName = "api")
    private EntityManager apiwebEntityManager;

    @Bean(name = "webJpaQueryFactory")
    public JPAQueryFactory webJpaQueryFactory() {
        return new JPAQueryFactory(webEntityManager);
    }

    @Bean(name = "apiJpaQueryFactory")
    public JPAQueryFactory apiJpaQueryFactory() {
        return new JPAQueryFactory(apiwebEntityManager);
    }
}
