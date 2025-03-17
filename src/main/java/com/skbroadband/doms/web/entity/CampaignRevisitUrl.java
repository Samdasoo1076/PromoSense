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
@Table(name = "TBL_CAMPAIGN_REVISIT_URL")
public class CampaignRevisitUrl extends CreateTimeBase {
    @Id
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_NO", nullable = false)
    private Campaign caNo;

    @Size(max = 2000)
    @Column(name = "REVISIT_URL", length = 2000)
    private String revisitUrl;

    @Size(max = 5)
    @Column(name = "GUBUN", length = 5)
    private String gubun;

    public void setCaNo(Campaign campaign) {
        caNo = campaign;
    }

}