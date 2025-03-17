package com.skbroadband.doms.api.mapper;

import com.skbroadband.doms.api.entity.CampaignApi;
import com.skbroadband.doms.api.entity.CampaignMessageApi;
import com.skbroadband.doms.api.reponse.CampaignApiResponse;
import com.skbroadband.doms.api.request.CampaignApiRequest;
import org.mapstruct.Mapper;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.mapper
 * @File : CampaignTargetApiMapper
 * @Program :
 * @Date : 2023-03-03
 * @Comment :
 */
@Mapper(componentModel = "spring")
public interface CampaignTargetApiMapper {
    default CampaignApiResponse toResponse(CampaignApi campaignApi, CampaignApiRequest request) {
        CampaignApiResponse.CampaignApiResponseBuilder builder = CampaignApiResponse.builder()
                .caNo(campaignApi.getId())
                .caPlace(campaignApi.getCaPlace())
                .caMsgType(campaignApi.getCaMsgType())
                .caPurpose(campaignApi.getCaPurpose())
                .caViewPoint(campaignApi.getCaViewPoint())
                .viewPointSub(campaignApi.getViewPointSub())
                .exposureLimitCnt(campaignApi.getExposureLimitCnt());

        if("ETC".equals(campaignApi.getCaPurpose()) || "ONLINE".equals(campaignApi.getCaPurpose())) {
            campaignApi.getCampaignTargetUrlApis().stream()
                    .filter(targetUrl -> request.getDEVICE_TYPE().equals(targetUrl.getGubun()))
                    .findFirst()
                    .ifPresent(targetUrl ->  builder.caPurposeUrl(targetUrl.getTargetUrl()));
        }

        if(campaignApi.getCampaignMessageApis().size() > 0) {
            CampaignMessageApi campaignMessageApi =  campaignApi.getCampaignMessageApis().get(0);
            builder.msgName(campaignMessageApi.getMsgName())
                    .msgImgNm01(campaignMessageApi.getMsgImgNm01())
                    .msgImgNm02(campaignMessageApi.getMsgImgNm02())
                    .msgImgNm03(campaignMessageApi.getMsgImgNm03())
                    .msgImgAlter01(campaignMessageApi.getMsgImgAlter01())
                    .msgImgAlter02(campaignMessageApi.getMsgImgAlter02())
                    .msgImgAlter03(campaignMessageApi.getMsgImgAlter03())
                    .msgTitleTf(campaignMessageApi.getMsgTitleTf())
                    .msgTitle(campaignMessageApi.getMsgTitle())
                    .msgContentsTf(campaignMessageApi.getMsgContentsTf())
                    .msgContents(campaignMessageApi.getMsgContents())
                    .msgButtonTf(campaignMessageApi.getMsgButtonTf())
                    .msgButtonPcUrl(campaignMessageApi.getMsgButtonPcUrl())
                    .msgButtonMoUrl(campaignMessageApi.getMsgButtonMoUrl())
                    .msgButtonColor(campaignMessageApi.getMsgButtonColor())
                    .msgButtonUrlTargetP(campaignMessageApi.getMsgButtonUrlTargetP())
                    .phoneButtonTf(campaignMessageApi.getPhoneButtonTf())
                    .msgTitleColor(campaignMessageApi.getMsgTitleColor())
                    .msgImgTf(campaignMessageApi.getMsgImgTf())
                    .msgContentsColor(campaignMessageApi.getMsgContentsColor())
                    .msgButton(campaignMessageApi.getMsgButton())
                    .msgButtonUrlTargetM(campaignMessageApi.getMsgButtonUrlTargetM())
                    .msgButtonBgColor(campaignMessageApi.getMsgButtonBgColor())
                    .msgLeaveLineColor(campaignMessageApi.getMsgLeaveLineColor())
                    .msgLeaveColor(campaignMessageApi.getMsgLeaveColor())
                    .msgLeaveTf(campaignMessageApi.getMsgLeaveTf())
                    .msgRegistTf(campaignMessageApi.getMsgRegistTf())
                    .msgRegistLineColor(campaignMessageApi.getMsgRegistLineColor())
                    .msgRegistColor(campaignMessageApi.getMsgRegistColor())
                    .msgMobileCallingTf(campaignMessageApi.getMsgMobileCallingTf())
                    .msgMobileCallingLineColor(campaignMessageApi.getMsgMobileCallingLineColor())
                    .msgMobileCallingColor(campaignMessageApi.getMsgMobileCallingColor())
                    .msgBackDimTf(campaignMessageApi.getMsgBackDimTf())
                    .msgReviewTf(campaignMessageApi.getMsgReviewTf())
                    .msgReviewType(campaignMessageApi.getMsgReviewType())
                    .msgReviewDays(campaignMessageApi.getMsgReviewDays())
                    .msgPcHtml(campaignMessageApi.getMsgPcHtml())
                    .msgMoHtml(campaignMessageApi.getMsgMoHtml())
                    .msgBanBgColor(campaignMessageApi.getMsgBanBgColor())
                    .msgBanTextColor(campaignMessageApi.getMsgBanTextColor())
                    .msgActionEffectType(campaignMessageApi.getMsgActionEffectType());
        }

        return builder.build();
    }
}
