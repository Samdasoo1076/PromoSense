package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.AdminGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminGroupRepository
 * @Program :
 * @Date : 2023-01-13
 * @Comment :
 */
public interface AdminGroupRepository extends JpaRepository<AdminGroup, Long> {

    List<AdminGroup> findByUseTfAndDelTfOrderByRegDate(String useTf, String delTf);

    Page<AdminGroup> findByUseTfAndDelTf(String useTf, String delTf, Pageable pageable);

    Optional<AdminGroup> findById(Long id);

    Boolean existsByGroupName(String groupName);

}
