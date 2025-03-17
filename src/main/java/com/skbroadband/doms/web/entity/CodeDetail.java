package com.skbroadband.doms.web.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_CODE_DETAIL")
public class CodeDetail extends TimeBase {

    @EmbeddedId
    private CodeDetailId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "PCODE", referencedColumnName = "PCODE", nullable = false, insertable = false, updatable = false)
    })
    private CodeParent codeParent;

    @Size(max = 20)
    @Column(name = "DCODE", length = 20, insertable = false, updatable = false)
    private String dcode;

    @Size(max = 50)
    @Column(name = "DCODE_NM", length = 50)
    private String dcodeNm;

    @Size(max = 255)
    @Column(name = "DCODE_EXT")
    private String dcodeExt;

    @Column(name = "DCODE_SEQ_NO")
    private Integer dcodeSeqNo;

    @Size(max = 30)
    @Column(name = "DCODE_EXT_VALUE1", length = 30)
    private String dcodeExtValue1;

    @Size(max = 30)
    @Column(name = "DCODE_EXT_VALUE2", length = 30)
    private String dcodeExtValue2;

    @Size(max = 30)
    @Column(name = "DCODE_EXT_VALUE3", length = 30)
    private String dcodeExtValue3;

    @Size(max = 30)
    @Column(name = "DCODE_EXT_VALUE4", length = 30)
    private String dcodeExtValue4;

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