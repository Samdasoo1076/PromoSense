create table if not exists TBL_ACCEPT
(
    SEQ_NO    int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    ACCEPT_IP varchar(20)      null comment '허용 IP',
    USE_TF    char             null comment '사용여부',
    DEL_TF    char             null comment '삭제여부',
    REG_ADM   int(11) unsigned null comment '등록관리자',
    REG_DATE  datetime         null comment '등록일',
    UP_ADM    int(11) unsigned null comment '수정관리자',
    UP_DATE   datetime         null comment '수정일',
    DEL_ADM   int(11) unsigned null comment '삭제관리자',
    DEL_DATE  datetime         null comment '삭제일'
    );

create table if not exists TBL_ACCESS_TOKEN_WHITE_LIST
(
    ADM_NO       int(11) unsigned not null comment '관리자번호'
    primary key,
    ACCESS_TOKEN varchar(300)     not null comment 'access token',
    ISSUED_DATE  datetime         not null comment '발행일시',
    EXPIRES_IN   datetime         not null comment '만료일시'
    );

create table if not exists TBL_ADMIN_GROUP
(
    GROUP_NO   int(11) unsigned auto_increment comment '관리자그룹 번호'
    primary key,
    GROUP_NAME varchar(50)      null comment '그룹명',
    GROUP_FLAG char             null comment '그룹상태',
    USE_TF     char default 'Y' null comment '사용여부',
    DEL_TF     char default 'N' null comment '삭제여부',
    REG_ADM    int(11) unsigned null comment '등록관리자',
    REG_DATE   datetime         null comment '등록일',
    UP_ADM     int(11) unsigned null comment '수정관리자',
    UP_DATE    datetime         null comment '수정일',
    DEL_ADM    int(11) unsigned null comment '삭제관리자',
    DEL_DATE   datetime         null comment '삭제일'
    )
    auto_increment = 2;

create table if not exists TBL_ADMIN_INFO
(
    ADM_NO          int(11) unsigned auto_increment comment '관리자 번호'
    primary key,
    GROUP_NO        int(11) unsigned   null comment '관리자그룹번호',
    ADM_ID          varchar(30)        null comment '관리자 ID',
    PASSWD          varchar(255)       null comment '비밀번호',
    ADM_NAME        varchar(30)        null comment '관리자 명',
    ADM_INFO        varchar(20)        null comment '관리자 정보(B사:B, C사:C, 양사:A)',
    ADM_HPHONE      varchar(100)       null comment '연락처(휴대폰)',
    ADM_HPHONE_HASH varchar(80)        null comment '연락처(휴대폰) 해시값',
    ADM_PHONE       varchar(30)        null comment '연락처',
    ADM_EMAIL       varchar(120)       null comment '이메일',
    ADM_EMAIL_HASH  varchar(80)        null comment '이메일 해시값',
    ADM_FLAG        char   default '0' null comment '승인 상태(''0'':가입요청, ''1'':가입승인, ''2'':중지, ''3'':가입거절)',
    POSITION_CODE   varchar(20)        null comment '직책코드',
    DEPT            varchar(20)        null comment '부서명',
    MEMO            varchar(500)       null comment '메모',
    USE_TF          char   default 'Y' null comment '사용여부',
    DEL_TF          char   default 'N' null comment '삭제여부',
    LOGIN_DATE      datetime           null comment '최근로그인 일자',
    LOGIN_FAIL_CNT  int(1) default 0   null comment '로그인 실패횟수',
    PASSWD_DATE     datetime           null comment '비밀번호수정일',
    REG_ADM         int(11) unsigned   null comment '등록관리자',
    REG_DATE        datetime           null comment '등록일',
    UP_ADM          int(11) unsigned   null comment '수정관리자',
    UP_DATE         datetime           null comment '수정일',
    DEL_ADM         int(11) unsigned   null comment '삭제관리자',
    DEL_DATE        datetime           null comment '삭제일',
    constraint FK_TBL_ADMIN_GROUP_TO_TBL_ADMIN_INFO
    foreign key (GROUP_NO) references TBL_ADMIN_GROUP (GROUP_NO)
    )
    auto_increment = 2;

