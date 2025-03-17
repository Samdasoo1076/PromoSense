package com.skbroadband.doms.global.constant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.constant
 * @File : WorkType
 * @Program :
 * @Date : 2023-02-06
 * @Comment :
 */
public enum WorkType {
    Login("login", "로그인", "로그인"),
    Logout("logout", "로그아웃", "로그아웃"),
    List("list", "리스트", "목록조회"),
    Detail("detail", "조회", "단건조회"),
    Excel("excel", "엑셀다운로드", "엑셀다운로드"),
    Pdf("pdf", "pdf다운로드", "pdf다운로드"),
    Registration("registration", "등록", "데이타 등록"),
    Modification("modification", "수정", "데이타 수정"),
    Delete("delete", "삭제", "데이타 삭제");

    final String code;
    final String name;
    final String description;

    WorkType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
