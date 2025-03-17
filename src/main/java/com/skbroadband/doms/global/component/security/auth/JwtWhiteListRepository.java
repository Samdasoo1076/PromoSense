package com.skbroadband.doms.global.component.security.auth;

import com.skbroadband.doms.web.entity.AccessTokenWhiteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : JwtWhiteListRepository
 * @Program :
 * @Date : 2023-01-12
 * @Comment :
 */
public interface JwtWhiteListRepository extends JpaRepository<AccessTokenWhiteList, Long> {
    @Modifying
    @Query("delete from AccessTokenWhiteList where admNo=:id")
    void deleteById(@Param("id") Long id);
}
