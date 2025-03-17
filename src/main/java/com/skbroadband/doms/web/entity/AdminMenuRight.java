package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "TBL_ADMIN_MENU_RIGHT")
public class AdminMenuRight extends CreateTimeBase {
    @EmbeddedId
    private AdminMenuRightId id;

    @MapsId("groupNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_NO", nullable = false)
    private AdminGroup groupNo;

    @MapsId("menuNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MENU_NO", nullable = false)
    private AdminMenu menuNo;

    @Column(name = "READ_FLAG")
    private String readFlag;

    @Column(name = "REG_FLAG")
    private String regFlag;

    @Column(name = "UPD_FLAG")
    private String updFlag;

    @Column(name = "DEL_FLAG")
    private String delFlag;

    @Column(name = "FILE_FLAG")
    private String fileFlag;
}