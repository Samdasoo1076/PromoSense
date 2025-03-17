package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_ACCESS_TOKEN_WHITE_LIST")
public class AccessTokenWhiteList {
    @Id
    @Column(name = "ADM_NO", columnDefinition = "INT UNSIGNED not null")
    private Long admNo;

    @Size(max = 300)
    @NotNull
    @Column(name = "ACCESS_TOKEN", nullable = false, length = 300)
    private String accessToken;

    @Column(name = "ISSUED_DATE", nullable = false)
    private Instant issuedDate;

    @Column(name = "EXPIRES_IN", nullable = false)
    private Instant expiresIn;

}