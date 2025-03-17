package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminLogRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {
    long countAllBy();

}
