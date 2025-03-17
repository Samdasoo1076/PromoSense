package com.skbroadband.doms.web.entity;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.config
 * @File : TimeBase
 * @Program :
 * @Date : 2023-01-05
 * @Comment :
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeBase extends CreateTimeBase {
    @LastModifiedBy
    @Column(name = "UP_ADM")
    private Long upAdm;

    @LastModifiedDate
    @Column(name = "UP_DATE")
    private Instant upDate;
}
