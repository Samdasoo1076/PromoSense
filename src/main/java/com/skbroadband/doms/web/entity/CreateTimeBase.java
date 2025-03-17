package com.skbroadband.doms.web.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.entity
 * @File : RegTimeBase
 * @Program :
 * @Date : 2023-01-05
 * @Comment :
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateTimeBase {
    @CreatedBy
    @Column(name = "REG_ADM", updatable = false)
    private Long regAdm;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private Instant regDate;
}
