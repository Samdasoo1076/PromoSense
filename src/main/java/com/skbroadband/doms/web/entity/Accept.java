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
@Table(name = "TBL_ACCEPT")
public class Accept extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 20)
    @Column(name = "ACCEPT_IP", length = 20)
    private String acceptIp;

    @Column(name = "USE_TF", length = 2)
    private String useTf;

    @Column(name = "DEL_TF", length = 2)
    private String delTf;


    @Column(name = "DEL_ADM")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

    /**
     * 접속 IP 삭제
     */
    public void deleteAcceptIp(Long delAdm) {
        this.delTf = "Y";
        this.useTf = "N";
        this.delAdm = delAdm;
        this.delDate = Instant.now();
    }

}