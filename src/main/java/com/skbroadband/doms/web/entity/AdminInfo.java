package com.skbroadband.doms.web.entity;

import com.skbroadband.doms.web.dto.AdminInfoDto;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_ADMIN_INFO")
public class AdminInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADM_NO", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_NO")
    private AdminGroup groupNo;

    @Size(max = 30)
    @Column(name = "ADM_ID", length = 30)
    private String admId;

    @Size(max = 255)
    @Column(name = "PASSWD")
    private String passwd;

    @Size(max = 30)
    @Column(name = "ADM_NAME", length = 30)
    private String admName;

    @Size(max = 20)
    @Column(name = "ADM_INFO", length = 30)
    private String admInfo;

    @Size(max = 100)
    @Column(name = "ADM_HPHONE", length = 100)
    private String admHphone;

    @Size(max = 80)
    @Column(name = "ADM_HPHONE_HASH", length = 80)
    private String admHphoneHash;

    @Size(max = 30)
    @Column(name = "ADM_PHONE", length = 30)
    private String admPhone;

    @Size(max = 120)
    @Column(name = "ADM_EMAIL", length = 120)
    private String admEmail;

    @Size(max = 80)
    @Column(name = "ADM_EMAIL_HASH", length = 80)
    private String admEmailHash;

    @Column(name = "ADM_FLAG", length = 2)
    private String admFlag;

    @Size(max = 20)
    @Column(name = "POSITION_CODE", length = 20)
    private String positionCode;

    @Size(max = 20)
    @Column(name = "DEPT", length = 20)
    private String dept;

    @Size(max = 500)
    @Column(name = "MEMO", length = 500)
    private String memo;

    @Column(name = "USE_TF", length = 2)
    private String useTf;

    @Column(name = "DEL_TF", length = 2)
    private String delTf;

    @Column(name = "LOGIN_DATE")
    private Instant loginDate;

    @Column(name = "LOGIN_FAIL_CNT")
    private Integer loginFailCnt;

    @Column(name = "PASSWD_DATE")
    private Instant passwdDate;

    @Column(name = "DEL_ADM", length = 30)
    private Long delAdm;

    @Column(name = "DEL_DATE")
    private Instant delDate;

    @Column(name = "UP_ADM", length = 30)
    private Long upAdm;

    @Column(name = "UP_DATE")
    private Instant upDate;

    @CreatedBy
    @Column(name = "REG_ADM")
    private Long regAdm;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private Instant regDate;

    /**
     * 패스워드 변경
     *
     * @param passwd
     */
    public void updatePasswd(String passwd) {
        this.passwd = passwd;
        this.passwdDate = Instant.now();
    }

    /**
     * 로그인 실패횟수
     *
     */
    public void updateLoginFailCnt() {
        if(this.loginFailCnt == null) {
            this.loginFailCnt = 1;
        } else {
            this.loginFailCnt += 1;
        }
    }

    /**
     * 로그인 일자 수정
     *
     */
    public void updateLoginDate() {
        this.loginDate = Instant.now();
        this.loginFailCnt = 0;
    }

    /**
     * role 설정
     * @param groupNo
     */
    public void setGroupNo(AdminGroup groupNo) {
        this.groupNo = groupNo;
    }

    /**
     * 계정 삭제
     * @param admNo
     */
    public void deleteAdminInfo(Long admNo) {
        this.delTf = "Y";
        this.useTf = "N";
        this.delAdm = admNo;
        this.delDate = Instant.now();
    }

    public void updateAdmInfo(AdminInfoDto param, Long upAdm) {
        if(StringUtils.hasText(param.getPasswd())) this.passwd = param.getPasswd();
        if(StringUtils.hasText(param.getAdmEmail())) this.admEmail = param.getAdmEmail();
        if(StringUtils.hasText(param.getAdmEmailHash())) this.admEmailHash = param.getAdmEmailHash();;

        this.admHphone = param.getAdmHphone();
        this.admHphoneHash = param.getAdmHphoneHash();
        this.admInfo = param.getAdmInfo();
        this.groupNo= AdminGroup.builder().id(param.getParamGroupNo()).build();
        this.dept = param.getDept();
        this.memo = param.getMemo();
        this.admFlag = param.getAdmFlag();
        this.upAdm = upAdm;
        this.upDate = Instant.now();

        /* 로그인 실패 카운트 초기화 */
        if (param.getLoginFailCnt() != null) {
            this.loginFailCnt = param.getLoginFailCnt();
        }
    }

    public void upadateRegAdmAndUpAdm(Long id) {
        this.regAdm = id;
        this.upAdm = id;
    }
}