package com.skbroadband.doms.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TBL_CAMPAIGN_SPEC_URL")
public class CampaignSpecUrlApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CA_NO", nullable = false)
    private CampaignApi caNo;

    @Column(name = "SPEC_URL", length = 2000)
    private String specUrl;

    @Column(name = "REG_ADM", columnDefinition = "INT UNSIGNED")
    private Long regAdm;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Size(max = 5)
    @Column(name = "GUBUN", length = 5)
    private String gubun;

}