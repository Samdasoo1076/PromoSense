package com.skbroadband.doms.web.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.web.dto.CampaignDto;
import com.skbroadband.doms.web.dto.QCampaignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skbroadband.doms.web.entity.QCampaign.campaign;
import static com.skbroadband.doms.web.entity.QCampaignBaseType.campaignBaseType;
import static org.springframework.util.ObjectUtils.isEmpty;



/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CampaignListSupportRepository
 * @Program :
 * @Date : 2022-11-18`
 * @Comment :
 */
@Repository
@RequiredArgsConstructor
public class CampaignListSupportRepository {
    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    public Page<CampaignDto> findAll(String keyword, String startDate, String endDate, String caGubun, String caFlag, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        List<OrderSpecifier> ORDERS = new ArrayList<>();

        Instant dateTimeNow = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant();

        if(StringUtils.hasText(keyword)) {
            builder.and(campaign.id.like(keyword));
            builder.or(campaign.caName.contains(keyword));
        }


        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {

            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();

            builder.and(campaign.caStartDate.between(startDateTime, endDateTime).or(campaign.caEndDate.between(startDateTime, endDateTime)));
        }


        if(StringUtils.hasText(caFlag)) {
            if(caFlag.equals("3")){

                builder.and(campaign.caState.ne("1"));

            }else if(caFlag.equals("2")){
                builder.and((campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow))));
                builder.and(campaign.caState.ne("1"));
                builder.and(campaign.useTf.eq("Y"));

            }else{

                builder.and(campaign.caState.eq("1"));

            }
        }

        builder.and(campaign.caGubun.eq(caGubun));

        builder.and(campaign.delTf.eq("N"));

        if (!isEmpty(pageable.getSort())) {

            for (Sort.Order order : pageable.getSort()) {

                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {

                    case "id":
                        OrderSpecifier<?> id = getSortedColumn(direction, campaign.id, "id");
                        ORDERS.add(id);
                        break;
                    case "viewOrder":
                        OrderSpecifier<?> viewOrder = getSortedColumn(direction, campaign.viewOrder, "viewOrder");
                        ORDERS.add(viewOrder.nullsLast());
                        break;
                    case "views":
//                        OrderSpecifier<?> visibleCnt = getSortedColumn(direction, campaign.visibleCnt, "visibleCnt");
                        ORDERS.add(new OrderSpecifier(direction, campaign.visibleCnt.add(campaign.visibleMoCnt)));
                        break;
                    case "caStartDate":
                        OrderSpecifier<?> caStartDate = getSortedColumn(direction, campaign.caStartDate, "caStartDate");
                        ORDERS.add(caStartDate);
                        break;
                    case "caEndDate":
                        OrderSpecifier<?> caEndDate = getSortedColumn(direction, campaign.caEndDate, "caEndDate");
                        ORDERS.add(caEndDate);
                        break;
                    case "upDate":
                        OrderSpecifier<?> upDate = getSortedColumn(direction, campaign.upDate, "upDate");
                        ORDERS.add(upDate);
                        break;

                    default:
                        break;
                }
            }
        }

        JPAQuery<CampaignDto> content =
                jpaQueryFactory
                        .select(
                                new QCampaignDto(
                                campaign.id,
                                campaign.caName,
                                campaign.caState,
                                campaign.tgType01,
                                campaign.tgType02,
                                campaign.caStartTf,
                                campaign.caStartDate,
                                campaign.caStartHour,
                                campaign.caStartMin,
                                campaign.caEndTf,
                                campaign.caEndDate,
                                campaign.caEndHour,
                                campaign.caEndMin,
                                campaign.caWeek,
                                campaign.caTime,
                                campaign.caStaySec,
                                campaign.exposureUrlTf,
                                campaign.exposureLimitCnt,
                                campaign.scrollPercent,
                                campaign.visibleCnt,
                                campaign.clickCnt,
                                campaign.closeCnt,
                                campaign.phoneCnt,
                                campaign.targetVisitCnt,
                                campaign.useTf,
                                campaign.delTf,
                                campaign.delAdm,
                                campaign.delDate,
                                campaign.viewOrder,
                                campaignBaseType.title,
                                ExpressionUtils.as(new CaseBuilder()
                                .when   (
                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                .and(campaign.useTf.eq("N"))
                                ).then("s0003")
                                .when   (
                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                ).then("s0002")
                                .when   (
                                        (campaign.caStartDate.goe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                ).then("s0001")
                                .when   (
                                        (campaign.caEndDate.lt(dateTimeNow))
                                ).then("s0004")
                                .otherwise("s9999"),"caStateNm"),
                                campaign.caPlace,
                                campaign.caViewPoint,
                                campaign.upDate,
                                campaign.regDate,
                                campaign.caMemo,
                                campaign.caPurpose,
                                campaign.viewPointSub,
                                campaign.caAlldayYn,
                                campaign.leaveCnt,
                                campaign.registCnt,
                                campaign.visibleMoCnt,
                                campaign.clickMoCnt,
                                campaign.closeMoCnt,
                                campaign.targetVisitMoCnt,
                                campaign.leaveMoCnt,
                                campaign.registMoCnt,
                                campaign.tg1Sub,
                                campaign.tg2Sub,
                                campaign.tgType02Yn,
                                campaign.tgFixedWord,
                                campaign.caPlaceYn,
                                campaign.caGubun
                        ))
                        .from(campaign)
                        .leftJoin(campaignBaseType)
                        .on(campaign.caMsgType.eq(campaignBaseType.id))
                        .where(builder)
                        .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(campaign.count())
                .from(campaign)
                .where(builder);

    return PageableExecutionUtils.getPage(
            content.fetchJoin().fetch(),
            pageable,
            countQuery::fetchOne);

    }

    public Map tabCount(String keyword, String startDate, String endDate, String caGubun){

        BooleanBuilder builder = new BooleanBuilder();
        HashMap tabCount = new HashMap();

        Instant dateTimeNow = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant();

        if(StringUtils.hasText(keyword)) {
            builder.and(campaign.id.like(keyword));
            builder.or(campaign.caName.contains(keyword));
        }

        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            builder.and(campaign.caStartDate.between(startDateTime, endDateTime).or(campaign.caEndDate.between(startDateTime, endDateTime)));
        }

//        if(StringUtils.hasText(caGubun)) {
            builder.and(campaign.caGubun.eq(caGubun));
//        }

        builder.and(campaign.delTf.eq("N"));

        JPAQuery<Long> regFinish =
                jpaQueryFactory.select(ExpressionUtils.as(campaign.id.count(), "regFinish"))
                               .from(campaign)
                               .where(
                                       campaign.caState.ne("1")
                                       , builder
                               );

        JPAQuery<Long> caActive =
                jpaQueryFactory.select(ExpressionUtils.as(campaign.id.count(), "caActive"))
                               .from(campaign)
                               .where(( campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                        , campaign.caState.eq("2")
                                        , campaign.useTf.eq("Y")
                                        , builder
                               );

        JPAQuery<Long> regIng =
                jpaQueryFactory.select(ExpressionUtils.as(campaign.id.count(), "regFinish"))
                               .from(campaign)
                               .where(
                                       campaign.caState.eq("1")
                                       , builder
                               );

        tabCount.put("regFinishCnt", regFinish.fetchOne());
        tabCount.put("caActiveCnt", caActive.fetchOne());
        tabCount.put("regIngCnt", regIng.fetchOne());

        return tabCount;
    }

    public CampaignDto basePopup(Long caNo) {

        Instant dateTimeNow = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant();

        JPAQuery<CampaignDto> content =
                jpaQueryFactory
                        .select(
                                new QCampaignDto(
                                        campaign.id,
                                        campaign.caName,
                                        campaign.caState,
                                        campaign.tgType01,
                                        campaign.tgType02,
                                        campaign.caStartTf,
                                        campaign.caStartDate,
                                        campaign.caStartHour,
                                        campaign.caStartMin,
                                        campaign.caEndTf,
                                        campaign.caEndDate,
                                        campaign.caEndHour,
                                        campaign.caEndMin,
                                        campaign.caWeek,
                                        campaign.caTime,
                                        campaign.caStaySec,
                                        campaign.exposureUrlTf,
                                        campaign.exposureLimitCnt,
                                        campaign.scrollPercent,
                                        campaign.visibleCnt,
                                        campaign.clickCnt,
                                        campaign.closeCnt,
                                        campaign.phoneCnt,
                                        campaign.targetVisitCnt,
                                        campaign.useTf,
                                        campaign.delTf,
                                        campaign.delAdm,
                                        campaign.delDate,
                                        campaign.viewOrder,
                                        campaignBaseType.title,
                                        ExpressionUtils.as(new CaseBuilder()
                                                .when   (
                                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                                .and(campaign.useTf.eq("N"))
                                                ).then("s0003")
                                                .when   (
                                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                ).then("s0002")
                                                .when   (
                                                        (campaign.caStartDate.goe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                ).then("s0001")
                                                .when   (
                                                        (campaign.caEndDate.lt(dateTimeNow))
                                                ).then("s0004")
                                                .otherwise("s9999"),"caStateNm"),
                                        campaign.caPlace,
                                        campaign.caViewPoint,
                                        campaign.upDate,
                                        campaign.regDate,
                                        campaign.caMemo,
                                        campaign.caPurpose,
                                        campaign.viewPointSub,
                                        campaign.caAlldayYn,
                                        campaign.leaveCnt,
                                        campaign.registCnt,
                                        campaign.visibleMoCnt,
                                        campaign.clickMoCnt,
                                        campaign.closeMoCnt,
                                        campaign.targetVisitMoCnt,
                                        campaign.leaveMoCnt,
                                        campaign.registMoCnt,
                                        campaign.tg1Sub,
                                        campaign.tg2Sub,
                                        campaign.tgType02Yn,
                                        campaign.tgFixedWord,
                                        campaign.caPlaceYn,
                                        campaign.caGubun
                        ))
                        .from(campaign)
                        .leftJoin(campaignBaseType)
                        .on(campaign.caMsgType.eq(campaignBaseType.id))
                        .where(campaign.id.eq(caNo));


        return content.fetchOne();

    }

    public Page<CampaignDto> findViewOrder(String keyword, String startDate, String endDate, String caGubun, String caFlag, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        List<OrderSpecifier> ORDERS = new ArrayList<>();

        Instant dateTimeNow = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant();

        if(StringUtils.hasText(keyword)) {
            builder.and(campaign.id.like(keyword));
            builder.or(campaign.caName.contains(keyword));
        }


        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {

            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();

            builder.and(campaign.caStartDate.between(startDateTime, endDateTime).or(campaign.caEndDate.between(startDateTime, endDateTime)));
        }



        builder.and((campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow))));
        builder.and(campaign.caState.ne("1"));
        builder.and(campaign.useTf.eq("Y"));
        builder.and(campaign.caGubun.eq(caGubun));
        builder.and(campaign.delTf.eq("N"));

        JPAQuery<CampaignDto> content =
                jpaQueryFactory
                        .select(
                                new QCampaignDto(
                                        campaign.id,
                                        campaign.caName,
                                        campaign.caState,
                                        campaign.tgType01,
                                        campaign.tgType02,
                                        campaign.caStartTf,
                                        campaign.caStartDate,
                                        campaign.caStartHour,
                                        campaign.caStartMin,
                                        campaign.caEndTf,
                                        campaign.caEndDate,
                                        campaign.caEndHour,
                                        campaign.caEndMin,
                                        campaign.caWeek,
                                        campaign.caTime,
                                        campaign.caStaySec,
                                        campaign.exposureUrlTf,
                                        campaign.exposureLimitCnt,
                                        campaign.scrollPercent,
                                        campaign.visibleCnt,
                                        campaign.clickCnt,
                                        campaign.closeCnt,
                                        campaign.phoneCnt,
                                        campaign.targetVisitCnt,
                                        campaign.useTf,
                                        campaign.delTf,
                                        campaign.delAdm,
                                        campaign.delDate,
                                        campaign.viewOrder,
                                        campaignBaseType.title,
                                        ExpressionUtils.as(new CaseBuilder()
                                                .when   (
                                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                                .and(campaign.useTf.eq("N"))
                                                ).then("s0003")
                                                .when   (
                                                        (campaign.caStartDate.loe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                ).then("s0002")
                                                .when   (
                                                        (campaign.caStartDate.goe(dateTimeNow).and(campaign.caEndDate.goe(dateTimeNow)))
                                                ).then("s0001")
                                                .when   (
                                                        (campaign.caEndDate.lt(dateTimeNow))
                                                ).then("s0004")
                                                .otherwise("s9999"),"caStateNm"),
                                        campaign.caPlace,
                                        campaign.caViewPoint,
                                        campaign.upDate,
                                        campaign.regDate,
                                        campaign.caMemo,
                                        campaign.caPurpose,
                                        campaign.viewPointSub,
                                        campaign.caAlldayYn,
                                        campaign.leaveCnt,
                                        campaign.registCnt,
                                        campaign.visibleMoCnt,
                                        campaign.clickMoCnt,
                                        campaign.closeMoCnt,
                                        campaign.targetVisitMoCnt,
                                        campaign.leaveMoCnt,
                                        campaign.registMoCnt,
                                        campaign.tg1Sub,
                                        campaign.tg2Sub,
                                        campaign.tgType02Yn,
                                        campaign.tgFixedWord,
                                        campaign.caPlaceYn,
                                        campaign.caGubun
                                ))
                        .from(campaign)
                        .leftJoin(campaignBaseType)
                        .on(campaign.caMsgType.eq(campaignBaseType.id))
                        .where(builder)
                        .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(campaign.count())
                .from(campaign)
                .where(builder);

        return PageableExecutionUtils.getPage(
                content.fetchJoin().fetch(),
                pageable,
                countQuery::fetchOne);

    }

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }

}
