package com.skbroadband.doms.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.constant
 * @File : ImageDivision
 * @Program :
 * @Date : 2023-02-20
 * @Comment :
 */
public enum ImageUri {
    MENU_ICON("menu_icon", ImagePath.menuIcon),
    CAMPAIGN("campaign", ImagePath.campaign),
    CAPTURE("capture", ImagePath.capture);

    final String division;
    final String path;

    ImageUri(String division, String path) {
        this.division = division;
        this.path = path;
    }

    public String getDivision() {
        return this.division;
    }

    public String getPath() {
        return this.path;
    }

    public static String getPath(String uri) {
        Optional<ImageUri>  opImageUri = Arrays.stream(ImageUri.values())
                .filter(imageUri -> imageUri.division.equals(uri))
                .findFirst();

        if(opImageUri.isPresent()) {
            return opImageUri.get().getPath();
        }

        return "";
    }
}

@Component
class ImagePath {
    public static String menuIcon;
    public static String campaign;
    public static String capture;

    @Value("${application.upload.path.menu-icon}")
    public void setMenuIcon(String path) {
        menuIcon = path;
    }

    @Value("${application.upload.path.campaign}")
    public void setCampaign(String path) {
        campaign = path;
    }

    @Value("${application.upload.path.capture}")
    public void setCapture(String path) {
        capture = path;
    }
}
