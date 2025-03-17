package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : AdminInfoRepository
 * @Program :
 * @Date : 2023-01-04
 * @Comment :
 */
public interface AdminInfoRepository extends JpaRepository<AdminInfo, Long> {
    Boolean existsByAdmId(String userId);

    Boolean existsByAdmName(String userName);

    Boolean existsByAdmEmail(String email);

    Boolean existsByAdmEmailHash(String email);

    Optional<AdminInfo> findByAdmId(String admId);

    Optional<AdminInfo> findByAdmIdAndUseTfAndDelTf(String admId, String useTf, String delTf);

    AdminInfo findByAdmNameAndAdmEmailHash(String admName, String admEmail);

    AdminInfo findByAdmIdAndAdmEmailHash(String admId, String admEmail);

    long countAllByUseTfAndDelTf(String useTf, String delTf);
}
