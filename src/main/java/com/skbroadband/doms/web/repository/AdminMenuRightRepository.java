package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.AdminGroup;
import com.skbroadband.doms.web.entity.AdminMenuRight;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminMenuRightRepository
 * @Program :
 * @Date : 2023-01-13
 * @Comment :
 */
public interface AdminMenuRightRepository extends JpaRepository<AdminMenuRight, Long> {
    void deleteByGroupNo(AdminGroup groupNo);

    AdminMenuRight findByMenuNo_MenuCodeAndMenuNo_UseTfAndMenuNo_DelTfAndGroupNo_IdAndGroupNo_UseTfAndGroupNo_DelTf(
            String menuCode,
            String menuUseTf,
            String menuDelTf,
            Long groupNo,
            String groupUseTf,
            String groupDelTf);
}
