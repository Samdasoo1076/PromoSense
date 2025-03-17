package com.skbroadband.doms.web.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.api.repository.CampaignLogApiRepository;
import com.skbroadband.doms.web.dto.CampaignAnalysisLogDto;
import com.skbroadband.doms.web.dto.CampaignDto;
import com.skbroadband.doms.web.dto.QCampaignAnalysisLogDto;
import com.skbroadband.doms.web.dto.QCampaignDto;
import com.skbroadband.doms.web.entity.QCampaignLog;
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
import java.util.Date;
import java.util.List;

import static com.skbroadband.doms.web.entity.QCampaign.campaign;
import static com.skbroadband.doms.web.entity.QCampaignBaseType.campaignBaseType;
import static com.skbroadband.doms.web.entity.QCampaignLog.campaignLog;
import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
@RequiredArgsConstructor
public class CampaignAnalysisSupportRepository {

    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;
    private final CampaignLogApiRepository campaignLogApiRepository;
    private final CampaignBaseTypeRepository campaignBaseTypeRepository;

    public Page<CampaignDto> findByAll(String keyword, String startDate, String endDate, String gubun, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        List<OrderSpecifier> orders = new ArrayList<>();

        if(StringUtils.hasText(keyword)) {
            builder.and(campaign.id.like(keyword).or(campaign.caName.contains(keyword)));
        }
        if(StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            builder.and(campaign.caStartDate.between(startDateTime, endDateTime).or(campaign.caEndDate.between(startDateTime, endDateTime)));
        }
        builder.and(campaign.delTf.eq("N"));
        builder.and(campaign.caState.eq("2"));
        builder.and(campaign.caGubun.eq(gubun));

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "ID":
                        OrderSpecifier<?> id = getSortedColumn(direction, campaign.id, "id");
                        orders.add(id);
                        break;
                    case "VIEWS":
                        orders.add(new OrderSpecifier(direction, campaign.visibleCnt.add(campaign.visibleMoCnt)));
                        break;
                    case "CLICK_PERCENT":
                        orders.add(new OrderSpecifier(direction, (((campaign.clickCnt.add(campaign.phoneCnt).add(campaign.leaveCnt).add(campaign.registCnt)
                                .add(campaign.clickMoCnt).add(campaign.clickMoCnt).add(campaign.leaveMoCnt).add(campaign.registMoCnt))
                                .divide((campaign.visibleCnt.add(campaign.visibleMoCnt)).multiply(1.0)))
                                .multiply(100))));
                        break;
                    case "VISIBLE_PERCENT":
                        orders.add(new OrderSpecifier(direction, ((campaign.targetVisitCnt.add(campaign.targetVisitMoCnt))
                                .divide((campaign.visibleCnt.add(campaign.visibleMoCnt)).multiply(1.0)))
                                .multiply(100)));
                        break;
                    case "LEAVE_PERCENT":
                        orders.add(new OrderSpecifier(direction, (((campaign.visibleCnt.add(campaign.visibleMoCnt))
                                .subtract((campaign.closeCnt.add(campaign.clickCnt).add(campaign.phoneCnt).add(campaign.leaveCnt)
                                        .add(campaign.closeMoCnt).add(campaign.clickMoCnt).add(campaign.leaveMoCnt)
                                        .add(campaign.registMoCnt).add(campaign.registCnt))))
                                .divide((campaign.visibleCnt.add(campaign.visibleMoCnt)).multiply(1.0)))
                                .multiply(100)));
                        break;
                    case "START_DATE":
                        OrderSpecifier<?> caStartDate = getSortedColumn(direction, campaign.caStartDate, "caStartDate");
                        orders.add(caStartDate);
                        break;
                    case "END_DATE":
                        OrderSpecifier<?> caEndDate = getSortedColumn(direction, campaign.caEndDate, "caEndDate");
                        orders.add(caEndDate);
                        break;
                    case "LAST_UP_DATE":
                        OrderSpecifier<?> upDate = getSortedColumn(direction, campaign.upDate, "upDate");
                        orders.add(upDate);
                        break;

                    default:
                        break;
                }
            }
        }

        Instant now = Instant.now();
        JPAQuery<CampaignDto> content = jpaQueryFactory
                .select(new QCampaignDto(
                        campaign.id,
                        campaign.caName,
                        campaign.visibleCnt,
                        campaign.clickCnt,
                        campaign.closeCnt,
                        campaign.phoneCnt,
                        campaign.targetVisitCnt,
                        campaign.leaveCnt,
                        campaign.registCnt,
                        campaign.visibleMoCnt,
                        campaign.clickMoCnt,
                        campaign.closeMoCnt,
                        campaign.targetVisitMoCnt,
                        campaign.leaveMoCnt,
                        campaign.registMoCnt,
                        campaign.caMsgType,
                        campaignBaseType.title,
                        campaign.caAlldayYn,
                        campaign.caStartDate,
                        campaign.caStartHour,
                        campaign.caStartMin,
                        campaign.caEndDate,
                        campaign.caEndHour,
                        campaign.caEndMin,
                        ExpressionUtils.as(new CaseBuilder()
                            .when   (
                                    (campaign.caStartDate.loe(now).and(campaign.caEndDate.goe(now)))
                                            .and(campaign.useTf.eq("N"))
                            ).then("s0003")
                            .when   (
                                    (campaign.caStartDate.loe(now).and(campaign.caEndDate.goe(now)))
                            ).then("s0002")
                            .when   (
                                    (campaign.caStartDate.goe(now).and(campaign.caEndDate.goe(now)))
                            ).then("s0001")
                            .when   (
                                    (campaign.caEndDate.lt(now))
                            ).then("s0004")
                            .otherwise("s9999"),"caStateNm")
                ))
                .from(campaign)
                .join(campaignBaseType)
                .on(campaign.caMsgType.eq(campaignBaseType.id))
                .where(
                        builder,
                        JPAExpressions.selectFrom(campaignLog).where(campaign.eq(campaignLog.caNo)).exists()
                )
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
//                .orderBy(Sort.Order.desc(campaign.visibleTotal))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(campaign.count())
                .from(campaign)
                .where(builder);

        return PageableExecutionUtils.getPage(content.fetch(),
                pageable,
                countQuery::fetchOne);
    }

    private OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }

    public List<CampaignAnalysisLogDto> getDetailAnalysisData(Long id, String startDate, String endDate){
        BooleanBuilder builder = new BooleanBuilder();
        if(null != startDate && !"".equalsIgnoreCase(startDate)){
            Instant startDateTime = LocalDateTime.parse(startDate + "T00:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            builder.and(campaignLog.regDate.after(startDateTime));
        }
        if(null != endDate && !"".equalsIgnoreCase(endDate)){
            Instant endDateTime = LocalDateTime.parse(endDate + "T23:59:59").atZone(ZoneId.of("Asia/Seoul")).toInstant();
            builder.and(campaignLog.regDate.before(endDateTime));
        }

        List<CampaignAnalysisLogDto> content = jpaQueryFactory
                .select(
                        new QCampaignAnalysisLogDto(
                                campaignLog.id,
                                campaignLog.deviceType,
                                campaignLog.eventType,
                                campaignLog.expTime,
                                campaignLog.regDate,
                                campaignLog.multiNo
                        )
                )
                .from(campaignLog)
                .innerJoin(campaign)
                .on(campaign.id.eq(id), campaign.eq(campaignLog.caNo)).fetchJoin()
                .where(builder).fetch();


        return content;
    }
}
