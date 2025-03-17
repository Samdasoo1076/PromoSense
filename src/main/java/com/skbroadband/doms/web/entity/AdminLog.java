package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_ADMIN_LOG")
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "ADM_NO")
    private Long admNo;

    @Size(max = 20)
    @Column(name = "LOG_IP", length = 20)
    private String logIp;

    @Column(name = "LOG_DATE")
    private Instant logDate;

    @Size(max = 300)
    @Column(name = "TASK", length = 300)
    private String task;

    @Size(max = 50)
    @Column(name = "TASK_TYPE", length = 50)
    private String taskType;

}