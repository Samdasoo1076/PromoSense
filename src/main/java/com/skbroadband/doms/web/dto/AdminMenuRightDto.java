package com.skbroadband.doms.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : AdminMenuRightDto
 * @Program :
 * @Date : 2023-02-06
 * @Comment :
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminMenuRightDto implements Serializable {

    private AdminGroupDto groupNo;
    private AdminMenuDto menuNo;
    private String readFlag;
    private String regFlag;
    private String updFlag;
    private String delFlag;
    private String fileFlag;
    private Long regAdm;
    private Instant regDate;

    @QueryProjection
    public AdminMenuRightDto(Long menuNo,
                             String menuName,
                             Integer menuDepth,
                             Long MenuParentNo,
                             String menuCode,
                             String readFlag,
                             String regFlag,
                             String updFlag,
                             String delFlag,
                             String fileFlag,
                             Long regAdm,
                             Instant regDate) {
        AdminMenuDto.AdminMenuDtoBuilder builder = AdminMenuDto.builder();

        this.menuNo = builder.build();
        this.menuNo.setId(menuNo);
        this.menuNo.setMenuName(menuName);
        this.menuNo.setMenuDepth(menuDepth);
        this.menuNo.setMenuParentNo(MenuParentNo);
        this.menuNo.setMenuCode(menuCode);
        this.readFlag = readFlag;
        this.regFlag = regFlag;
        this.updFlag = updFlag;
        this.delFlag = delFlag;
        this.fileFlag = fileFlag;
        this.regAdm = regAdm;
        this.regDate = regDate;
    }

}
