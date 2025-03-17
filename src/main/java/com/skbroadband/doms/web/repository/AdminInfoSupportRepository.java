package com.skbroadband.doms.web.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.web.entity.AdminInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.skbroadband.doms.web.entity.QAdminInfo.adminInfo;

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
public class AdminInfoSupportRepository {
    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<AdminInfo> findByIdWithGroup(Long id) {
        Tuple result = jpaQueryFactory.select(adminInfo, adminInfo.groupNo)
                .from(adminInfo)
                .leftJoin(adminInfo.groupNo)
                .on(
                        adminInfo.groupNo.useTf.eq("Y"),
                        adminInfo.groupNo.delTf.eq("N"))
                .where(adminInfo.id.eq(id))
                .fetchOne();

        if(Objects.isNull(result)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(result.get(adminInfo));
        }
    }

    public Page<AdminInfo> findList(String keyword, String startDate, String endDate, String admFlag, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(adminInfo.admId.contains(keyword).or(adminInfo.admName.contains(keyword)));
        }
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            builder.and(adminInfo.regDate.between(startDateTime, endDateTime));
        }
        if(StringUtils.hasText(admFlag)) {
            builder.and(adminInfo.admFlag.eq(admFlag));
        }
        builder.and(adminInfo.useTf.eq("Y"));
        builder.and(adminInfo.delTf.eq("N"));

        JPAQuery<AdminInfo> content = jpaQueryFactory
                .selectFrom(adminInfo)
                .leftJoin(adminInfo.groupNo)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(adminInfo.count())
                .from(adminInfo)
                .leftJoin(adminInfo.groupNo)
                .where(builder);

        return PageableExecutionUtils.getPage(content.fetchJoin().fetch(),
                pageable,
                countQuery::fetchOne);
    }

    public List<AdminInfo> findExcelList(String keyword, String startDate, String endDate) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(adminInfo.admId.contains(keyword));
            builder.or(adminInfo.admName.contains(keyword));
        }
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").toInstant(ZoneOffset.UTC);
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").toInstant(ZoneOffset.UTC);
            builder.and(adminInfo.regDate.between(startDateTime, endDateTime));
        }

        builder.and(adminInfo.useTf.eq("Y"));
        builder.and(adminInfo.delTf.eq("N"));

        JPAQuery<AdminInfo> content = jpaQueryFactory
                .selectFrom(adminInfo)
                .leftJoin(adminInfo.groupNo)
                .where(builder);

        return content.fetchJoin().fetch();
    }

}
