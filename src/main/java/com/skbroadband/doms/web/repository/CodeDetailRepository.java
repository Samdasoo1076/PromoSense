package com.skbroadband.doms.web.repository;

import com.skbroadband.doms.web.entity.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : 홍수민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.repository
 * @File : CodeDetailRepository
 * @Program :
 * @Date : 2023-02-08
 * @Comment :
 */
public interface CodeDetailRepository extends JpaRepository<CodeDetail, String> {

    List<CodeDetail> findByUseTfAndDelTfAndCodeParent_IdOrderByDcodeSeqNo(String useTf, String delTf, String pcode);

    CodeDetail findByUseTfAndDelTfAndCodeParent_IdAndDcode(String useTf, String delTf, String pcode, String dcode);

}
