package com.skbroadband.doms.web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CODE_PARENT")
public class CodeParent extends TimeBase {
    /*@EmbeddedId
    private CodeParentId id;*/

    @Id
    @Size(max = 20)
    @NotNull
    @Column(name = "PCODE", nullable = false, length = 20)
    private String id;

    @Size(max = 50)
    @Column(name = "PCODE_NM", length = 50)
    private String pcodeNm;

    @Lob
    @Column(name = "PCODE_MEMO")
    private String pcodeMemo;

    @Column(name = "PCODE_SEQ_NO")
    private Integer pcodeSeqNo;

    @Column(name = "USE_TF")
    private String useTf;

    @Column(name = "DEL_TF")
    private String delTf;

    @Size(max = 30)
    @Column(name = "DEL_ADM", length = 30)
    private String delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;
}