package com.skbroadband.doms.web.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_ADMIN_MENU")
public class AdminMenu extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 50)
    @Column(name = "MENU_CODE", length = 10)
    private String menuCode;

    @Size(max = 50)
    @Column(name = "MENU_NAME", length = 50)
    private String menuName;

    @Size(max = 150)
    @Column(name = "MENU_URL", length = 150)
    private String menuUrl;

    @Column(name = "MENU_DEPTH")
    private Integer menuDepth;

    @Column(name = "MENU_FLAG")
    private String menuFlag;

    @Size(max = 50)
    @Column(name = "MENU_IMG", length = 100)
    private String menuImg;

    @Size(max = 50)
    @Column(name = "MENU_IMG_OVER", length = 50)
    private String menuImgOver;

    @Column(name = "MENU_ORDER")
    private Integer menuOrder;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Size(max = 30)
    @Column(name = "DEL_ADM", length = 30)
    private String delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MENU_PARENT_NO")
//    private AdminMenu parentMenu;

    @Column(name = "MENU_PARENT_NO")
    private Long menuParentNo;

    @OneToMany(mappedBy = "menuParentNo", fetch = FetchType.LAZY)
    @BatchSize(size = 500)
    private List<AdminMenu> subMenu  = new ArrayList<AdminMenu>();

    public void delete() {
        this.useTf = "N";
        this.delTf = "Y";
    }
}