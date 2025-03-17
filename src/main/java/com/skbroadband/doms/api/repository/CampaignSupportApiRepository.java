package com.skbroadband.doms.api.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.api.entity.CampaignApi;
import com.skbroadband.doms.api.reponse.PreviewApiResponse;
import com.skbroadband.doms.api.reponse.QPreviewApiResponse;
import com.skbroadband.doms.api.request.CampaignApiRequest;
import com.skbroadband.doms.api.request.PreviewApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.skbroadband.doms.api.entity.QCampaignApi.campaignApi;
import static com.skbroadband.doms.api.entity.QCampaignExposureTimeApi.campaignExposureTimeApi;
import static com.skbroadband.doms.api.entity.QCampaignMessageApi.campaignMessageApi;
import static com.skbroadband.doms.api.entity.QCampaignTargetUrlApi.campaignTargetUrlApi;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.repository
 * @File : CampaignSupportApiRepository
 * @Program :
 * @Date : 2023-03-06
 * @Comment :
 */
@Repository
@RequiredArgsConstructor
public class CampaignSupportApiRepository {
    @Qualifier(value = "apiJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 캠페인 조회
     *
     * @param campaignRequest
     * @return
     */
    public List<CampaignApi> findCampaign(CampaignApiRequest campaignRequest) {
        JPAQuery<CampaignApi> query = jpaQueryFactory.select(campaignApi)
                .from(campaignApi)
                .innerJoin(campaignApi.campaignExposureTimeApis, campaignExposureTimeApi)
                .on(campaignExposureTimeApi.startTime.eq(campaignRequest.getHour()))
                .innerJoin(campaignApi.campaignMessageApis, campaignMessageApi)
                .fetchJoin()
                .leftJoin(campaignApi.campaignExposureUrlApis)
                .fetchJoin()
                .leftJoin(campaignApi.campaignRevisitUrlApis)
                .fetchJoin()
                .leftJoin(campaignApi.campaignSpecUrlApis)
                .fetchJoin()
                .leftJoin(campaignApi.campaignTargetUrlApis)
                .fetchJoin()
                .where( exceptCampaign(campaignRequest.getEXCEPT_CA_NO()), //캠페인번호로 제외
                        campaignMessageApi.msgButtonPcUrl.coalesce("").notEqualsIgnoreCase(campaignRequest.getTHIS_URL()),
                        campaignMessageApi.msgButtonMoUrl.coalesce("").notEqualsIgnoreCase(campaignRequest.getTHIS_URL()),
                        campaignApi.caWeek.contains(campaignRequest.getDayOfWeek()),
                        campaignApi.useTf.eq("Y"),
                        campaignApi.delTf.eq("N"),
                        campaignApi.caState.eq("2"),
                        campaignApi.tgType01.in(campaignRequest.getCAM_TARGET().split(",")), // 캠페인 1차 대상
                        campaignApi.caGubun.eq(campaignRequest.getCamGubun()),
                        campaignMessageApi.delTf.eq("N"),
                        formattedNowDate.between(campaignApi.caStartDate, campaignApi.caEndDate)
                )
                .distinct()
                .orderBy(campaignApi.viewOrder.asc());

        return query.fetch().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 캠페인번호 제외 처리
     *
     * @param campaignNo
     * @return
     */
    private BooleanExpression exceptCampaign(String campaignNo) {
        if(!StringUtils.hasText(campaignNo)) {
            return null;
        }

        return campaignApi.id.notIn(Arrays.stream(campaignNo.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet()));
    }

    private final DateTemplate<LocalDateTime> formattedNowDate = Expressions.dateTemplate(LocalDateTime.class,
            "DATE_FORMAT(now(), {0})",
            Expressions.constant("%Y-%m-%d %H:%m:%s"));

    /**
     * 미리보기
     * 
     * @param previewRequest
     * @return
     */
    public Optional<PreviewApiResponse> findPreview(PreviewApiRequest previewRequest) {
        JPAQuery<PreviewApiResponse>  query =  jpaQueryFactory.select(new QPreviewApiResponse(
                        campaignApi.id, campaignApi.caPurpose, campaignTargetUrlApi.targetUrl, campaignApi.caPlace, campaignApi.caMsgType, campaignApi.caViewPoint,
                        campaignApi.viewPointSub, campaignApi.exposureLimitCnt, campaignMessageApi.msgName, campaignMessageApi.msgImgNm01,
                        campaignMessageApi.msgImgNm02, campaignMessageApi.msgImgNm03, campaignMessageApi.msgImgAlter01,
                        campaignMessageApi.msgImgAlter02, campaignMessageApi.msgImgAlter03, campaignMessageApi.msgTitleTf,
                        campaignMessageApi.msgTitle, campaignMessageApi.msgContentsTf, campaignMessageApi.msgContents,
                        campaignMessageApi.msgButtonTf, campaignMessageApi.msgButtonPcUrl, campaignMessageApi.msgButtonMoUrl,
                        campaignMessageApi.msgButtonColor, campaignMessageApi.msgButtonUrlTargetP, campaignMessageApi.phoneButtonTf,
                        campaignMessageApi.msgTitleColor, campaignMessageApi.msgImgTf, campaignMessageApi.msgContentsColor,
                        campaignMessageApi.msgButton, campaignMessageApi.msgButtonUrlTargetM, campaignMessageApi.msgButtonBgColor,
                        campaignMessageApi.msgLeaveLineColor, campaignMessageApi.msgLeaveColor, campaignMessageApi.msgLeaveTf,
                        campaignMessageApi.msgRegistTf, campaignMessageApi.msgRegistLineColor, campaignMessageApi.msgRegistColor,
                        campaignMessageApi.msgMobileCallingTf, campaignMessageApi.msgMobileCallingLineColor, campaignMessageApi.msgMobileCallingColor,
                        campaignMessageApi.msgBackDimTf, campaignMessageApi.msgReviewTf, campaignMessageApi.msgReviewType,
                        campaignMessageApi.msgReviewDays, campaignMessageApi.msgPcHtml, campaignMessageApi.msgMoHtml,
                        campaignMessageApi.msgBanBgColor, campaignMessageApi.msgBanTextColor, campaignMessageApi.msgActionEffectType))
                .from(campaignApi)
                .leftJoin(campaignMessageApi)
                .on(campaignApi.eq(campaignMessageApi.caNo)
                        .and(campaignMessageApi.delTf.eq("N")))
                .leftJoin(campaignTargetUrlApi)
                .on(campaignApi.id.eq(campaignTargetUrlApi.caNo.id).and(campaignTargetUrlApi.gubun.eq("P")))
                .where(campaignApi.id.eq(previewRequest.getCA_NO()),
                        campaignApi.caGubun.eq(previewRequest.getCamGubun())
                ).distinct();

        return Optional.ofNullable(query.fetchFirst());
    }
}
