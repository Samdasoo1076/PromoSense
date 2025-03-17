package com.skbroadband.doms.api.mapper;

import com.skbroadband.doms.api.entity.CampaignLogApi;
import com.skbroadband.doms.api.request.LogApiRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.api.mapper
 * @File : CampaignLogApiMapper
 * @Program :
 * @Date : 2023-03-03
 * @Comment :
 */
@Mapper(componentModel = "spring", imports = {LocalDateTime.class, DateTimeFormatter.class, WeekFields.class})
public interface CampaignLogApiMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "CA_NO", target = "caNo")
    @Mapping(source = "DEVICE_TYPE", target = "deviceType")
    @Mapping(source = "EVENT_TYPE", target = "eventType")
    @Mapping(source = "MSG_TYPE", target = "msgType")
    @Mapping(source = "CA_REFERER", target = "caReferer")
    @Mapping(source = "COOKIE_CNT", target = "cookieCnt")
    @Mapping(source = "EXP_TIME", target = "expTime")
    @Mapping(source = "CA_SESSION_ID", target = "caSessionId")
    @Mapping(source = "CA_COOKIE_ID", target = "caCookieId")
    @Mapping(source = "CA_FULL_URL", target = "caFullUrl")
    @Mapping(source = "CA_TITLE", target = "caTitle")
    @Mapping(source = "CA_IP", target = "caIp")
    @Mapping(source = "CA_MENU_NO", target = "caMenuNo")
    @Mapping(source = "CA_C_CODE", target = "caCCode")
    CampaignLogApi convert(LogApiRequest request);

    default CampaignLogApi toEntity(LogApiRequest request) {
        LocalDateTime now = LocalDateTime.now();

        CampaignLogApi campaignLogApi = convert(request);
        campaignLogApi.setRegDate(now);
        campaignLogApi.setExpTimeStart(now);
        campaignLogApi.setExpTimeEnd(now);
        campaignLogApi.setCaYmd(now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        campaignLogApi.setCaYear(String.valueOf(now.getYear()));
        campaignLogApi.setCaWeek(String.valueOf(now.get(WeekFields.ISO.weekOfMonth())));
        campaignLogApi.setCaMonth(String.valueOf(now.getMonthValue()));
        campaignLogApi.setCaMin(String.valueOf(now.getMinute()));
        campaignLogApi.setCaHour(String.valueOf(now.getHour()));
        campaignLogApi.setCaDay(String.valueOf(now.getDayOfMonth()));
        campaignLogApi.setMultiNo(request.getMULTI_NO());

        return campaignLogApi;
    }
}
