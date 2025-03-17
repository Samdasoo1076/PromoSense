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
@Table(name = "TBL_CAMPAIGN_EXPOSURE_TIME")
public class CampaignExposureTime extends CreateTimeBase {
    @Id
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_NO", nullable = false)
    private Campaign caNo;

    @Size(max = 5)
    @Column(name = "START_TIME", length = 5)
    private String startTime;

    @Size(max = 5)
    @Column(name = "END_TIME", length = 5)
    private String endTime;

    public void setCaNo(Campaign campaign) {
        caNo = campaign;
    }

}