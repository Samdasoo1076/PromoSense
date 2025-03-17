package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CAMPAIGN_BASE_TYPE")
public class CampaignBaseType extends TimeBase {
    @Id
    @Column(name = "BASE_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 50)
    @Column(name = "TITLE", length = 50)
    private String title;

    @Size(max = 300)
    @Column(name = "CONTENT", length = 300)
    private String content;

    @Size(max = 50)
    @Column(name = "CSS_NAME", length = 50)
    private String cssName;

    @Column(name = "CONTENT_URI")
    private String contentUri;

    @Column(name = "BASE_ORDER")
    private Long baseOrder;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Column(name = "DEL_ADM")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

}