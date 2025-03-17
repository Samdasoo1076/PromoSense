package com.skbroadband.doms.web.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skbroadband.doms.web.dto.AdminMenuDto;
import com.skbroadband.doms.web.dto.AdminMenuRightDto;
import com.skbroadband.doms.web.dto.QAdminMenuDto;
import com.skbroadband.doms.web.dto.QAdminMenuRightDto;
import com.skbroadband.doms.web.entity.QAdminMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.skbroadband.doms.web.entity.QAdminMenu.adminMenu;
import static com.skbroadband.doms.web.entity.QAdminMenuRight.adminMenuRight;

@Repository
@RequiredArgsConstructor
public class AdminMenuRightSupportRepository {
    @Qualifier(value = "webJpaQueryFactory")
    private final JPAQueryFactory jpaQueryFactory;

    public List<AdminMenuRightDto> getAuthMenuList(Long groupNo) {
        QAdminMenu adminMenu1 = new QAdminMenu("adminMenu1");
        JPAQuery<AdminMenuRightDto> content = jpaQueryFactory
                .select(new QAdminMenuRightDto(adminMenu.id,
                        adminMenu.menuName,
                        adminMenu.menuDepth,
                        adminMenu.menuParentNo,
                        adminMenu.menuCode,
                        adminMenuRight.readFlag,
                        adminMenuRight.regFlag,
                        adminMenuRight.updFlag,
                        adminMenuRight.delFlag,
                        adminMenuRight.fileFlag,
                        adminMenuRight.regAdm,
                        adminMenuRight.regDate
                ))
                .from(adminMenuRight)
                .rightJoin(adminMenu)
                .on(adminMenuRight.menuNo.eq(adminMenu), adminMenuRight.groupNo.id.eq(groupNo))
                .leftJoin(adminMenu1)
                .on(adminMenu.menuParentNo.eq(adminMenu1.id))
                .where(
                        adminMenu.useTf.eq("Y"),
                        adminMenu.delTf.eq("N")
                )
                .orderBy(adminMenu1.menuOrder.stringValue().concat(".").concat(
                        adminMenu.menuOrder.stringValue()).coalesce(adminMenu.menuOrder.stringValue()).asc());

        return content.fetch();
    }

    @Cacheable(value = "menuCache", key="#groupNo")
    public List<AdminMenuDto> menuByRight(Long groupNo) {
        return jpaQueryFactory
                .select(new QAdminMenuDto(
                        adminMenuRight.menuNo.id,
                        adminMenuRight.menuNo.menuCode,
                        adminMenuRight.menuNo.menuName,
                        adminMenuRight.menuNo.menuUrl,
                        adminMenuRight.menuNo.menuDepth,
                        adminMenuRight.menuNo.menuImg,
                        adminMenuRight.menuNo.menuOrder,
                        adminMenuRight.menuNo.menuParentNo,
                        adminMenuRight.menuNo.menuParentNo.coalesce(adminMenuRight.menuNo.id)
                ))
                .from(adminMenuRight)
                .innerJoin(adminMenuRight.menuNo)
                .innerJoin(adminMenuRight.groupNo)
                .where(
                        adminMenuRight.menuNo.useTf.eq("Y"),
                        adminMenuRight.menuNo.delTf.eq("N"),
                        adminMenuRight.groupNo.useTf.eq("Y"),
                        adminMenuRight.groupNo.delTf.eq("N"),
                        adminMenuRight.groupNo.id.eq(groupNo),
                        ExpressionUtils.anyOf(
                                adminMenuRight.readFlag.eq("Y"),
                                adminMenuRight.regFlag.eq("Y"),
                                adminMenuRight.updFlag.eq("Y"),
                                adminMenuRight.delFlag.eq("Y"),
                                adminMenuRight.fileFlag.eq("Y")
                        ),
                        adminMenuRight.menuNo.menuUrl.ne("/myinfo.do?acl=M0000")
                )
                .fetch();
    }
}
