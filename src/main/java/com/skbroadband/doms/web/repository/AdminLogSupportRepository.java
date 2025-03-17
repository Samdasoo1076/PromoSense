package com.skbroadband.doms.web.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.web.dto.AccountLogDto;
import com.skbroadband.doms.web.dto.QAccountLogDto;
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
import java.util.HashMap;
import java.util.List;

import static com.skbroadband.doms.web.entity.QAdminGroup.adminGroup;
import static com.skbroadband.doms.web.entity.QAdminInfo.adminInfo;
import static com.skbroadband.doms.web.entity.QAdminLog.adminLog;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.example.repository
 * @File : ExampleCustomRepositoryImpl
 * @Program :
 * @Date : 2022-11-18
 * @Comment :
 */
@Repository
@RequiredArgsConstructor
public class AdminLogSupportRepository {
    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AccountLogDto> findAll(String keyword, String startDate, String endDate, String taskType, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(adminInfo.admId.contains(keyword));
            builder.or(adminInfo.admName.contains(keyword));
        }
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
//            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").toInstant(ZoneOffset.UTC);
//            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").toInstant(ZoneOffset.UTC);
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();

            builder.and(adminLog.logDate.between(startDateTime, endDateTime));
        }
        if(StringUtils.hasText(taskType)) {
            builder.and(adminLog.taskType.contains(taskType));
        }
        builder.and(adminInfo.useTf.eq("Y"));
        builder.and(adminInfo.delTf.eq("N"));

        JPAQuery<AccountLogDto> content =
                jpaQueryFactory
                .select(new QAccountLogDto(adminLog.id, adminInfo.groupNo.id, adminInfo.admId, adminLog.admNo, adminLog.logIp
                        , adminLog.logDate, adminLog.task, adminLog.taskType, adminGroup.groupName, adminInfo.dept))
                .from(adminLog)
                .leftJoin(adminInfo)
                .on(adminInfo.id.eq(adminLog.admNo))
                .leftJoin(adminGroup)
                        .on(adminGroup.id.eq(adminInfo.groupNo.id))
                .where(builder)
                .orderBy(adminLog.logDate.desc(),adminLog.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(adminLog.count())
                .from(adminLog)
                .leftJoin(adminInfo)
                .on(adminInfo.id.eq(adminLog.admNo))
                .leftJoin(adminGroup)
                .on(adminGroup.id.eq(adminInfo.groupNo.id))
                .where(builder);

        return PageableExecutionUtils.getPage(content.fetchJoin().fetch(),
                pageable,
                countQuery::fetchOne);

    }

    public List<AccountLogDto> findExcelList(String keyword, String startDate, String endDate) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(adminInfo.admId.contains(keyword));
            builder.or(adminInfo.admName.contains(keyword));
        }
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
//            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").toInstant(ZoneOffset.UTC);
//            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").toInstant(ZoneOffset.UTC);
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();

            builder.and(adminLog.logDate.between(startDateTime, endDateTime));
        }

        builder.and(adminInfo.useTf.eq("Y"));
        builder.and(adminInfo.delTf.eq("N"));

        JPAQuery<AccountLogDto> content =
                jpaQueryFactory
                        .select(new QAccountLogDto(adminLog.id, adminInfo.groupNo.id, adminInfo.admId, adminLog.admNo, adminLog.logIp
                                , adminLog.logDate, adminLog.task, adminLog.taskType, adminGroup.groupName, adminInfo.dept))
                        .from(adminLog)
                        .leftJoin(adminInfo)
                        .on(adminInfo.id.eq(adminLog.admNo))
                        .leftJoin(adminGroup)
                        .on(adminGroup.id.eq(adminInfo.groupNo.id))
                        .where(builder)
                        .orderBy(adminLog.logDate.desc(),adminLog.id.desc());


        return content.fetchJoin().fetch();
    }

    public Long totalCount(){

        BooleanBuilder builder = new BooleanBuilder();
        HashMap tabCount = new HashMap();


        builder.and(adminInfo.useTf.eq("Y"));
        builder.and(adminInfo.delTf.eq("N"));

        JPAQuery<Long> adminLogCount =
                jpaQueryFactory.select(ExpressionUtils.as(adminLog.id.count(), "admLogCount"))
                        .from(adminLog)
                        .leftJoin(adminInfo)
                        .on(adminInfo.id.eq(adminLog.admNo))
                        .leftJoin(adminGroup)
                        .on(adminGroup.id.eq(adminInfo.groupNo.id))
                        .where(builder);

        tabCount.put("regIngCnt", adminLogCount.fetchOne());

        return adminLogCount.fetchOne();
    }
}
