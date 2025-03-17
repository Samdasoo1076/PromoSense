package com.skbroadband.doms.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : AdminMenuDto
 * @Program :
 * @Date : 2023-01-30
 * @Comment :
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminMenuDto implements Serializable {
    private Long id;
    private String menuCode;
    private String menuName;
    private String menuUrl;
    private Integer menuDepth;
    private String menuFlag;
    private String menuImg;
    private String menuImgOver;
    private Integer menuOrder;
    private Long menuParentNo;
    private String useTf;
    private String delTf;
    private String delAdm;
    private Instant delDate;

    private Long menuGroupNo;
    private boolean selected = false;
    @QueryProjection
    public AdminMenuDto(Long id, String menuCode, String menuName, String menuUrl, Integer menuDepth, String menuImg, Integer menuOrder, Long menuParentNo, Long menuGroupNo) {
        this.id = id;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuDepth = menuDepth;
        this.menuImg = menuImg;
        this.menuOrder = menuOrder;
        this.menuParentNo = menuParentNo;
        this.menuGroupNo = menuGroupNo;
    }
    private List<AdminMenuDto> subMenu =  new ArrayList<AdminMenuDto>();
}
