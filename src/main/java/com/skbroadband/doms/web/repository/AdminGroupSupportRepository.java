package com.skbroadband.doms.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminInfoSupportRepository
 * @Program :
 * @Date : 2023-01-13
 * @Comment :
 */
@Repository
@RequiredArgsConstructor
public class AdminGroupSupportRepository {
    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    /*public Page<AdminGroup> findList(Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(adminGroup.useTf.eq("Y"));
        builder.and(adminGroup.delTf.eq("N"));

        JPAQuery<AdminGroup> content = jpaQueryFactory
                .selectFrom(adminGroup)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(adminGroup.count())
                .from(adminGroup)
                .where(builder);

        return PageableExecutionUtils.getPage(content.fetch(),
                pageable,
                countQuery::fetchOne);
    }*/

}
