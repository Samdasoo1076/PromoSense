package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.Accept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AcceptRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface AcceptRepository extends JpaRepository<Accept, Long> {
    Page<Accept> findByUseTfAndDelTfOrderByIdDesc(String useTf, String delTf, Pageable pageable);
    Boolean existsByAcceptIp(String acceptIp);
    Boolean existsByAcceptIpAndUseTfAndDelTf(String ip, String useTf, String delTf);
    Optional<Accept> findById(Long seqNo);

}
