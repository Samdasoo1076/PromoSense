package com.skbroadband.doms.web.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Embeddable
public class AdminMenuRightId implements Serializable {
    private static final long serialVersionUID = 1504087496616613985L;
    @Column(name = "GROUP_NO", columnDefinition = "INT UNSIGNED not null")
    private Long groupNo;

    @Column(name = "MENU_NO", columnDefinition = "INT UNSIGNED not null")
    private Long menuNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdminMenuRightId entity = (AdminMenuRightId) o;
        return Objects.equals(this.menuNo, entity.menuNo) &&
                Objects.equals(this.groupNo, entity.groupNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuNo, groupNo);
    }

}