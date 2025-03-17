package com.skbroadband.doms.web.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CodeDetailId implements Serializable {
    private static final long serialVersionUID = 5153737326693968034L;

    @Size(max = 20)
    @NotNull
    @Column(name = "PCODE", nullable = false, length = 20)
    private String pcode;

    @Size(max = 20)
    @NotNull
    @Column(name = "DCODE", nullable = false, length = 20)
    private String dcode;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CodeDetailId entity = (CodeDetailId) o;
        return Objects.equals(this.pcode, entity.pcode) &&
                Objects.equals(this.dcode, entity.dcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pcode, dcode);
    }

}