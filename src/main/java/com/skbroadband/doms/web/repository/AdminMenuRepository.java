package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminMenuRepository
 * @Program :
 * @Date : 2023-01-16
 * @Comment :
 */
public interface AdminMenuRepository extends JpaRepository<AdminMenu, Long> {
    List<AdminMenu> findByUseTfAndDelTfAndMenuParentNoIsNullOrderByMenuOrderAsc(@Param("useTf") String useTf,
                                                                                @Param("delTf") String delTf);
    Boolean existsAdminMenuByMenuCodeIgnoreCaseAndUseTfAndDelTf(String menuCode, String useTf, String delTf);

    @Modifying(clearAutomatically = true)
    @Query("update AdminMenu menu set menu.menuOrder=:order, menu.upAdm=:admNo, menu.upDate=current_timestamp where menu.id=:id")
    void changeOrder(@Param("id") Long id, @Param("order") Integer order, @Param("admNo") Long admNo);

    List<AdminMenu> findByUseTfAndDelTfAndMenuParentNoOrderByMenuOrderAsc(@Param("useTf") String useTf,
                                                                          @Param("delTf") String delTf,
                                                                          @Param("id") Long menuId);
}
