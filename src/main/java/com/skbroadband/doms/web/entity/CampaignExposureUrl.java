package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CAMPAIGN_EXPOSURE_URL")
public class CampaignExposureUrl extends CreateTimeBase {
    @Id
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CA_NO", nullable = false)
    private Campaign caNo;

    @Size(max = 2000)
    @Column(name = "EXPOSURE_URL", length = 2000)
    private String exposureUrl;

    @Size(max = 5)
    @Column(name = "GUBUN", length = 5)
    private String gubun;

    @Column(name = "INCLUDE_YN")
    private String includeYn;

    public void setCaNo(Campaign campaign) {
        caNo = campaign;
    }

}