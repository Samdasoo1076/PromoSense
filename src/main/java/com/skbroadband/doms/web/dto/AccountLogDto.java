package com.skbroadband.doms.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.Instant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.dto
 * @File : AdminInfoDto
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AccountLogDto {
    private Long seqNo;
    private Long groupNo;
    private String admId;
    private Long admNo;
    private String logIp;
    private Instant logDate;
    private String task;
    private String taskType;
    private String groupName;
    private String dept;

    @QueryProjection
    public AccountLogDto(Long seqNo, Long groupNo, String admId, Long admNo, String logIp,
                         Instant logDate, String task, String taskType, String groupName, String dept) {
        this.seqNo = seqNo;
        this.groupNo = groupNo;
        this.admId = admId;
        this.admNo = admNo;
        this.logIp = logIp;
        this.logDate = logDate;
        this.task = task;
        this.taskType = taskType;
        this.groupName = groupName;
        this.dept = dept;
    }
}