create table if not exists TBL_ADMIN_LOG
(
    SEQ_NO    int unsigned auto_increment comment '일련 번호'
    primary key,
    ADM_NO    int(11) unsigned null comment '로그인 관리자 ID',
    LOG_IP    varchar(20)      null comment '로그인 IP',
    LOG_DATE  datetime         null comment '로그 등록일',
    TASK      varchar(300)     null comment '작업 내용',
    TASK_TYPE varchar(50)      null comment '작업 구분'
    );

create table if not exists TBL_ADMIN_MENU
(
    MENU_NO        int unsigned auto_increment comment '메뉴 번호'
    primary key,
    MENU_CODE      varchar(10)      null,
    MENU_NAME      varchar(50)      null comment '메뉴명',
    MENU_URL       varchar(150)     null comment '메뉴 URL',
    MENU_DEPTH     int(1)           null comment '메뉴레벨',
    MENU_FLAG      char             null comment '메뉴 상태',
    MENU_IMG       varchar(50)      null comment '메뉴 이미지',
    MENU_IMG_OVER  varchar(50)      null comment '메뉴 이미지 2',
    MENU_ORDER     int default 0    null,
    USE_TF         char             null comment '사용여부',
    DEL_TF         char             null comment '삭제여부',
    REG_ADM        int(11) unsigned null comment '등록관리자',
    REG_DATE       datetime         null comment '등록일',
    UP_ADM         int(11) unsigned null comment '수정관리자',
    UP_DATE        datetime         null comment '수정일',
    DEL_ADM        int(11) unsigned null comment '삭제관리자',
    DEL_DATE       datetime         null comment '삭제일',
    MENU_PARENT_NO int              null comment '상위 메뉴번호'
    )
    auto_increment = 13;

create table if not exists TBL_ADMIN_MENU_RIGHT
(
    GROUP_NO  int(11) unsigned not null comment '관리자그룹 번호',
    MENU_NO   int(11) unsigned not null comment '메뉴 번호',
    READ_FLAG char             null comment '읽기 권한',
    REG_FLAG  char             null comment '등록 권한',
    UPD_FLAG  char             null comment '수정 권한',
    DEL_FLAG  char             null comment '삭제 권한',
    FILE_FLAG char             null comment '파일 사용 권한',
    REG_ADM   int(11) unsigned null comment '등록관리자',
    REG_DATE  datetime         null comment '등록일',
    primary key (GROUP_NO, MENU_NO),
    constraint FK_TBL_ADMIN_GROUP_TO_TBL_ADMIN_MENU_RIGHT
    foreign key (GROUP_NO) references TBL_ADMIN_GROUP (GROUP_NO),
    constraint FK_TBL_ADMIN_MENU_TO_TBL_ADMIN_MENU_RIGHT
    foreign key (MENU_NO) references TBL_ADMIN_MENU (MENU_NO)
    );

