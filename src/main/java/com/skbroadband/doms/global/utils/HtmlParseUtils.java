package com.skbroadband.doms.global.utils;

import com.skbroadband.doms.web.dto.CampaignMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.util.StringUtils;

@Slf4j
public class HtmlParseUtils {

    public static String getChangePcHtml(CampaignMessageDto campaignMessageDto,
                                         String rendingUrl,
                                         String directUrl,
                                         String leaveUrl,
                                         String address) {
        Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(campaignMessageDto.getMsgPcHtml()));
        Elements imgElems = doc.select("img");
        Elements imgTextElems = doc.select(".campaign-popup");
        Elements rendingUrls = doc.select(".attr-move");
        Elements leaveUrls = doc.select(".attr-send");
        Elements directUrls = doc.select(".attr-join");

        if("4".equals(campaignMessageDto.getMsgType())) {
            if(!StringUtils.isEmpty(campaignMessageDto.getMsgImgNm01())) {
                imgTextElems.get(0).attr("style",
                        "--campaign-profile-image: url(" + address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01() + ")");
            } else {
                String src = imgTextElems.get(0).attr("style");
                if(src.indexOf(address) == -1) {
                    src = address + src;
                }
                imgTextElems.get(0).attr("src", src);
            }
        } else if("8".equals(campaignMessageDto.getMsgType())) {
            if (
                    campaignMessageDto.getMsgImgNm01List() != null &&
                    !campaignMessageDto.getMsgImgNm01List().isEmpty()
            ) {
                for ( int i = 0; i < campaignMessageDto.getMsgImgNm01List().size(); i++) {
                    imgElems.get(i).attr("src", address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01List().get(i));
                }
            }
        } else {
            if(!StringUtils.isEmpty(campaignMessageDto.getMsgImgNm01())) {
                imgElems.get(0).attr("src", address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01());
            } else {
                String src = imgElems.get(0).attr("src");
                if(src.indexOf(address) == -1) {
                    src = address + src;
                }
                imgElems.get(0).attr("src", src);
            }
        }

        if(rendingUrls != null && rendingUrls.size() > 0) {
            rendingUrls.get(0).attr("href", rendingUrl);
        }
        if(leaveUrls != null && leaveUrls.size() > 0) {
            leaveUrls.get(0).attr("href", leaveUrl);
        }
        if(directUrls != null && directUrls.size() > 0) {
            directUrls.get(0).attr("href", directUrl);
        }

        Elements returnStrs = doc.select("div");

        return returnStrs.get(0).toString();
    }

    public static String getChangeMoHtml(CampaignMessageDto campaignMessageDto,
                                         String rendingUrl,
                                         String directUrl,
                                         String leaveUrl,
                                         String callingUrl,
                                         String address) {
        Document doc = Jsoup.parse(HtmlUtils.htmlUnescape(campaignMessageDto.getMsgMoHtml()));
        Elements imgElems = doc.select("img");
        Elements imgTextElems = doc.select(".campaign-popup");
        Elements rendingUrls = doc.select(".attr-move");
        Elements leaveUrls = doc.select(".attr-send");
        Elements directUrls = doc.select(".attr-join");
        Elements callingUrls = doc.select(".attr-call");

        if("4".equals(campaignMessageDto.getMsgType())) {
            if(!StringUtils.isEmpty(campaignMessageDto.getMsgImgNm01())) {
                imgTextElems.get(0).attr("style",
                        "--campaign-profile-image: url(" + address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01() + ")");
            } else {
                String src = imgTextElems.get(0).attr("style");
                if(src.indexOf(address) == -1) {
                    src = address + src;
                }
                imgTextElems.get(0).attr("src", src);
            }
        } else if("8".equals(campaignMessageDto.getMsgType())) {
            if (
                    campaignMessageDto.getMsgImgNm01List() != null &&
                            !campaignMessageDto.getMsgImgNm01List().isEmpty()
            ) {
                for ( int i = 0; i < campaignMessageDto.getMsgImgNm01List().size(); i++) {
                    imgElems.get(i).attr("src", address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01List().get(i));
                }
            }
        } else {
            if(!StringUtils.isEmpty(campaignMessageDto.getMsgImgNm01())) {
                imgElems.get(0).attr("src", address + "/images/campaign/" + campaignMessageDto.getMsgImgNm01());
            } else {
                String src = imgElems.get(0).attr("src");
                if(src.indexOf(address) == -1) {
                    src = address + src;
                }
                imgElems.get(0).attr("src", src);
            }
        }

        /*rendingUrls.get(0).attr("href", campaignMessageDto.getMsgButtonMoUrl());
        if("N".equals(campaignMessageDto.getMsgButtonUrlTargetM())) {
            rendingUrls.get(0).attr("href", rendingUrl);
        }*/
        if(rendingUrls != null && rendingUrls.size() > 0) {
            rendingUrls.get(0).attr("href", rendingUrl);
        }
        if(leaveUrls != null && leaveUrls.size() > 0) {
            leaveUrls.get(0).attr("href", leaveUrl);
        }
        if(directUrls != null && directUrls.size() > 0) {
            directUrls.get(0).attr("href", directUrl);
        }
        if(callingUrls != null && callingUrls.size() > 0) {
            callingUrls.get(0).attr("href", callingUrl);
        }

        Elements returnStrs = doc.select("div");

        return returnStrs.get(0).toString();
    }

}
