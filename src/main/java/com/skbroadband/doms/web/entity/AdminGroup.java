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
@Table(name = "TBL_ADMIN_GROUP")
public class AdminGroup extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 50)
    @Column(name = "GROUP_NAME", length = 50)
    private String groupName;

    @Column(name = "GROUP_FLAG", length = 2)
    private String groupFlag;

    @Column(name = "USE_TF", length = 2)
    private String useTf;

    @Column(name = "DEL_TF", length = 2)
    private String delTf;

    @Column(name = "DEL_ADM")
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

    /**
     * 권한 그룹 삭제
     */
    public void deleteGroup(Long delAdm) {
        this.delTf = "Y";
        this.useTf = "N";
        this.delAdm = delAdm;
        this.delDate = Instant.now();
    }
}