create table if not exists TBL_CAMPAIGN
(
    CA_NO               int(11) unsigned auto_increment comment '캠페인 번호'
    primary key,
    CA_NAME             varchar(255)     null comment '캠페인 명',
    CA_STATE            char default '1' null comment '캠페인 상태',
    TG_TYPE01           varchar(20)      null comment '1차 타겟',
    TG_TYPE02           varchar(20)      null comment '2차 타겟',
    CA_START_TF         char             null comment '시작일 사용여부 X',
    CA_START_DATE       datetime         null comment '캠페인 시작일',
    CA_START_HOUR       varchar(2)       null comment '시작 시간',
    CA_START_MIN        varchar(2)       null comment '시작 분',
    CA_END_TF           char             null comment '종료일 사용여부 X',
    CA_END_DATE         datetime         null comment '캠페인 종료일',
    CA_END_HOUR         varchar(2)       null comment '종료 시간',
    CA_END_MIN          varchar(2)       null comment '종료 분',
    CA_WEEK             varchar(20)      null comment '노출요일',
    CA_TIME             varchar(20)      null comment '노출시간',
    CA_STAY_SEC         int              null comment '아무동작 없이 머문 초 X',
    EXPOSURE_URL_TF     char             null comment '노출URL사용여부 X',
    EXPOSURE_LIMIT_CNT  varchar(20)      null comment '노출 제한 수(노출빈도)',
    SCROLL_PERCENT      int              null comment '스크롤 퍼센트 X',
    CA_PURPOSE          varchar(20)      null comment '캠페인 목표 전환 지점',
    VISIBLE_CNT         int  default 0   null comment '노출수',
    CLICK_CNT           int  default 0   null comment '랜딩 클릭수(CTA)',
    CLOSE_CNT           int  default 0   null comment '닫기 클릭수',
    PHONE_CNT           int  default 0   null comment '전화연결 클릭 수',
    TARGET_VISIT_CNT    int  default 0   null comment '목표설정 도달 수',
    USE_TF              char default 'Y' null comment '사용여부',
    DEL_TF              char default 'N' null comment '삭제여부',
    REG_ADM             int(11) unsigned null comment '등록관리자',
    REG_DATE            datetime         null comment '등록일',
    UP_ADM              int(11) unsigned null comment '수정관리자',
    UP_DATE             datetime         null comment '수정일',
    DEL_ADM             int(11) unsigned null comment '삭제관리자',
    DEL_DATE            datetime         null comment '삭제일',
    CA_GUBUN            varchar(20)      null comment '캠페인 구분',
    CA_MEMO             varchar(500)     null comment '캠페인 설명글',
    CA_ALLDAY_YN        char             null comment '종일 여부',
    TG1_SUB             varchar(20)      null comment '1차타겟 재방문고객선택 후 선택되는 값',
    TG_FIXED_WORD       varchar(200)     null comment '특정 검색어',
    CA_PLACE            varchar(20)      null comment '노출 위치',
    CA_VIEW_POINT       varchar(20)      null comment '노출 시점',
    VIEW_POINT_SUB      varchar(20)      null comment '노출 시점 서브',
    CA_MSG_TYPE         int              null comment '메시지 유형',
    TG_TYPE02_YN        char             null comment '2차타겟 선택 여부',
    CA_PLACE_YN         char             null comment '노출 위치 제외페이지 사용여부',
    VIEW_ORDER          int              null comment '노출순위',
    LEAVE_CNT           int  default 0   null comment '연락처 남기기 수',
    REGIST_CNT          int  default 0   null comment '바로가입 수',
    VISIBLE_MO_CNT      int  default 0   null comment '모바일 노출수',
    CLICK_MO_CNT        int  default 0   null comment '모바일 랜딩 클릭수(CTA)',
    CLOSE_MO_CNT        int  default 0   null comment '모바일 닫기 클릭수',
    TARGET_VISIT_MO_CNT int  default 0   null comment '모바일 목표설정 도달수',
    LEAVE_MO_CNT        int  default 0   null comment '모바일 연락처 남기기 수',
    REGIST_MO_CNT       int  default 0   null comment '모바일 바로가입 수',
    TG2_SUB             varchar(20)      null comment '2차타겟 재방문고객선택 후 선택되는 값'
    )

create table if not exists TBL_CAMPAIGN_BASE_TYPE
(
    BASE_NO     int(11) unsigned auto_increment comment '유형 번호'
    primary key,
    TITLE       varchar(50)      null comment '유형 명',
    CONTENT     varchar(300)     null comment '유형 설명',
    CSS_NAME    varchar(50)      null comment '유형 이미지(CSS)',
    CONTENT_URI varchar(100)     null comment '유형 HTML',
    BASE_ORDER  int              null comment '유형 순번',
    USE_TF      char             null comment '사용여부',
    DEL_TF      char             null comment '삭제여부',
    REG_ADM     int(11) unsigned null comment '등록관리자',
    REG_DATE    datetime         null comment '등록일',
    UP_ADM      int(11) unsigned null comment '수정관리자',
    UP_DATE     datetime         null comment '수정일',
    DEL_ADM     int(11) unsigned null comment '삭제관리자',
    DEL_DATE    datetime         null comment '삭제일'
    )
    auto_increment = 7;

create table if not exists TBL_CAMPAIGN_EXPOSURE_TIME
(
    SEQ_NO     int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO      int(11) unsigned not null comment '캠페인 번호',
    START_TIME varchar(5)       null comment '시작시간',
    END_TIME   varchar(5)       null comment '종료시간',
    REG_ADM    int(11) unsigned null comment '등록관리자',
    REG_DATE   datetime         null comment '등록일',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_EXPOSURE_TIME
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_EXPOSURE_URL
(
    SEQ_NO       int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO        int(11) unsigned not null comment '캠페인 번호',
    EXPOSURE_URL varchar(2000)     null comment '노출URL',
    REG_ADM      int(11) unsigned null comment '등록관리자',
    REG_DATE     datetime         null comment '등록일',
    GUBUN        varchar(5)       null comment '모바일, PC구분 (M : 모바일, P : PC)',
    INCLUDE_YN   char             null comment '포함여부',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_EXPOSURE_URL
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_LOG
(
    SEQ_NO         bigint unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO          int(11) unsigned not null comment '캠페인 번호',
    DEVICE_TYPE    varchar(5)       null comment '접속기기 구분',
    EVENT_TYPE     varchar(15)      null comment '이벤트 구분',
    MSG_TYPE       varchar(15)      null comment '메시지 구분',
    CA_REFERER     varchar(500)     null comment '외부 접속 경로',
    COOKIE_CNT     int              null comment '재방문 구분',
    CA_YMD         varchar(30)      null comment '접속년월일',
    CA_YEAR        varchar(4)       null comment '접속년',
    CA_MONTH       varchar(30)      null comment '접속달',
    CA_DAY         varchar(2)       null comment '접속일',
    CA_HOUR        varchar(2)       null comment '접속시간',
    CA_MIN         varchar(2)       null comment '접속 분',
    CA_WEEK        varchar(2)       null comment '접속요일',
    EXP_TIME_START datetime         null comment '접속시작시간',
    EXP_TIME_END   datetime         null comment '접속종료시간',
    EXP_TIME       int              null comment '채류초',
    CA_SESSION_ID  varchar(255)     null comment '세션ID',
    CA_COOKIE_ID   varchar(255)     null comment '쿠키ID',
    CA_FULL_URL    varchar(500)     null comment '접속 전체 URL',
    CA_TITLE       varchar(150)     null comment '접속페이지 제목',
    CA_IP          varchar(20)      null comment '접속 IP',
    CA_MENU_NO     varchar(20)      null comment '접속 메뉴 일련번호',
    CA_C_CODE      varchar(20)      null comment '캠페인 코드 (SKB 전용)',
    REG_DATE       datetime         null comment '등록일',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_LOG
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_MESSAGE
(
    MSG_NO                        int(11) unsigned auto_increment comment '메시지 번호'
        primary key,
    CA_NO                         int(11) unsigned not null comment '캠페인 번호',
    MSG_TYPE                      varchar(25)      null comment '메시지 타입',
    MSG_STATE                     char             null comment '메시지 상태',
    MSG_NAME                      varchar(30)      null comment '메시지 명',
    MSG_IMG_NM_01                 varchar(100)     null comment '서버저장 이미지명',
    MSG_IMG_RNM_01                varchar(100)     null comment '이미지명',
    MSG_IMG_NM_02                 varchar(100)     null comment '서버저장 이미지명2',
    MSG_IMG_RNM_02                varchar(100)     null comment '이미지명2',
    MSG_IMG_NM_03                 varchar(100)     null comment '서버저장 이미지명3',
    MSG_IMG_RNM_03                varchar(100)     null comment '이미지명3',
    MSG_IMG_ALTER_01              varchar(300)     null comment '이미지1 대체 텍스트',
    MSG_IMG_ALTER_02              varchar(300)     null comment '이미지2 대체 텍스트',
    MSG_IMG_ALTER_03              varchar(300)     null comment '이미지3 대체 텍스트',
    MSG_TITLE_TF                  char             null comment '메인문구 사용 여부',
    MSG_TITLE                     varchar(50)      null comment '메인문구',
    MSG_CONTENTS_TF               char             null comment '서브문구 사용 여부',
    MSG_CONTENTS                  varchar(300)     null comment '서브문구',
    MSG_BUTTON_TF                 char             null comment '랜딩 URL 버튼 사용여부',
    MSG_BUTTON_PC_URL             varchar(300)     null comment '랜딩URL (PC)',
    MSG_BUTTON_MO_URL             varchar(300)     null comment '랜딩URL (MO)',
    MSG_BUTTON_COLOR              varchar(20)      null comment '랜딩 버튼 글자 색상',
    MSG_BUTTON_URL_TARGET_P       char             null comment '랜딩 URL 타겟 PC (새창, 자기창)',
    PHONE_BUTTON_TF               char             null comment '전화상담 노출 여부',
    MSG_THUMB_PC_IMG              varchar(50)      null comment '메시지 썸네일이미지 (PC)',
    MSG_THUMB_MO_IMG              varchar(50)      null comment '메시지 썸네일이미지 (MO)',
    USE_TF                        char             null comment '사용여부',
    DEL_TF                        char             null comment '삭제여부',
    REG_ADM                       int(11) unsigned null comment '등록관리자',
    REG_DATE                      datetime         null comment '등록일',
    UP_ADM                        int(11) unsigned null comment '수정관리자',
    UP_DATE                       datetime         null comment '수정일',
    DEL_ADM                       int(11) unsigned null comment '삭제관리자',
    DEL_DATE                      datetime         null comment '삭제일',
    MSG_TITLE_COLOR               varchar(10)      null comment '메인문구 색상',
    MSG_IMG_TF                    char             null comment '이미지 사용여부',
    MSG_CONTENTS_COLOR            varchar(10)      null comment '서브문구 색상',
    MSG_BUTTON                    varchar(100)     null comment '랜딩 버튼 문구',
    MSG_BUTTON_URL_TARGET_M       char             null comment '랜딩 URL 타겟 모바일(새창, 자기창)',
    MSG_BUTTON_BG_COLOR           varchar(20)      null comment '랜딩 버튼 배경 색상',
    MSG_LEAVE_LINE_COLOR          varchar(20)      null comment '연락처 남기기 라인 색상',
    MSG_LEAVE_COLOR               varchar(20)      null comment '연락처 남기기 문구 색상',
    MSG_LEAVE_TF                  char             null comment '연락처 남기기 사용여부',
    MSG_REGIST_TF                 char             null comment '바로가입 사용여부',
    MSG_REGIST_LINE_COLOR         varchar(20)      null comment '바로가입 라인 색상',
    MSG_REGIST_COLOR              varchar(20)      null comment '바로가입 문구 색상',
    MSG_MOBILE_CALLING_TF         char             null comment '모바일 전용 전화 걸기 사용여부',
    MSG_MOBILE_CALLING_LINE_COLOR varchar(20)      null comment '모바일 전용 전화걸기 라인 색상',
    MSG_MOBILE_CALLING_COLOR      varchar(20)      null comment '모바일 전용 전화걸기 문구 색상',
    MSG_BACK_DIM_TF               char             null comment '메시지 백그라운드 DIM 처리여부',
    MSG_REVIEW_TF                 char             null comment '메시지 다시보지 않기 사용여부',
    MSG_REVIEW_TYPE               varchar(20)      null comment '다시보지 않기 옵션(하루, 날짜지정)',
    MSG_REVIEW_DAYS               varchar(3)       null comment '다시보지 않기 일수',
    MSG_SAVE_TF                   char             null comment '메지시 저장여부',
    MSG_PC_HTML                   text             null comment 'pc html',
    MSG_MO_HTML                   text             null comment 'MOBILE HTML',
    MSG_BAN_BG_COLOR              varchar(20)      null comment '띠배너 배경 색상',
    MSG_BAN_TEXT_COLOR            varchar(20)      null comment '띠배너 닫기 색상',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_MESSAGE
        foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_MESSAGE_HISTORY
(
    MSG_HIS_NO                    int(11) unsigned auto_increment comment '메시지 이력 번호'
    primary key,
    MSG_TYPE                      varchar(25)      null comment '메시지 타입',
    MSG_STATE                     char             null comment '메시지 상태',
    MSG_NAME                      varchar(30)      null comment '메시지 명',
    MSG_IMG_NM_01                 varchar(100)     null comment '서버저장 이미지명',
    MSG_IMG_RNM_01                varchar(100)     null comment '이미지명',
    MSG_IMG_NM_02                 varchar(100)     null comment '서버저장 이미지명2',
    MSG_IMG_RNM_02                varchar(100)     null comment '이미지명2',
    MSG_IMG_NM_03                 varchar(100)     null comment '서버저장 이미지명3',
    MSG_IMG_RNM_03                varchar(100)     null comment '이미지명3',
    MSG_IMG_ALTER_01              varchar(300)     null comment '이미지1 대체 텍스트',
    MSG_IMG_ALTER_02              varchar(300)     null comment '이미지2 대체 텍스트',
    MSG_IMG_ALTER_03              varchar(300)     null comment '이미지3 대체 텍스트',
    MSG_TITLE_TF                  char             null comment '메인문구 사용 여부',
    MSG_TITLE                     varchar(50)      null comment '메인문구',
    MSG_CONTENTS_TF               char             null comment '서브문구 사용 여부',
    MSG_CONTENTS                  varchar(300)     null comment '서브문구',
    MSG_BUTTON_TF                 char             null comment '랜딩 URL 버튼 사용여부',
    MSG_BUTTON_PC_URL             varchar(300)     null comment '랜딩URL (PC)',
    MSG_BUTTON_MO_URL             varchar(300)     null comment '랜딩URL (MO)',
    MSG_BUTTON_COLOR              varchar(20)      null comment '랜딩 버튼 글자 색상',
    MSG_BUTTON_URL_TARGET_P       char             null comment '랜딩 URL 타겟 PC (새창, 자기창)',
    PHONE_BUTTON_TF               char             null comment '전화상담 노출 여부',
    MSG_THUMB_PC_IMG              varchar(50)      null comment '메시지 썸네일이미지 (PC)',
    MSG_THUMB_MO_IMG              varchar(50)      null comment '메시지 썸네일이미지 (MO)',
    USE_TF                        char             null comment '사용여부',
    DEL_TF                        char             null comment '삭제여부',
    REG_ADM                       int(11) unsigned null comment '등록관리자',
    REG_DATE                      datetime         null comment '등록일',
    UP_ADM                        int(11) unsigned null comment '수정관리자',
    UP_DATE                       datetime         null comment '수정일',
    DEL_ADM                       int(11) unsigned null comment '삭제관리자',
    DEL_DATE                      datetime         null comment '삭제일',
    MSG_TITLE_COLOR               varchar(10)      null comment '메인문구 색상',
    MSG_IMG_TF                    char             null comment '이미지 사용여부',
    MSG_CONTENTS_COLOR            varchar(10)      null comment '서브문구 색상',
    MSG_BUTTON                    varchar(100)     null comment '랜딩 버튼 문구',
    MSG_BUTTON_URL_TARGET_M       char             null comment '랜딩 URL 타겟 모바일(새창, 자기창)',
    MSG_BUTTON_BG_COLOR           varchar(20)      null comment '랜딩 버튼 배경 색상',
    MSG_LEAVE_LINE_COLOR          varchar(20)      null comment '연락처 남기기 라인 색상',
    MSG_LEAVE_COLOR               varchar(20)      null comment '연락처 남기기 문구 색상',
    MSG_LEAVE_TF                  char             null comment '연락처 남기기 사용여부',
    MSG_REGIST_TF                 char             null comment '바로가입 사용여부',
    MSG_REGIST_LINE_COLOR         varchar(20)      null comment '바로가입 라인 색상',
    MSG_REGIST_COLOR              varchar(20)      null comment '바로가입 문구 색상',
    MSG_MOBILE_CALLING_TF         char             null comment '모바일 전용 전화 걸기 사용여부',
    MSG_MOBILE_CALLING_LINE_COLOR varchar(20)      null comment '모바일 전용 전화걸기 라인 색상',
    MSG_MOBILE_CALLING_COLOR      varchar(20)      null comment '모바일 전용 전화걸기 문구 색상',
    MSG_BACK_DIM_TF               char             null comment '메시지 백그라운드 DIM 처리여부',
    MSG_REVIEW_TF                 char             null comment '메시지 다시보지 않기 사용여부',
    MSG_REVIEW_TYPE               varchar(20)      null comment '다시보지 않기 옵션(하루, 날짜지정)',
    MSG_REVIEW_DAYS               varchar(3)       null comment '다시보지 않기 일수',
    MSG_SAVE_TF                   char             null comment '메지시 저장여부',
    MSG_PC_HTML                   text             null comment 'pc html',
    MSG_MO_HTML                   text             null comment 'MOBILE HTML',
    MSG_CAPTURE_NAME              varchar(100)     null comment '캡쳐된 파일이름',
    MSG_BAN_BG_COLOR              varchar(20)      null comment '띠배너 배경 색상',
    MSG_BAN_TEXT_COLOR            varchar(20)      null comment '띠배너 닫기 색상'
    );

create table if not exists TBL_CAMPAIGN_REVISIT_URL
(
    SEQ_NO      int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO       int(11) unsigned not null comment '캠페인 번호',
    GUBUN       varchar(5)       null comment '모바일, PC구분 (M : 모바일, P : PC)',
    REVISIT_URL varchar(2000)     null comment '재방문 URL',
    REG_ADM     int(11) unsigned null comment '등록관리자',
    REG_DATE    datetime         null comment '등록일',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_REVISIT_URL
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_SPEC_URL
(
    SEQ_NO   int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO    int(11) unsigned not null comment '캠페인 번호',
    SPEC_URL varchar(2000)     null comment '특정 URL',
    REG_ADM  int(11) unsigned null comment '등록관리자',
    REG_DATE datetime         null comment '등록일',
    GUBUN    varchar(5)       null comment '모바일, PC구분 (M : 모바일, P : PC)',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_SPEC_URL
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CAMPAIGN_TARGET_URL
(
    SEQ_NO     int(11) unsigned auto_increment comment '일련 번호'
    primary key,
    CA_NO      int(11) unsigned not null comment '캠페인 번호',
    TARGET_URL varchar(2000)     null comment '목표전환 URL',
    REG_ADM    int(11) unsigned null comment '등록관리자',
    REG_DATE   datetime         null comment '등록일',
    GUBUN      varchar(5)       null comment '모바일, PC구분 (M : 모바일, P : PC)',
    constraint FK_TBL_CAMPAIGN_TO_TBL_CAMPAIGN_TARGET_URL
    foreign key (CA_NO) references TBL_CAMPAIGN (CA_NO)
    );

create table if not exists TBL_CODE_PARENT
(
    PCODE        varchar(20)      not null comment '대분류 코드'
    primary key,
    PCODE_NM     varchar(50)      null comment '코드명',
    PCODE_MEMO   text             null comment '코드메모',
    PCODE_SEQ_NO int              null comment '코드 전시 순번',
    USE_TF       char default 'Y' null comment '사용여부',
    DEL_TF       char default 'N' null comment '삭제여부',
    REG_ADM      int(11) unsigned null comment '등록관리자',
    REG_DATE     datetime         null comment '등록일',
    UP_ADM       int(11) unsigned null comment '수정관리자',
    UP_DATE      datetime         null comment '수정일',
    DEL_ADM      int(11) unsigned null comment '삭제관리자',
    DEL_DATE     datetime         null comment '삭제일'
    );

create table if not exists TBL_CODE_DETAIL
(
    PCODE            varchar(20)      not null comment '대분류 코드',
    DCODE            varchar(20)      not null comment '하위분류 코드',
    DCODE_NM         varchar(50)      null comment '코드명',
    DCODE_EXT        varchar(255)     null comment '코드설명',
    DCODE_SEQ_NO     int              null comment '코드 전시 순번',
    DCODE_EXT_VALUE1 varchar(300)     null comment '사용자정의 값1',
    DCODE_EXT_VALUE2 varchar(300)     null comment '사용자정의 값2',
    DCODE_EXT_VALUE3 varchar(300)     null comment '사용자정의 값3',
    DCODE_EXT_VALUE4 varchar(300)     null comment '사용자정의 값4',
    USE_TF           char             null comment '사용여부',
    DEL_TF           char             null comment '삭제여부',
    REG_ADM          int(11) unsigned null comment '등록관리자',
    REG_DATE         datetime         null comment '등록일',
    UP_ADM           int(11) unsigned null comment '수정관리자',
    UP_DATE          datetime         null comment '수정일',
    DEL_ADM          int(11) unsigned null comment '삭제관리자',
    DEL_DATE         datetime         null comment '삭제일',
    primary key (PCODE, DCODE),
    constraint TBL_CODE_DETAIL_TBL_CODE_PARENT_PCODE_NO_PCODE_fk
    foreign key (PCODE) references TBL_CODE_PARENT (PCODE)
    );

create index TBL_CAMPAIGN_CA_END_DATE_index
    on TBL_CAMPAIGN (CA_END_DATE desc);
create index TBL_CAMPAIGN_CA_START_DATE_index
    on TBL_CAMPAIGN (CA_START_DATE desc);
