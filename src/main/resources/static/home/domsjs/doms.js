let doms_url = "144.24.85.101:7080";

// 스테이징
if (location.host && location.host === "wwwstg.bworld.co.kr") {
  doms_url = "1.225.22.46";

  doms_securekey = "BoXYeCb4EueDKLTZYy6pzr1yK5GsifS9";
} else {
  doms_securekey = "HsVGL3gWBba1JsInccfOJHqEq5I7igMN";
}

if (location.host === "") {
  doms_url = "localhost";
  doms_securekey = "BoXYeCb4EueDKLTZYy6pzr1yK5GsifS9";
}

// localStorage에 담을 내용
// 1. 쿠키 아이디 (고유값)
// 2. 방문 횟수
// 3. 최초 접속일
// 4. 마지막 접속일
// 5. 최초 방문 URL
// 6. 마지막 URL (방문)

// client Session
// console.log(session_id);

// popup 구분
// 1. 캠페인 콘텐츠 팝업(세로팝업)
// 2. 캠페인 콘텐츠 팝업(가로팝업)
// 3. 이미지 플로팅
// 4. 텍스트 플로팅
// 5. 미니 플로팅
// 6. 이미지 띠배너

// ALL 전체, NEW 신규, RETURN 재방문 고객, SEARCH 사이트 탐색에 관심이 많은 고객, FIXED 특정 경로로 유입한 고객

// TAR1_ALL 전체, TAR1_FIXED 특정 페이지에 관심이 많은 고객 (최근 7일 동안 특정 페이지를 3번 이상 방문한 고객),
// TAR1_EVERY 최근에 자주 방문한 고객 (최근 7일 동안 사이트에 3번 이상 접속한 고객), TAR1_ONLINE 온라인 상담 가입상담 신청 이력이 있는 고객

const generateRandomString = (num) => {
  const characters =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
  let result = "";
  const charactersLength = characters.length;
  for (let i = 0; i < num; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
};

// 숫자를 받아 날자를 리턴하는 합수
let getPlusDate = function (intDay, hh, mi, ss) {
  let plusDate = "";
  let now_day = new Date();
  now_day.setDate(now_day.getDate() + intDay);

  plusDate =
    now_day.toISOString().substr(0, 10) + " " + hh + ":" + mi + ":" + ss;

  return plusDate;
};

let getKeywordFromUrl = function (str) {
  let result = "";

  if (str.includes("?")) {
    const queryStr = str.split("?");

    if (queryStr[1].includes("&")) {
      const queryWord = queryStr[1].split("&");

      for (var i = 0; i < queryWord.length; i++) {
        const queryWords = queryWord[i].split("=");

        try {
          decodeURIComponent(queryWords[1]);
          if (queryWords[1] != "")
            result = result + " " + decodeURIComponent(queryWords[1]);
        } catch (e) {
          continue;
        }
      }
    }
  }

  return result;
};

let doms_is_mobile = false;
let doms_open = true;
let doms_time = 0;
let doms_exp_time = 0;

let doms_now_day = new Date();
let doms_year = doms_now_day.getFullYear();
let doms_month = ("0" + (doms_now_day.getMonth() + 1)).slice(-2);
let doms_day = ("0" + doms_now_day.getDate()).slice(-2);
let doms_hour = ("0" + doms_now_day.getHours()).slice(-2);
let doms_min = ("0" + doms_now_day.getMinutes()).slice(-2);
let doms_sec = ("0" + doms_now_day.getSeconds()).slice(-2);
let doms7DateLater = getPlusDate(7, doms_hour, doms_min, doms_sec);
let doms100DateLater = getPlusDate(100, doms_hour, doms_min, doms_sec);

let cookie_id =
  doms_year +
  "" +
  doms_month +
  "" +
  doms_day +
  "" +
  doms_hour +
  "" +
  doms_min +
  "" +
  doms_sec +
  "" +
  generateRandomString(5);
let cookie_cnt = 1;
let cookie_create_date =
  doms_year +
  "-" +
  doms_month +
  "-" +
  doms_day +
  " " +
  doms_hour +
  ":" +
  doms_min +
  ":" +
  doms_sec;
let cookie_last_date =
  doms_year +
  "-" +
  doms_month +
  "-" +
  doms_day +
  " " +
  doms_hour +
  ":" +
  doms_min +
  ":" +
  doms_sec;
let cookie_last_url = window.location.href;
let cookie_is_counsel = "N";

let doms_cam_target = "ALL";
let doms_tg1_sub = "TAR1_ALL";
let cookie_except_ca_no = "";
let arrReviewList = new Array();
let arrLimitList = new Array();
let arrPurposeList = new Array();
let con_doms_referer = "";
let con_keyword = "";

// 케이블 모바일 만 사용
let linePopupHeight = 0;
let lineHeadHeight = 45;
let linecontainerHeight = 168;

if (
  /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
    navigator.userAgent
  )
) {
  doms_is_mobile = true;
}

if (window.location.href.includes("/cable/Main.do") && doms_is_mobile == true) {
  linecontainerHeight = 143;
}

let scrollSwiper = "";

document.addEventListener("DOMContentLoaded", function () {
  if (location.href.includes("popup=y")) {
    return false;
  }

  doms_open = true;
  doms_loading_time = 0;
  doms_exp_time = 0;

  // 쿠키 셋팅
  settingCookie();
  // 현 세션에서 방문 수
  settingVisitCnt();
  // 몇일간 보이지 않기 셋팅
  resetReviewList();
  // 클릭 회수 제한
  resetLimitList();
  // 7일간 동일 링크 체크
  settingDomainCnt();
  // 동일 세션내 목표 전환 체크
  chkPurposeVisit();

  // 특정 경로로 유입한 고객
  if (doms_referer != "" && doms_referer.includes(doms_domain) == false) {
    doms_cam_target = doms_cam_target + ",FIXED";
    doms_tg1_sub = doms_tg1_sub + ",OUTLINK";
    doms_tg1_sub = doms_tg1_sub + ",FIXEDWORD";
    con_doms_referer = doms_referer;

    con_keyword =
      getKeywordFromUrl(doms_referer) + getKeywordFromUrl(window.location.href);

    //con_keyword = doms_referer + window.location.href
    con_doms_referer = window.location.href;
  } else {
    con_doms_referer = doms_referer;
    con_keyword = "";
  }

  //console.log(con_keyword);
  //decodeURIComponent

  // 제외 번호 중복 작업
  const arr_cookie_except_ca_no = cookie_except_ca_no.split(",");

  let dup_cookie_except_ca_no = [...new Set(arr_cookie_except_ca_no)];

  let doms_device_type = "P";
  if (doms_is_mobile == true) doms_device_type = "M";

  const doms_condition = {
    // API 인증을 위한 보안 키
    DOMS_KEY: "P3QerWZfzEZoK66PesI072xbuQGYoKWi", // 환경에 따라 적절한 키 사용

    // 대상 고객 세그먼트(쉼표로 구분)
    // 옵션: ALL(전체), NEW(신규), RETURN(재방문), SEARCH(탐색 많음), FIXED(특정 경로)
    CAM_TARGET: doms_cam_target,

    // 현재 페이지 URL
    THIS_URL: window.location.href,

    // 기기 유형: "P"는 PC, "M"은 모바일
    DEVICE_TYPE: "P",

    // 하위 타겟팅 매개변수(쉼표로 구분)
    // 옵션: TAR1_ALL(전체), TAR1_FIXED(특정 페이지 관심), TAR1_EVERY(자주 방문), TAR1_ONLINE(상담 신청), TAR1_NONE 등
    TG1_SUB: doms_tg1_sub,
    REFER_URL: con_doms_referer,
    KEYWORD: con_keyword,

    // 제외할 캠페인 번호(쉼표로 구분)
    EXCEPT_CA_NO: "",
  };
  doms_ca_no = "";
  doms_url = "144.24.85.101:7080";
  if (doms_ca_no !== "") {
    if (doms_url === "localhost") {
      previewDomsPopup2(doms_ca_no);
    } else {
      previewDomsPopup(doms_ca_no);
    }
  } else {
    if (doms_url === "localhost") {
      matchDomsPopup(doms_condition);
    } else {
      matchDomsPopup(doms_condition);
    }
  }

  setInterval(() => ++doms_loading_time, 1000);
});

let start_exp_time = function () {
  doms_exp_time = 0;
  var doms_exp_timer = setInterval(function () {
    doms_exp_time++;
  }, 1000);
};

const resetReviewList = () => {
  if (localStorage.getItem("DOMS_REVIEW_LIST")) {
    const data = JSON.parse(localStorage.getItem("DOMS_REVIEW_LIST"));

    arrReviewList.length = 0;

    // 기간이 지난 정보 삭제
    for (var i = 0; i < data.length; i++) {
      //console.log(cookie_last_date);
      //console.log(data[i][2]);
      if (cookie_last_date < data[i][2]) {
        arrReviewList.push([data[i][0], parseInt(data[i][1]), data[i][2]]);
        if (cookie_except_ca_no == "") {
          cookie_except_ca_no = data[i][0];
        } else {
          cookie_except_ca_no = cookie_except_ca_no + "," + data[i][0];
        }
        //console.log("cookie_except_ca_no : "+cookie_except_ca_no);
      }
    }

    localStorage.DOMS_REVIEW_LIST = JSON.stringify(arrReviewList);
  }
};

const resetLimitList = () => {
  if (localStorage.getItem("DOMS_LIMIT_CNT")) {
    const data = JSON.parse(localStorage.getItem("DOMS_LIMIT_CNT"));

    // 기간이 지난 정보 삭제
    for (var i = 0; i < data.length; i++) {
      if (parseInt(data[i][1]) <= parseInt(data[i][2])) {
        if (cookie_except_ca_no == "") {
          cookie_except_ca_no = data[i][0];
        } else {
          cookie_except_ca_no = cookie_except_ca_no + "," + data[i][0];
        }
        //console.log("cookie_except_ca_no : "+cookie_except_ca_no);
      }
    }
  }
};

const addLimitList = (caNo, limitCnt, displayCnt, expiredDate) => {
  if (!localStorage.getItem("DOMS_LIMIT_CNT")) {
    arrLimitList.push([caNo, parseInt(limitCnt), displayCnt, expiredDate]);

    localStorage.DOMS_LIMIT_CNT = JSON.stringify(arrLimitList);
  } else {
    const data = JSON.parse(localStorage.getItem("DOMS_LIMIT_CNT"));
    let isExsist = false;

    arrLimitList.length = 0;

    // 기간이 지난 정보 수정
    for (var i = 0; i < data.length; i++) {
      if (cookie_last_date < data[i][3]) {
        if (caNo == data[i][0]) {
          // 동일 번호가 있다면
          isExsist = true;
          arrLimitList.push([
            data[i][0],
            parseInt(data[i][1]),
            parseInt(data[i][2]) + 1,
            expiredDate,
          ]);
        } else {
          arrLimitList.push([
            data[i][0],
            parseInt(data[i][1]),
            data[i][2],
            data[i][3],
          ]);
        }
      }
    }

    if (isExsist == false)
      arrLimitList.push([caNo, parseInt(limitCnt), displayCnt, expiredDate]);

    localStorage.DOMS_LIMIT_CNT = JSON.stringify(arrLimitList);
  }
};

const addPurposeList = (caNo, caPurpose, caPurposeUrl, msgType) => {
  if (
    sessionStorage.getItem("s_purpose_list") == null ||
    sessionStorage.getItem("s_purpose_list") == ""
  ) {
    arrPurposeList.push([caNo, caPurpose, caPurposeUrl, msgType]);

    sessionStorage.setItem("s_purpose_list", JSON.stringify(arrPurposeList));
  } else {
    const data = JSON.parse(sessionStorage.getItem("s_purpose_list"));
    let isExsist = false;

    arrPurposeList.length = 0;
    // 기간이 지난 정보 수정
    for (var i = 0; i < data.length; i++) {
      if (caNo == data[i][0]) {
        // 동일 번호가 있다면
        isExsist = true;
      } else {
        arrPurposeList.push([data[i][0], data[i][1], data[i][2], data[i][3]]);
      }
    }

    if (isExsist == false)
      arrPurposeList.push([caNo, caPurpose, caPurposeUrl, msgType]);

    sessionStorage.setItem("s_purpose_list", JSON.stringify(arrPurposeList));
  }
};

const settingCookie = () => {
  if (!localStorage.getItem("DOMS_COOKIE")) {
    sessionStorage.setItem("s_cookie_cnt", cookie_cnt);

    const strCookieInfo = {
      cookie_id: cookie_id,
      cookie_cnt: cookie_cnt,
      cookie_create_date: cookie_create_date,
      cookie_last_date: cookie_last_date,
      cookie_last_url: cookie_last_url,
      cookie_is_counsel: cookie_is_counsel,
    };

    localStorage.DOMS_COOKIE = JSON.stringify(strCookieInfo);

    doms_cam_target = doms_cam_target + ",NEW";
  } else {
    const data = JSON.parse(localStorage.getItem("DOMS_COOKIE"));

    cookie_id = data.cookie_id;

    if (
      sessionStorage.getItem("s_cookie_cnt") == null ||
      sessionStorage.getItem("s_cookie_cnt") == ""
    ) {
      cookie_cnt = parseInt(data.cookie_cnt) + 1;
      sessionStorage.setItem("s_cookie_cnt", cookie_cnt);
    } else {
      cookie_cnt = parseInt(data.cookie_cnt);
    }

    cookie_create_date = data.cookie_create_date;
    cookie_last_date =
      doms_year +
      "-" +
      doms_month +
      "-" +
      doms_day +
      " " +
      doms_hour +
      ":" +
      doms_min +
      ":" +
      doms_sec;
    cookie_last_url = window.location.href;

    if (cookie_last_url.includes("cs/done") == true) {
      cookie_is_counsel = "Y";
    } else {
      cookie_is_counsel = data.cookie_is_counsel;
    }

    const strCookieInfo = {
      cookie_id: cookie_id,
      cookie_cnt: cookie_cnt,
      cookie_create_date: cookie_create_date,
      cookie_last_date: cookie_last_date,
      cookie_last_url: cookie_last_url,
      cookie_is_counsel: cookie_is_counsel,
    };

    localStorage.DOMS_COOKIE = JSON.stringify(strCookieInfo);

    doms_cam_target = doms_cam_target + ",RETURN";

    if (cookie_is_counsel == "Y") {
      doms_tg1_sub = doms_tg1_sub + ",TAR1_ONLINE";
    } else {
      doms_tg1_sub = doms_tg1_sub + ",TAR1_NONE";
    }
  }
};

const settingVisitCnt = () => {
  if (
    sessionStorage.getItem("s_visit_cnt") == null ||
    sessionStorage.getItem("s_visit_cnt") == ""
  ) {
    sessionStorage.setItem("s_visit_cnt", 1);
  } else {
    sessionStorage.setItem(
      "s_visit_cnt",
      parseInt(sessionStorage.getItem("s_visit_cnt")) + 1
    );
    if (parseInt(sessionStorage.getItem("s_visit_cnt")) > 3)
      doms_cam_target = doms_cam_target + ",SEARCH";
  }
};

const chkPurposeVisit = () => {
  if (
    sessionStorage.getItem("s_purpose_list") == null ||
    sessionStorage.getItem("s_purpose_list") == ""
  ) {
    // 아무것도 하지 않음
  } else {
    const data = JSON.parse(sessionStorage.getItem("s_purpose_list"));

    // 기간이 지난 정보 수정
    for (var i = 0; i < data.length; i++) {
      if (window.location.origin + window.location.pathname == data[i][2]) {
        // 목표 전환 남기기
        saveDomsLog("VISIT", data[i][0], data[i][3], "", "", "");
      } else {
        arrPurposeList.push([data[i][0], data[i][1], data[i][2], data[i][3]]);
      }
    }
    sessionStorage.setItem("s_purpose_list", JSON.stringify(arrPurposeList));
  }
};

// 도메인 배열
let arrDomainList = new Array();

const settingDomainCnt = () => {
  if (
    localStorage.getItem("DOMS_DOMAIN") == null ||
    localStorage.getItem("DOMS_DOMAIN") == ""
  ) {
    arrDomainList.push([window.location.href, 1, doms7DateLater]);
    localStorage.DOMS_DOMAIN = JSON.stringify(arrDomainList);
  } else {
    const data = JSON.parse(localStorage.getItem("DOMS_DOMAIN"));

    //배열 초기화
    arrDomainList.length = 0;
    let isExsist = false;
    let cntExsistDomain = 0;
    let cntExsistVisit = 0;

    // 기간이 지난 정보 수정
    for (var i = 0; i < data.length; i++) {
      if (cookie_last_date < data[i][2]) {
        arrDomainList.push([data[i][0], parseInt(data[i][1]), data[i][2]]);
      }
    }

    arrDomainList.push([window.location.href, 1, doms7DateLater]);
    localStorage.DOMS_DOMAIN = JSON.stringify(arrDomainList);

    const data_domain = JSON.parse(localStorage.getItem("DOMS_DOMAIN"));
    for (var i = 0; i < data_domain.length; i++) {
      cntExsistVisit = cntExsistVisit + 1;
      if (data_domain[i][0] == window.location.href) {
        cntExsistDomain = cntExsistDomain + 1;
      }
    }

    // 최근에 자주 방문한 고객 (7일동안 3번 이상 접속한 고객)
    if (cntExsistVisit > 3) doms_tg1_sub = doms_tg1_sub + ",TAR1_EVERY";

    // 특정 페이지에 관심이 많은 고객 (7일동안 특정 페이지를 3번 이상 접속한 고객)
    if (cntExsistDomain > 3) doms_tg1_sub = doms_tg1_sub + ",TAR1_FIXED";
  }

  //return domain7date;
};

const previewDomsPopup = (doms_ca_no) => {
  var formData = new FormData();
  formData.append("DOMS_KEY", doms_securekey);
  formData.append("CA_NO", "1");

  fetch(`http://${doms_url}/api/v1/preview`, {
    method: "POST",
	headers: {
        'Accept': 'application/json',
		'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData,
  })
    .then((response) => response.json())
    .then((result) => {
      let multiList = "";
      let resultData = "";

      if (typeof result.data.campaign !== "undefined") {
        resultData = result.data.campaign;

        if (typeof result.data.multiList !== "undefined") {
          multiList = result.data.multiList;
        }
      } else {
        resultData = result.data;
      }

      console.log(result.data);
      //console.log(result.data.CA_MSG_TYPE);

      let preview = true;

      let strPopup = makePopupHtml(resultData, preview, multiList);

      let container = document.getElementsByTagName("body")[0];

      if (resultData.CA_MSG_TYPE == "6") {
        if (
          window.location.href.includes("bdirectshop.com") &&
          doms_is_mobile == true
        ) {
          container = document.getElementById("Document");
        } else {
          container = document.getElementById("page");
        }

        container.prepend(stringToHTML(strPopup));
      } else {
        container.append(stringToHTML(strPopup));
      }

      domsPopupOpen(resultData.CA_MSG_TYPE);

      if (resultData.CA_MSG_TYPE === "8") {
        scrollSwiper = initSwiper();
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  //   var request = $.ajax({
  //     url: `//${doms_url}/api/v1/preview`,
  //     type: "POST",
  //     data: { DOMS_KEY: doms_securekey, CA_NO: doms_ca_no },
  //     dataType: "json",
  //   });

  //   request.done(function (result) {
  //     let multiList = "";
  //     let resultData = "";

  //     if (typeof result.data.campaign !== "undefined") {
  //       resultData = result.data.campaign;

  //       if (typeof result.data.multiList !== "undefined") {
  //         multiList = result.data.multiList;
  //       }
  //     } else {
  //       resultData = result.data;
  //     }

  //     console.log(result.data);
  //     //console.log(result.data.CA_MSG_TYPE);

  //     let preview = true;

  //     let strPopup = makePopupHtml(resultData, preview, multiList);

  //     let container = document.getElementsByTagName("body")[0];

  //     if (resultData.CA_MSG_TYPE == "6") {
  //       if (
  //         window.location.href.includes("bdirectshop.com") &&
  //         doms_is_mobile == true
  //       ) {
  //         container = document.getElementById("Document");
  //       } else {
  //         container = document.getElementById("page");
  //       }

  //       container.prepend(stringToHTML(strPopup));
  //     } else {
  //       container.append(stringToHTML(strPopup));
  //     }

  //     domsPopupOpen(resultData.CA_MSG_TYPE);

  //     if (resultData.CA_MSG_TYPE === "8") {
  //       scrollSwiper = initSwiper();
  //     }
  //   });

  //   request.fail(function (jqXHR, textStatus) {
  //     console.log(jqXHR.responseText);
  //     return false;
  //   });
};

const matchDomsPopup = (doms_condition) => {
  // 전환 페이지에서 view count 가 한번 더 잡히기 때문에 예외 처리
  if (location.pathname === "/shop/com/cs/done.do") {
    return false;
  }



  var formData = new FormData();
  formData.append("DOMS_KEY", doms_condition.DOMS_KEY);
  formData.append("CAM_TARGET", doms_condition.CAM_TARGET);
  formData.append("THIS_URL", doms_condition.THIS_URL);
  formData.append("DEVICE_TYPE", doms_condition.DEVICE_TYPE);
  formData.append("TG1_SUB", doms_condition.TG1_SUB);
  formData.append("REFER_URL", doms_condition.REFER_URL);
  formData.append("KEYWORD", doms_condition.KEYWORD);
  formData.append("EXCEPT_CA_NO", doms_condition.EXCEPT_CA_NO);

  console.log(doms_condition);
  console.log(formData);

  fetch(`http://${doms_url}/api/v1/campaign`, {
    method: "POST",
	headers: {
        'Origin': 'bdirectshop'
    },

    body: formData
  })
    .then((response) => response.json())
    .then((result) => {


	  console.log(result);

      let multiList = "";
      let resultData = "";

      if (typeof result.data.campaign !== "undefined") {
        resultData = result.data.campaign;

        if (typeof result.data.multiList !== "undefined") {
          multiList = result.data.multiList;
        }
      } else {
        resultData = result.data;
      }

      if (resultData == "undefined" || resultData == null || resultData == "") {
        // 리턴 되는 DOMS 없음
      } else {



        let preview = false;
        let strPopup = makePopupHtml(resultData[0], preview, multiList);

        let container = document.getElementsByTagName("body")[0];

        if (resultData[0].CA_MSG_TYPE == "6") {
          if (
            window.location.href.includes("bdirectshop.com") &&
            doms_is_mobile == true
          ) {
            container = document.getElementById("Document");
          } else {
            container = document.getElementById("page");
          }

          container.prepend(stringToHTML(strPopup));
        } else {
          container.append(stringToHTML(strPopup));
        }

        if (resultData[0].CA_MSG_TYPE === "8") {
          scrollSwiper = initSwiper();
        }

        // console.log(resultData[0]);

        // AT_ONCE 즉시
        // PERCENT 노출 페이지 스크롤
        // LOADING N 초 후
        // NONE_MOVE 초 후
        // BEFORE_OUT 이탈

        if (resultData[0].CA_VIEW_POINT == "AT_ONCE") {

          domsPopupOpen(resultData[0].CA_MSG_TYPE);

          start_exp_time();
          activateLink();

          saveDomsLog(
            "SHOW",
            resultData[0].CA_NO,
            resultData[0].CA_MSG_TYPE,
            resultData[0].EXPOSURE_LIMIT_CNT,
            resultData[0].CA_PURPOSE,
            resultData[0].CA_PURPOSE_URL
          );
        } else if (resultData[0].CA_VIEW_POINT == "PERCENT") {
          let viewPointPercentage = parseInt(resultData[0].VIEW_POINT_SUB);

          setInterval(function () {
            let scrollTo = $(window).scrollTop(),
              docHeight = $(document).height(),
              windowHeight = $(window).height(),
              scrollPercent = (scrollTo / (docHeight - windowHeight)) * 100;

            if (scrollPercent > viewPointPercentage) {
              if (doms_open == true) {
                domsPopupOpen(resultData[0].CA_MSG_TYPE);
                start_exp_time();
                activateLink();
                saveDomsLog(
                  "SHOW",
                  resultData[0].CA_NO,
                  resultData[0].CA_MSG_TYPE,
                  resultData[0].EXPOSURE_LIMIT_CNT,
                  resultData[0].CA_PURPOSE,
                  resultData[0].CA_PURPOSE_URL
                );
                doms_open = false;
              }
            }
          }, 300);
        } else if (resultData[0].CA_VIEW_POINT == "LOADING") {
          setTimeout(() => {
            domsPopupOpen(resultData[0].CA_MSG_TYPE);
            start_exp_time();
            activateLink();
            saveDomsLog(
              "SHOW",
              resultData[0].CA_NO,
              resultData[0].CA_MSG_TYPE,
              resultData[0].EXPOSURE_LIMIT_CNT,
              resultData[0].CA_PURPOSE,
              resultData[0].CA_PURPOSE_URL
            );
          }, parseInt(resultData[0].VIEW_POINT_SUB) * 1000);
        } else if (resultData[0].CA_VIEW_POINT == "NONE_MOVE") {
          setInterval(function () {
            // console.log(doms_loading_time);

            if (doms_loading_time > parseInt(resultData[0].VIEW_POINT_SUB)) {
              if (doms_open == true) {
                domsPopupOpen(resultData[0].CA_MSG_TYPE);
                start_exp_time();
                activateLink();
                saveDomsLog(
                  "SHOW",
                  resultData[0].CA_NO,
                  resultData[0].CA_MSG_TYPE,
                  resultData[0].EXPOSURE_LIMIT_CNT,
                  resultData[0].CA_PURPOSE,
                  resultData[0].CA_PURPOSE_URL
                );
                doms_open = false;
              }
            }
          }, 1000);
        } else if (resultData[0].CA_VIEW_POINT == "BEFORE_OUT") {
          if (resultData[0].VIEW_POINT_SUB == "OTHER_PAGE") {
            let click_url = "";

            // $(document).on("click", ".dom-header a, .page-head a", function() {
            $(document).on("click", "#wrap a", function () {
              if (doms_open == true) {
                domsPopupOpen(resultData[0].CA_MSG_TYPE);
                start_exp_time();
                activateLink();
                saveDomsLog(
                  "SHOW",
                  resultData[0].CA_NO,
                  resultData[0].CA_MSG_TYPE,
                  resultData[0].EXPOSURE_LIMIT_CNT,
                  resultData[0].CA_PURPOSE,
                  resultData[0].CA_PURPOSE_URL
                );
                doms_open = false;

                click_url = $(this).attr("href");
                $(this).attr("href", "javascript:void(0)");
              } else {
                if ($(this).attr("href") == "javascript:void(0)") {
                  $(this).attr("href", click_url);
                }
              }
            });
          }

          if (resultData[0].VIEW_POINT_SUB == "BACK_BTN") {
            let is_head = false;
            // $(document).on("click", ".dom-header a, .page-head a", function() {
            $(document).on("click", "#wrap a", function () {
              is_head = true;
            });

            window.addEventListener(
              "popstate",
              function (event) {
                setTimeout(() => {
                  if (doms_open == true && is_head == false) {
                    domsPopupOpen(resultData[0].CA_MSG_TYPE);
                    start_exp_time();
                    activateLink();
                    saveDomsLog(
                      "SHOW",
                      resultData[0].CA_NO,
                      resultData[0].CA_MSG_TYPE,
                      resultData[0].EXPOSURE_LIMIT_CNT,
                      resultData[0].CA_PURPOSE,
                      resultData[0].CA_PURPOSE_URL
                    );
                    doms_open = false;
                  } else {
                    if (is_head == false)
                      history.pushState(null, null, window.location.pathname);
                  }
                }, 500);
              },
              false
            );
          }
        }
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });

////////////////////////////


  // var request = $.ajax({
    // //url: `//${doms_url}/api/v1/campaign`,
	// url: `http://${doms_url}/api/v1/campaign`,
    // type: "POST",
    // data: {
      // DOMS_KEY: doms_condition.DOMS_KEY,
      // CAM_TARGET: doms_condition.CAM_TARGET,
      // THIS_URL: doms_condition.THIS_URL,
      // DEVICE_TYPE: doms_condition.DEVICE_TYPE,
      // TG1_SUB: doms_condition.TG1_SUB,
      // REFER_URL: doms_condition.REFER_URL,
      // KEYWORD: doms_condition.KEYWORD,
      // EXCEPT_CA_NO: doms_condition.EXCEPT_CA_NO,
    // },
    // dataType: "json",
  // });
  // request.done(function (result) {
    // let multiList = "";
    // let resultData = "";

    // if (typeof result.data.campaign !== "undefined") {
      // resultData = result.data.campaign;

      // if (typeof result.data.multiList !== "undefined") {
        // multiList = result.data.multiList;
      // }
    // } else {
      // resultData = result.data;
    // }

    // if (resultData == "undefined" || resultData == null || resultData == "") {
      // // 리턴 되는 DOMS 없음
    // } else {
      // let preview = false;
      // let strPopup = makePopupHtml(resultData[0], preview, multiList);

      // let container = document.getElementsByTagName("body")[0];

      // if (resultData[0].CA_MSG_TYPE == "6") {
        // if (
          // window.location.href.includes("bdirectshop.com") &&
          // doms_is_mobile == true
        // ) {
          // container = document.getElementById("Document");
        // } else {
          // container = document.getElementById("page");
        // }

        // container.prepend(stringToHTML(strPopup));
      // } else {
        // container.append(stringToHTML(strPopup));
      // }

      // if (resultData[0].CA_MSG_TYPE === "8") {
        // scrollSwiper = initSwiper();
      // }

      // //console.log(resultData[0]);

      // // AT_ONCE 즉시
      // // PERCENT 노출 페이지 스크롤
      // // LOADING N 초 후
      // // NONE_MOVE 초 후
      // // BEFORE_OUT 이탈

      // if (resultData[0].CA_VIEW_POINT == "AT_ONCE") {
        // domsPopupOpen(resultData[0].CA_MSG_TYPE);
        // start_exp_time();
        // activateLink();

        // saveDomsLog(
          // "SHOW",
          // resultData[0].CA_NO,
          // resultData[0].CA_MSG_TYPE,
          // resultData[0].EXPOSURE_LIMIT_CNT,
          // resultData[0].CA_PURPOSE,
          // resultData[0].CA_PURPOSE_URL
        // );
      // } else if (resultData[0].CA_VIEW_POINT == "PERCENT") {
        // let viewPointPercentage = parseInt(resultData[0].VIEW_POINT_SUB);

        // setInterval(function () {
          // let scrollTo = $(window).scrollTop(),
            // docHeight = $(document).height(),
            // windowHeight = $(window).height(),
            // scrollPercent = (scrollTo / (docHeight - windowHeight)) * 100;

          // if (scrollPercent > viewPointPercentage) {
            // if (doms_open == true) {
              // domsPopupOpen(resultData[0].CA_MSG_TYPE);
              // start_exp_time();
              // activateLink();
              // saveDomsLog(
                // "SHOW",
                // resultData[0].CA_NO,
                // resultData[0].CA_MSG_TYPE,
                // resultData[0].EXPOSURE_LIMIT_CNT,
                // resultData[0].CA_PURPOSE,
                // resultData[0].CA_PURPOSE_URL
              // );
              // doms_open = false;
            // }
          // }
        // }, 300);
      // } else if (resultData[0].CA_VIEW_POINT == "LOADING") {
        // setTimeout(() => {
          // domsPopupOpen(resultData[0].CA_MSG_TYPE);
          // start_exp_time();
          // activateLink();
          // saveDomsLog(
            // "SHOW",
            // resultData[0].CA_NO,
            // resultData[0].CA_MSG_TYPE,
            // resultData[0].EXPOSURE_LIMIT_CNT,
            // resultData[0].CA_PURPOSE,
            // resultData[0].CA_PURPOSE_URL
          // );
        // }, parseInt(resultData[0].VIEW_POINT_SUB) * 1000);
      // } else if (resultData[0].CA_VIEW_POINT == "NONE_MOVE") {
        // setInterval(function () {
          // //console.log(doms_loading_time);

          // if (doms_loading_time > parseInt(resultData[0].VIEW_POINT_SUB)) {
            // if (doms_open == true) {
              // domsPopupOpen(resultData[0].CA_MSG_TYPE);
              // start_exp_time();
              // activateLink();
              // saveDomsLog(
                // "SHOW",
                // resultData[0].CA_NO,
                // resultData[0].CA_MSG_TYPE,
                // resultData[0].EXPOSURE_LIMIT_CNT,
                // resultData[0].CA_PURPOSE,
                // resultData[0].CA_PURPOSE_URL
              // );
              // doms_open = false;
            // }
          // }
        // }, 1000);
      // } else if (resultData[0].CA_VIEW_POINT == "BEFORE_OUT") {
        // if (resultData[0].VIEW_POINT_SUB == "OTHER_PAGE") {
          // let click_url = "";

          // //$(document).on("click", ".dom-header a, .page-head a", function() {
          // $(document).on("click", "#wrap a", function () {
            // if (doms_open == true) {
              // domsPopupOpen(resultData[0].CA_MSG_TYPE);
              // start_exp_time();
              // activateLink();
              // saveDomsLog(
                // "SHOW",
                // resultData[0].CA_NO,
                // resultData[0].CA_MSG_TYPE,
                // resultData[0].EXPOSURE_LIMIT_CNT,
                // resultData[0].CA_PURPOSE,
                // resultData[0].CA_PURPOSE_URL
              // );
              // doms_open = false;

              // click_url = $(this).attr("href");
              // $(this).attr("href", "javascript:void(0)");
            // } else {
              // if ($(this).attr("href") == "javascript:void(0)") {
                // $(this).attr("href", click_url);
              // }
            // }
          // });
        // }

        // if (resultData[0].VIEW_POINT_SUB == "BACK_BTN") {
          // let is_head = false;
          // //$(document).on("click", ".dom-header a, .page-head a", function() {
          // $(document).on("click", "#wrap a", function () {
            // is_head = true;
          // });

          // window.addEventListener(
            // "popstate",
            // function (event) {
              // setTimeout(() => {
                // if (doms_open == true && is_head == false) {
                  // domsPopupOpen(resultData[0].CA_MSG_TYPE);
                  // start_exp_time();
                  // activateLink();
                  // saveDomsLog(
                    // "SHOW",
                    // resultData[0].CA_NO,
                    // resultData[0].CA_MSG_TYPE,
                    // resultData[0].EXPOSURE_LIMIT_CNT,
                    // resultData[0].CA_PURPOSE,
                    // resultData[0].CA_PURPOSE_URL
                  // );
                  // doms_open = false;
                // } else {
                  // if (is_head == false)
                    // history.pushState(null, null, window.location.pathname);
                // }
              // }, 500);
            // },
            // false
          // );
        // }
      // }
    // }

    // console.log(resultData);
  // });

  // request.fail(function (jqXHR, textStatus) {
    // console.log(jqXHR.responseText);
    // return false;
  // });
};

let activateLink = function () {
  // 미리보기에서는 저장 안하지만 테스트 데이터 수집 목적으로 사용
  let linkClickClass = document.querySelectorAll(".doms_click");

  linkClickClass.forEach((target) =>
    target.addEventListener("click", function () {
      saveDomsLog(
        this.getAttribute("data-action"),
        this.getAttribute("data-caNo"),
        this.getAttribute("data-msgType"),
        this.getAttribute("data-limitCnt"),
        "",
        ""
      );

      if (
        this.getAttribute("data-action") == "CONTACT" ||
        this.getAttribute("data-action") == "LINKCLICK" ||
        this.getAttribute("data-action") == "SIGNUP"
      ) {
        // 최대 노출 설정 (누루때 까지)
        if (this.getAttribute("data-limitCnt") == "CLICK") {
          // 최대 노출이 CLICK 인 경우 0 data-limitCnt 0 으로 지정
          addLimitList(this.getAttribute("data-caNo"), 0, 0, doms100DateLater);
        }

        domsPopupClose(
          this.getAttribute("data-msgType"),
          this.getAttribute("data-action")
        );
      }
    })
  );
};

const saveDomsLog = (
  action,
  doms_ca_no,
  msg_type,
  limit_cnt,
  ca_purpose,
  ca_purpose_url,
  multi_no = 0
) => {
  if (msg_type === "8") {
    if (action !== "SHOW" && action !== "CLOSECLICK" && action !== "VISIT") {
      multi_no =
        document
          .querySelector(".slide-item.swiper-slide.swiper-slide-active")
          .getAttribute("data-multi-no") ?? 0;
    }
  }

  let device_type = "P";
  if (doms_is_mobile == true) device_type = "M";

  var formData = new FormData();

  formData.append("DOMS_KEY", "P3QerWZfzEZoK66PesI072xbuQGYoKWi"); // 보안 키 (API 인증용)
  formData.append("CA_NO", doms_ca_no); // 캠페인 번호
  formData.append("DEVICE_TYPE", "P"); // 기기 유형 (P: PC, M: 모바일)
  formData.append("EVENT_TYPE", action); // 이벤트 타입 (SHOW, CLOSECLICK, LINKCLICK 등)
  formData.append("MSG_TYPE", msg_type); // 메시지 타입 (1~8: 팝업 유형)
  formData.append("CA_REFERER", doms_referer); // 참조 URL (유입 경로)
  formData.append("COOKIE_CNT", cookie_cnt); // 쿠키 카운트 (방문 횟수)
  formData.append("EXP_TIME", doms_exp_time); // 노출 시간 (초 단위)
  formData.append("CA_SESSION_ID", session_id); // 세션 ID
  formData.append("CA_COOKIE_ID", cookie_id); // 쿠키 ID (고유 식별자)
  formData.append("CA_FULL_URL", window.location.href); // 현재 페이지 전체 URL
  formData.append("CA_TITLE", document.title); // 현재 페이지 제목
  formData.append("CA_IP", doms_remote_ip); // 사용자 IP 주소
  formData.append("CA_MENU_NO", skb_ca_menu_no); // 메뉴 번호
  formData.append("CA_C_CODE", skb_ca_c_code); // 캠페인 코드
  formData.append("MULTI_NO", multi_no); // 멀티 슬라이드 번호 (캐러셀용)

  fetch(`http://${doms_url}/api/v1/log`, {
    method: "POST",
    body: formData,
  })
    .then((response) => response.json())
    .then((result) => {
      if (limit_cnt != "UNLIMIT" && limit_cnt != "CLICK" && action == "SHOW") {
        addLimitList(doms_ca_no, parseInt(limit_cnt), 1, doms100DateLater);
      }

      // CA_PURPOSE value (NONE / ONLINE / ETC)
      // CA_PURPOSE_URL
      if (ca_purpose == "ONLINE" && action == "SHOW") {
        if (device_type == "P")
          ca_purpose_url = location.origin + "/shop/com/cs/done.do";
        if (device_type == "M")
          ca_purpose_url = location.origin + "/shop/com/cs/done.do";

        addPurposeList(doms_ca_no, ca_purpose, ca_purpose_url, msg_type);
      }

      if (ca_purpose == "ETC" && action == "SHOW") {
        addPurposeList(doms_ca_no, ca_purpose, ca_purpose_url, msg_type);
      }
      //addPurposeList

      console.log(result);
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  /*
    console.log(doms_is_mobile);
    console.log(doms_referer);
    console.log(action);
    console.log(ca_no);
    console.log(msg_type);
    console.log(limit_cnt);
    console.log(cookie_cnt);
    console.log(session_id);
    console.log(cookie_id);
    console.log(window.location.href);
    console.log(document.title);
    console.log(doms_remote_ip);
    console.log(skb_ca_menu_no);
    console.log(skb_ca_c_code);
    */
};

// 문자열을 HTML 로
let stringToHTML = function (str) {
  let dom = document.createElement("div");
  dom.innerHTML = str;
  return dom;
};

// 팝업 제작
let makePopupHtml = function (data, preview, multiList) {
  //console.log("팝업 만들기 "+data.CA_MSG_TYPE);
  let html = "";

  let dataBuiDevice = "laptop";
  let dataBuiDimmed = "false";

  if (doms_is_mobile == true) dataBuiDevice = "mobile";
  if (data.MSG_BACK_DIM_TF == "Y") dataBuiDimmed = "true";

  let landPageUrlTarget = `target="_self"`;

  if (dataBuiDevice == "laptop" && data.MSG_BUTTON_URL_TARGET_P == "N")
    landPageUrlTarget = `target="_blank"`;
  if (dataBuiDevice == "mobile" && data.MSG_BUTTON_URL_TARGET_M == "N")
    landPageUrlTarget = `target="_blank"`;

  //console.log(doms_is_mobile);

  // a: 효과없음, b: 페이드인, c: 나타나기, d: 흔들림
  let efftect = data.MSG_ACTION_EFFECT_TYPE ?? "a";

  let addPdding = "";

  if (dataBuiDevice == "mobile") {
    addPdding = "padding-bottom:29vw;";
  }

  if (data.CA_MSG_TYPE == "1") {
    html =
      `<div class="campaign-popup module-b style-a type-a small"
                     style="${addPdding}"
                     id="campaignDefaultPopupTypeA"
                     data-campaign-toggle="campaignDefaultPopup"
                     data-campaign-dimmed="` +
      dataBuiDimmed +
      `"
                     data-campaign-device="` +
      dataBuiDevice +
      `">`;
  } else if (data.CA_MSG_TYPE == "2") {
    html =
      `<div class="campaign-popup module-b style-a type-a medium" id="campaignDefaultPopupTypeB" data-campaign-toggle="campaignDefaultPopup" data-campaign-dimmed="` +
      dataBuiDimmed +
      `" data-campaign-device="` +
      dataBuiDevice +
      `">`;
  } else if (data.CA_MSG_TYPE == "3") {
    html =
      `<div class="campaign-popup module-b style-b type-a medium"
                    style="${addPdding}"
                    id="campaignScrollPopupTypeA"
                    data-campaign-toggle="campaignScrollPopup"
                    data-campaign-dimmed="` +
      dataBuiDimmed +
      `"
                    data-campaign-device="` +
      dataBuiDevice +
      `">`;
  } else if (data.CA_MSG_TYPE == "4") {
    if (data.MSG_IMG_TF == "Y") {
      html =
        `<div class="campaign-popup module-b style-b type-c medium"
                        id="campaignScrollPopupTypeB"
                        data-campaign-toggle="campaignScrollPopup"
                        data-campaign-dimmed="` +
        dataBuiDimmed +
        `"
                        data-campaign-device="` +
        dataBuiDevice +
        `"
                        style="--campaign-profile-image: url(//${doms_url}/images/campaign/` +
        data.MSG_IMG_NM01 +
        `); ${addPdding}">`;
    } else {
      html =
        `<div class="campaign-popup module-b style-b type-c medium"
                        style="${addPdding}"
                        id="campaignScrollPopupTypeB"
                        data-campaign-toggle="campaignScrollPopup"
                        data-campaign-dimmed="` +
        dataBuiDimmed +
        `"
                        data-campaign-device="` +
        dataBuiDevice +
        `">`;
    }
  } else if (data.CA_MSG_TYPE == "5") {
    html =
      `<div style="--effect: ${efftect};" class="campaign-popup module-c style-a type-a medium" id="campaignFloatingPopupTypeA" data-campaign-toggle="campaignFloatingPopup" data-campaign-dimmed="` +
      dataBuiDimmed +
      `" data-campaign-device="` +
      dataBuiDevice +
      `">`;
  } else if (data.CA_MSG_TYPE == "6") {
    html =
      `<div class="campaign-popup module-a style-a type-a medium" id="campaignLeadBoardTypeA" data-campaign-toggle="campaignLeadBoard" data-campaign-device="` +
      dataBuiDevice +
      `" style="--campaign-background-color:` +
      data.MSG_BAN_BG_COLOR +
      `;--campaign-unit-color:` +
      data.MSG_BAN_TEXT_COLOR +
      `;">`;
  } else if (data.CA_MSG_TYPE == "7") {
    html =
      `<div style="--effect: ${efftect}; --campaign-background-color:${data.MSG_BAN_BG_COLOR}" class="campaign-popup module-d style-a type-a medium campaign-toggle-target" id="campaignActionFloatingPopupTypeA" data-campaign-toggle="campaignActionFloatingPopup" data-campaign-dimmed="` +
      dataBuiDimmed +
      `" data-campaign-device="` +
      dataBuiDevice +
      `">`;
  } else if (data.CA_MSG_TYPE == "8") {
    html =
      `<div style="--effect: ${efftect}; ${addPdding}"
                    class="campaign-popup module-b style-b type-a medium campaign-toggle-target"
                    id="campaignScrollPopupTypeC"
                    data-campaign-toggle="campaignScrollPopup"
                    data-campaign-dimmed="` +
      dataBuiDimmed +
      `"
                    data-campaign-device="` +
      dataBuiDevice +
      `">`;
  }

  let swiperOpenTemplate = function (msgType) {
    if (msgType !== "8") return "";

    return `
            <div class="scroll-slides swiper">
                <div class="slide-list swiper-wrapper">
        `;
  };

  let swiperCloseTemplate = function (msgType) {
    if (msgType !== "8") return "";

    return `
                </div>
            </div>
        `;
  };

  html =
    html +
    `
        <div class="popup-page-body">
            <div class="popup-local">
                <div class="popup-local-head">
                    <h2 class="popup-local-subject"><span class="popup-local-name">캠페인</span></h2>
                </div>
                    <div class="popup-local-body">
                        <div class="popup-content">
                            <div class="popup-content-body">
        ${swiperOpenTemplate(data.CA_MSG_TYPE)}`;

  if (data.CA_MSG_TYPE == "8") {
    let slideHtml = "";

    for (let i = 0; i < multiList.length; i++) {
      let multiData = multiList[i];

      landPageUrlTarget = `target="_self"`;

      if (dataBuiDevice == "laptop" && multiData.msgButtonUrlTargetP == "N")
        landPageUrlTarget = `target="_blank"`;
      if (dataBuiDevice == "mobile" && multiData.msgButtonUrlTargetM == "N")
        landPageUrlTarget = `target="_blank"`;

      console.log(multiData);

      let landPageUrl = multiData.msgButtonPcUrl;
      if (doms_is_mobile == true) landPageUrl = data.msgButtonMoUrl;

      let strGa4 =
        `data-ga4id="` +
        skb_data_ga4id_scroll +
        `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
        skb_data_ga4desc_scroll +
        `" `;

      let landingBtnHtml = "";
      if (multiData.msgButtonTf == "Y") {
        landingBtnHtml +=
          ` <a ${landPageUrlTarget} class="btn type-cta attr-move doms_click" style="background-color:` +
          multiData.msgButtonBgColor +
          `;border-color:` +
          multiData.msgButtonBgColor +
          `" href="` +
          landPageUrl +
          `" id="BDS_DOMS_action" data-action="LINKCLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" ` +
          strGa4 +
          `><span class="btn-text" style="pointer-events: none; color: ${multiData.msgButtonColor};">` +
          multiData.msgButton +
          `</span></a>`;
      }

      let leaveBtnHtml = "";
      if (data.MSG_LEAVE_TF == "Y") {
        if (
          window.location.href.includes("btvcable.shop") &&
          dataBuiDevice == "mobile"
        ) {
          leaveBtnHtml +=
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onClick="consultNewRequestPopup.active('consultNewRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else if (
          window.location.href.includes("bworld.co.kr") &&
          dataBuiDevice == "mobile"
        ) {
          leaveBtnHtml +=
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest', getTelephoneNumber); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else {
          leaveBtnHtml +=
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        }
      }

      let registBtnHtml = "";
      if (data.MSG_REGIST_TF == "Y") {
        registBtnHtml +=
          ` <a class="btn type-cta attr-join doms_click" style="border-color:` +
          data.MSG_REGIST_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_REGIST_COLOR +
          `" href="` +
          skb_baro_url +
          `" data-action="SIGNUP" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" target="_blank" id="BDS_DOMS_baro"><span class="btn-text" style="color:` +
          data.MSG_REGIST_COLOR +
          `">바로가입</span></a>`;
      }

      let callBtnHtml = "";
      if (
        data.MSG_MOBILE_CALLING_TF == "Y" &&
        doms_is_mobile == true &&
        skb_chkMoblieCall !== "time_off"
      ) {
        callBtnHtml +=
          ` <a class="btn type-cta attr-call doms_click" style="border-color:` +
          data.MSG_MOBILE_CALLING_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `" href="tel:` +
          skb_tel_num +
          `" id="BDS_DOMS_phone_call" data-action="PHONECLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `"><span class="btn-text" style="color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `">가입상담 전화</span></a>`;
      }

      let imgSrc = `//${doms_url}`;

      if (doms_url === "localhost") {
        imgSrc = `http://${doms_url}`;
      }

      /*let imageBtnHtml = `
                <span class="board-cover doms_click" href="` + landPageUrl + `" id="BDS_DOMS_action" data-action="LINKCLICK"
                    data-caNo="` + data.CA_NO + `" data-msgType="` + data.CA_MSG_TYPE + `" data-limitCnt="` + data.EXPOSURE_LIMIT_CNT + `" >
                        <img src="${ imgSrc }/images/campaign/` + multiData.msgImgNm01 + `" alt="` + multiData.msgImgAlter01 + `" />
                </span>
            `;*/
      let imageBtnHtml =
        `
                <span class="board-cover">
                    <img src="${imgSrc}/images/campaign/` +
        multiData.msgImgNm01 +
        `" alt="` +
        multiData.msgImgAlter01 +
        `" />
                </span>
            `;

      let boardInformTemplate = `
                <div class="board-inform">
                    <div class="board-util">
                        <span class="button-area">
                            ${landingBtnHtml}
                            ${leaveBtnHtml}
                            ${registBtnHtml}
                            ${callBtnHtml}
                        </span>
                    </div>
                </div>
            `;

      if (
        landingBtnHtml === "" &&
        leaveBtnHtml === "" &&
        registBtnHtml === "" &&
        callBtnHtml === ""
      ) {
        boardInformTemplate = "";
      }

      slideHtml += `
                <div class="slide-item swiper-slide" data-multi-no="${multiData.multiSeq}">
                    <!-- info-board -->
                    <div class="info-board">
                        <div class="board-wrap">
                            <div class="board-figure">
                                ${imageBtnHtml}
                            </div>
                            ${boardInformTemplate}
                        </div>
                    </div>
                    <!-- //info-board -->
                </div>
            `;
    }
    html += slideHtml;
  } else {
    html += `
                                <div class="info-board">
                                    <div class="board-wrap">`;

    // 이미지 사용 1,2,3,5,6,7
    if (
      data.CA_MSG_TYPE == "1" ||
      data.CA_MSG_TYPE == "2" ||
      data.CA_MSG_TYPE == "3" ||
      data.CA_MSG_TYPE == "5" ||
      data.CA_MSG_TYPE == "6" ||
      data.CA_MSG_TYPE == "7"
    ) {
      html = html + `<div class="board-figure">`;

      // 링크 사용 6,7
      if (data.CA_MSG_TYPE == "6" || data.CA_MSG_TYPE == "7") {
        let landPageUrl = data.MSG_BUTTON_PC_URL;
        if (doms_is_mobile == true) landPageUrl = data.MSG_BUTTON_MO_URL;

        html =
          html +
          `<span class="board-cover doms_click" href="` +
          landPageUrl +
          `" id="BDS_DOMS_action" data-action="LINKCLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" ><img src="//${doms_url}/images/campaign/` +
          data.MSG_IMG_NM01 +
          `" alt="` +
          data.MSG_IMG_ALTER01 +
          `" /></span>`;
      } else {
        if (data.MSG_IMG_NM01 != "")
          html =
            html +
            `<span class="board-cover"><img src="//${doms_url}/images/campaign/` +
            data.MSG_IMG_NM01 +
            `" alt="` +
            data.MSG_IMG_ALTER01 +
            `" /></span>`;
      }

      html = html + `</div>`;
    }

    html = html + `<div class="board-inform">`;

    // 1, 2, 4, 5, 7
    if (
      data.CA_MSG_TYPE == "1" ||
      data.CA_MSG_TYPE == "2" ||
      data.CA_MSG_TYPE == "4" ||
      data.CA_MSG_TYPE == "5" ||
      data.CA_MSG_TYPE == "7"
    ) {
      html = html + ` <div class="board-head">`;

      let msgTitleColor = "";
      let msgContentsColor = "";

      if (data.MSG_TITLE_COLOR !== "")
        msgTitleColor = `style="color:` + data.MSG_TITLE_COLOR + `"`;
      if (data.MSG_CONTENTS_COLOR !== "")
        msgContentsColor = `style="color:` + data.MSG_CONTENTS_COLOR + `"`;

      if (data.MSG_TITLE_TF == "Y")
        html =
          html +
          ` <p class="board-subject"><span class="board-name" ` +
          msgTitleColor +
          `>` +
          data.MSG_TITLE +
          `</span></p>`;

      if (data.MSG_CONTENTS_TF == "Y" && data.MSG_CONTENTS != "")
        html =
          html +
          ` <p class="board-summary" ` +
          msgContentsColor +
          `>` +
          data.MSG_CONTENTS.replace(/(\r|\r\n)/g, "<br />") +
          `</p>`;

      html = html + ` </div>`;
    }

    let msgButtonTf = "";

    //if (data.MSG_BUTTON_TF == "N") msgButtonTf = `style="display:none"`;

    html =
      html +
      ` <div class="board-util">
                            <span class="button-area" ` +
      msgButtonTf +
      `>`;

    let strGa4 = "";

    // 버튼 영역 1, 2, 4번
    if (
      data.CA_MSG_TYPE == "1" ||
      data.CA_MSG_TYPE == "2" ||
      data.CA_MSG_TYPE == "4"
    ) {
      if (data.CA_MSG_TYPE == "1" || data.CA_MSG_TYPE == "2") {
        strGa4 =
          `data-ga4id="` +
          skb_data_ga4id_popup +
          `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
          skb_data_ga4desc_popup +
          `" `;
      } else {
        strGa4 =
          `data-ga4id="` +
          skb_data_ga4id_scroll +
          `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
          skb_data_ga4desc_scroll +
          `" `;
      }

      let landPageUrl = data.MSG_BUTTON_PC_URL;
      if (doms_is_mobile == true) landPageUrl = data.MSG_BUTTON_MO_URL;

      if (data.MSG_BUTTON_TF == "Y") {
        html =
          html +
          ` <a ${landPageUrlTarget} class="btn type-cta attr-move doms_click" style="background-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `;border-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `" href="` +
          landPageUrl +
          `" id="BDS_DOMS_action" data-action="LINKCLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" ` +
          strGa4 +
          `><span class="btn-text" style="pointer-events: none; color: ${data.MSG_BUTTON_COLOR}">` +
          data.MSG_BUTTON +
          `</span></a>`;
      }

      if (data.MSG_LEAVE_TF == "Y") {
        if (
          window.location.href.includes("btvcable.shop") &&
          dataBuiDevice == "mobile"
        ) {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onClick="consultNewRequestPopup.active('consultNewRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else if (
          window.location.href.includes("bworld.co.kr") &&
          dataBuiDevice == "mobile"
        ) {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest', getTelephoneNumber); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        }
      }

      if (data.MSG_REGIST_TF == "Y") {
        html =
          html +
          ` <a class="btn type-cta attr-join doms_click" style="border-color:` +
          data.MSG_REGIST_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_REGIST_COLOR +
          `" href="` +
          skb_baro_url +
          `" data-action="SIGNUP" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" target="_blank" id="BDS_DOMS_baro"><span class="btn-text" style="color:` +
          data.MSG_REGIST_COLOR +
          `">바로가입</span></a>`;
      }

      if (
        data.MSG_MOBILE_CALLING_TF == "Y" &&
        doms_is_mobile == true &&
        skb_chkMoblieCall !== "time_off"
      ) {
        html =
          html +
          ` <a class="btn type-cta attr-call doms_click" style="border-color:` +
          data.MSG_MOBILE_CALLING_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `" href="tel:` +
          skb_tel_num +
          `" id="BDS_DOMS_phone_call" data-action="PHONECLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `"><span class="btn-text" style="color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `">가입상담 전화</span></a>`;
      }
    }

    // 3번
    if (data.CA_MSG_TYPE == "3") {
      let landPageUrl = data.MSG_BUTTON_PC_URL;
      if (doms_is_mobile == true) landPageUrl = data.MSG_BUTTON_MO_URL;

      strGa4 =
        `data-ga4id="` +
        skb_data_ga4id_scroll +
        `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
        skb_data_ga4desc_scroll +
        `" `;

      if (data.MSG_BUTTON_TF == "Y") {
        html =
          html +
          ` <a ${landPageUrlTarget} class="btn type-cta attr-move doms_click" style="background-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `;border-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `" href="` +
          landPageUrl +
          `" id="BDS_DOMS_action" data-action="LINKCLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" ` +
          strGa4 +
          `><span class="btn-text" style="pointer-events: none;">` +
          data.MSG_BUTTON +
          `</span></a>`;
      }

      if (data.MSG_LEAVE_TF == "Y") {
        if (
          window.location.href.includes("btvcable.shop") &&
          dataBuiDevice == "mobile"
        ) {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onClick="consultNewRequestPopup.active('consultNewRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else if (
          window.location.href.includes("bworld.co.kr") &&
          dataBuiDevice == "mobile"
        ) {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest', getTelephoneNumber); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        } else {
          html =
            html +
            ` <a class="btn type-cta attr-send doms_click" style="border-color:` +
            data.MSG_LEAVE_LINE_COLOR +
            `;--campaign-button-border-color:` +
            data.MSG_LEAVE_COLOR +
            `" href="#consultationRequest" id="BDS_DOMS_contact" data-action="CONTACT" data-caNo="` +
            data.CA_NO +
            `" data-msgType="` +
            data.CA_MSG_TYPE +
            `" data-limitCnt="` +
            data.EXPOSURE_LIMIT_CNT +
            `" onclick="contentPopup.active('consultationRequest'); return false;"><span class="btn-text" style="color:` +
            data.MSG_LEAVE_COLOR +
            `">가입상담 신청</span></a>`;
        }
      }

      if (data.MSG_REGIST_TF == "Y") {
        html =
          html +
          ` <a class="btn type-cta attr-join doms_click" style="border-color:` +
          data.MSG_REGIST_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_REGIST_COLOR +
          `" href="` +
          skb_baro_url +
          `" data-action="SIGNUP" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" target="_blank" id="BDS_DOMS_baro"><span class="btn-text" style="color:` +
          data.MSG_REGIST_COLOR +
          `">바로가입</span></a>`;
      }

      if (
        data.MSG_MOBILE_CALLING_TF == "Y" &&
        doms_is_mobile == true &&
        skb_chkMoblieCall !== "time_off"
      ) {
        html =
          html +
          ` <a class="btn type-cta attr-call doms_click" style="border-color:` +
          data.MSG_MOBILE_CALLING_LINE_COLOR +
          `;--campaign-button-border-color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `" href="tel:` +
          skb_tel_num +
          `" id="BDS_DOMS_phone_call" data-action="PHONECLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `"><span class="btn-text" style="color:` +
          data.MSG_MOBILE_CALLING_COLOR +
          `">가입상담 전화</span></a>`;
      }
    }

    // 5, 6번
    if (
      data.CA_MSG_TYPE == "5" ||
      data.CA_MSG_TYPE == "6" ||
      data.CA_MSG_TYPE == "7"
    ) {
      let landPageUrl = data.MSG_BUTTON_PC_URL;
      if (doms_is_mobile == true) landPageUrl = data.MSG_BUTTON_MO_URL;

      if (data.CA_MSG_TYPE == "5") {
        strGa4 =
          `data-ga4id="` +
          skb_data_ga4id_floating +
          `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
          skb_data_ga4desc_floating +
          `" `;
      } else {
        strGa4 =
          `data-ga4id="` +
          skb_data_ga4id_lead +
          `" data-ga4="ga4" data-ga4type="more" data-ga4location="all" data-ga4desc="` +
          skb_data_ga4desc_lead +
          `" `;
      }
      if (data.MSG_BUTTON_TF == "Y") {
        html =
          html +
          ` <a ${landPageUrlTarget} class="btn type-goto attr-move doms_click" style="background-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `;border-color:` +
          data.MSG_BUTTON_BG_COLOR +
          `" href="` +
          landPageUrl +
          `" id="BDS_DOMS_action" data-action="LINKCLICK" data-caNo="` +
          data.CA_NO +
          `" data-msgType="` +
          data.CA_MSG_TYPE +
          `" data-limitCnt="` +
          data.EXPOSURE_LIMIT_CNT +
          `" ` +
          strGa4 +
          `><span class="btn-text" style="pointer-events: none;">랜딩 페이지로 이동</span></a>`;
      }
    }

    html =
      html +
      ` </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                ${swiperCloseTemplate(data.CA_MSG_TYPE)}`;
  }

  html += `</div></div>`;

  html =
    html +
    `
<div class="popup-local-util">`;

  // 1,2,3,4,6, 8 번 기간 동안 안보이기
  if (
    data.CA_MSG_TYPE == "1" ||
    data.CA_MSG_TYPE == "2" ||
    data.CA_MSG_TYPE == "3" ||
    data.CA_MSG_TYPE == "4" ||
    data.CA_MSG_TYPE == "6" ||
    data.CA_MSG_TYPE == "8"
  ) {
    if (data.MSG_REVIEW_TF == "Y") {
      if (data.MSG_REVIEW_DAYS == null || data.MSG_REVIEW_DAYS == "")
        data.MSG_REVIEW_DAYS = 1;

      let str_msg_review = "";

      if (data.MSG_REVIEW_DAYS == 1) {
        str_msg_review = "오늘 하루 보지 않기";
      } else {
        str_msg_review = data.MSG_REVIEW_DAYS + "일 동안 보지 않기";
      }

      html =
        html +
        `
<fieldset class="submit-form">
<legend>팝업 노출 설정 서식</legend>
<div class="form-list">
<div class="form-item">
<div class="form-wrap">
<div class="form-head"><p class="form-subject"><span class="form-name">팝업 설정 선택</span></p></div>
<div class="form-body">
<div class="option-list">
<span class="option-item">
<label class="form checkbox">
<input class="form-elem" type="checkbox" data-caNo="` +
        data.CA_NO +
        `" data-msgReviewDay="` +
        data.MSG_REVIEW_DAYS +
        `" id="doms_msg_review_day"/>
<span class="form-text">` +
        str_msg_review +
        `</span>
</label>
</span>
</div>
</div>
</div>
</div>
</div>
</fieldset>`;
    }
  }

  // 액션형 플로팅이면 닫기 누를 시 오늘 하루 보지 않기 효과
  if (data.CA_MSG_TYPE == "7") {
    html +=
      `<input style="display:none;" class="form-elem" type="checkbox" data-caNo="` +
      data.CA_NO +
      `" data-msgReviewDay="1" id="doms_msg_review_day" checked/>`;
  }

  html =
    html +
    `
<div class="button-display">
<span class="button-area">
<button class="btn popup-close doms_click" type="button" data-action="CLOSECLICK" data-caNo="` +
    data.CA_NO +
    `" data-msgType="` +
    data.CA_MSG_TYPE +
    `" data-limitCnt="` +
    data.EXPOSURE_LIMIT_CNT +
    `" onclick="domsPopupClose('` +
    data.CA_MSG_TYPE +
    `', 'CLOSECLICK')">
<span class="btn-text">닫기</span>
</button>
</span>
</div>
</div>
</div>
</div>
</div>`;

  //console.log(html);

  return html;
};

let domsPopupOpen = function (msgType) {
  if (msgType == "1") {
    campaignDefaultPopup.update("campaignDefaultPopupTypeA");
    campaignDefaultPopup.active("campaignDefaultPopupTypeA");
  } else if (msgType == "2") {
    campaignDefaultPopup.update("campaignDefaultPopupTypeB");
    campaignDefaultPopup.active("campaignDefaultPopupTypeB");
  } else if (msgType == "3") {
    campaignScrollPopup.update("campaignScrollPopupTypeA");
    campaignScrollPopup.active("campaignScrollPopupTypeA");
  } else if (msgType == "4") {
    campaignScrollPopup.update("campaignScrollPopupTypeB");
    campaignScrollPopup.active("campaignScrollPopupTypeB");
  } else if (msgType == "5") {
    campaignFloatingPopup.update("campaignFloatingPopupTypeA");
    campaignFloatingPopup.active("campaignFloatingPopupTypeA");
  } else if (msgType == "6") {
    campaignLeadBoard.update("campaignLeadBoardTypeA");
    campaignLeadBoard.active("campaignLeadBoardTypeA");

    // 모바일 인경우
    if (
      window.location.href.includes("btvcable.shop") &&
      doms_is_mobile == true
    ) {
      setTimeout(() => {
        linePopupHeight = parseInt(
          document.getElementById("campaignLeadBoardTypeA").offsetHeight
        );

        if ($(".top_banner_new").css("display") == "none") {
          $(".top_banner_new").css({ top: linePopupHeight + "px" });
          $(".page-head").css({
            position: "fixed",
            top: linePopupHeight + "px",
          });
          $(".page-head ~ .page-body").css({
            "margin-top":
              linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
          });
          $("#container").css({
            "padding-top":
              linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
          });
        } else {
          $(".top_banner_new").css({ top: linePopupHeight + "px" });
          $(".page-head").css({
            position: "fixed",
            top: linePopupHeight + lineHeadHeight + "px",
          });
          $(".page-head ~ .page-body").css({
            "margin-top": linePopupHeight + linecontainerHeight + "px",
          });
          $("#container").css({
            "padding-top": linePopupHeight + linecontainerHeight + "px",
          });
        }
      }, 100);
    }

    if (
      window.location.href.includes("bdirectshop.com") &&
      doms_is_mobile == true
    ) {
      setTimeout(() => {
        linePopupHeight = parseInt(
          document.getElementById("campaignLeadBoardTypeA").offsetHeight
        );

        $("#Header").css({ margin: linePopupHeight + "px 0 0 0" });
        $("#Center").css({ margin: linePopupHeight + "px 0 0 0" });
      }, 100);
    }

    if (
      window.location.href.includes("bdirectshop.com") &&
      doms_is_mobile == false
    ) {
      setTimeout(() => {
        $("body").attr("style", "margin-top:52px");
        $(".header").attr("style", "top:52px");
        $(".header_wrap").attr("style", "top:52px");
      }, 100);
    }

    if (
      window.location.href.includes("btvcable.shop") &&
      doms_is_mobile == false
    ) {
      setTimeout(() => {
        $("body").attr("style", "margin-top:52px");
        $(".header").attr("style", "top:52px");
        $(".header_wrap").attr("style", "top:52px");
      }, 100);
    }
  } else if (msgType == "7") {
    campaignActionFloatingPopup.update("campaignActionFloatingPopupTypeA");
    campaignActionFloatingPopup.active("campaignActionFloatingPopupTypeA");
  } else if (msgType == "8") {
    campaignScrollPopup.update("campaignScrollPopupTypeC");
    campaignScrollPopup.active("campaignScrollPopupTypeC");
  }
};

let domsPopupClose = function (msgType, action) {
  if (msgType == "1") {
    campaignDefaultPopup.inactive("campaignDefaultPopupTypeA");
  } else if (msgType == "2") {
    campaignDefaultPopup.inactive("campaignDefaultPopupTypeB");
  } else if (msgType == "3") {
    campaignScrollPopup.inactive("campaignScrollPopupTypeA");
  } else if (msgType == "4") {
    campaignScrollPopup.inactive("campaignScrollPopupTypeB");
  } else if (msgType == "5") {
    campaignFloatingPopup.inactive("campaignFloatingPopupTypeA");
  } else if (msgType == "6") {
    campaignLeadBoard.inactive("campaignLeadBoardTypeA");

    // 모바일 인경우
    if (
      window.location.href.includes("btvcable.shop") &&
      doms_is_mobile == true
    ) {
      //console.log(lineHeadHeight);
      //console.log(document.getElementById('campaignLeadBoardTypeA').classList.contains("active"));
      //console.log($(".top_banner_new").css("display"));

      if ($(".top_banner_new").css("display") == "none") {
        $(".page-head").css({ position: "fixed", top: "0px" });
        $(".page-head ~ .page-body").css({
          "margin-top": linecontainerHeight - lineHeadHeight + "px",
        });
        $("#container").css({
          "padding-top": linecontainerHeight - lineHeadHeight + "px",
        });
      } else {
        $(".top_banner_new").css({ top: "0px" });
        $(".page-head").css({ position: "fixed", top: lineHeadHeight + "px" });
        $(".page-head ~ .page-body").css({
          "margin-top": linecontainerHeight + "px",
        });
        $("#container").css({ "padding-top": linecontainerHeight + "px" });
      }
    }

    if (
      window.location.href.includes("bdirectshop.com") &&
      doms_is_mobile == true
    ) {
      linePopupHeight = parseInt(
        document.getElementById("campaignLeadBoardTypeA").offsetHeight
      );

      $("#Header").css({ margin: "0 0 0 0" });
      $("#Center").css({ margin: "0 0 0 0" });
    }

    if (
      window.location.href.includes("bdirectshop.com") &&
      doms_is_mobile == false
    ) {
      setTimeout(() => {
        $("body").attr("style", "margin-top:0px");
        $(".header").attr("style", "top:0px");
        $(".header_wrap").attr("style", "top:0px");
      }, 100);
    }

    if (
      window.location.href.includes("btvcable.shop") &&
      doms_is_mobile == false
    ) {
      setTimeout(() => {
        $("body").attr("style", "margin-top:0px");
        $(".header").attr("style", "top:0px");
        $(".header_wrap").attr("style", "top:0px");
      }, 100);
    }
  } else if (msgType === "7") {
    campaignActionFloatingPopup.inactive("campaignActionFloatingPopupTypeA");
  } else if (msgType === "8") {
    campaignScrollPopup.inactive("campaignScrollPopupTypeC");
  }

  // 닫기 버튼 클릭 시
  if (action == "CLOSECLICK") {
    //console.log("doms_msg_review_day : "+document.getElementById("doms_msg_review_day"));

    if (
      document.getElementById("doms_msg_review_day") != null &&
      document.getElementById("doms_msg_review_day") != "undefined"
    ) {
      if (document.getElementById("doms_msg_review_day").checked == true) {
        let expiredDate = getPlusDate(
          parseInt(
            document
              .getElementById("doms_msg_review_day")
              .getAttribute("data-msgReviewDay")
          ),
          doms_hour,
          doms_min,
          doms_sec
        );

        if (
          localStorage.getItem("DOMS_REVIEW_LIST") == null ||
          localStorage.getItem("DOMS_REVIEW_LIST") == ""
        ) {
          arrReviewList.push([
            document
              .getElementById("doms_msg_review_day")
              .getAttribute("data-caNo"),
            parseInt(
              document
                .getElementById("doms_msg_review_day")
                .getAttribute("data-msgReviewDay")
            ),
            expiredDate,
          ]);
        } else {
          // review list 재설정
          arrReviewList.length = 0;
          const data = JSON.parse(localStorage.getItem("DOMS_REVIEW_LIST"));
          for (var i = 0; i < data.length; i++) {
            arrReviewList.push([data[i][0], parseInt(data[i][1]), data[i][2]]);
          }
          arrReviewList.push([
            document
              .getElementById("doms_msg_review_day")
              .getAttribute("data-caNo"),
            parseInt(
              document
                .getElementById("doms_msg_review_day")
                .getAttribute("data-msgReviewDay")
            ),
            expiredDate,
          ]);
        }

        localStorage.DOMS_REVIEW_LIST = JSON.stringify(arrReviewList);
        //DOMS_REVIEW_LIST
      }
    }
  }
};

function resetDomsLoadingTime() {
  doms_loading_time = 0;
}

window.addEventListener("focus", resetDomsLoadingTime, false);
window.addEventListener("blur", resetDomsLoadingTime, false);
window.addEventListener("click", resetDomsLoadingTime, false);
window.addEventListener("mousemove", resetDomsLoadingTime, false);
window.addEventListener("keypress", resetDomsLoadingTime, false);
window.addEventListener("scroll", resetDomsLoadingTime, false);
document.addEventListener("touchMove", resetDomsLoadingTime, false);
document.addEventListener("touchEnd", resetDomsLoadingTime, false);

/* 캐시된 경우에 페이지 새로고침 처리 */
window.addEventListener("pageshow", function (event) {
  /* 2024-08-05
   *  기획전 상세 페이지는 새로고침 되지 않게
   *  */
  let isAndroid = /Android/i.test(navigator.userAgent);
  let isIOS = /iPhone|iPad|iPod/i.test(navigator.userAgent);
  let isMobileApp = isAndroid || isIOS;

  if (location.pathname.includes("/event/")) {
    return false;
  } else {
    if (event.persisted && !isMobileApp) {
      window.location.reload();
    }
  }
});

// 케이블 샵 jquery 사용

$(document).on("click", ".btn_to_banner_new", function () {
  if (
    document
      .getElementById("campaignLeadBoardTypeA")
      .classList.contains("active") == true
  ) {
    $(".top_banner_new").css({ top: linePopupHeight - lineHeadHeight + "px" });
    $(".page-head").css({ position: "fixed", top: linePopupHeight + "px" });
    $(".page-head ~ .page-body").css({
      "margin-top":
        linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
    });
    $("#container").css({
      "padding-top":
        linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
    });
  } else {
    $(".page-head").css({ position: "fixed", top: "0px" });
    $(".page-head ~ .page-body").css({
      "margin-top": linecontainerHeight - lineHeadHeight + "px",
    });
    $("#container").css({
      "padding-top": linecontainerHeight - lineHeadHeight + "px",
    });
  }
});

window.addEventListener("resize", function () {
  if (
    window.location.href.includes("btvcable.shop") &&
    doms_is_mobile == true
  ) {
    setTimeout(() => {
      //console.log($(".top_banner_new").css("display"));

      if (document.getElementById("campaignLeadBoardTypeA") != null) {
        console.log(document.getElementById("campaignLeadBoardTypeA"));
        linePopupHeight = parseInt(
          document.getElementById("campaignLeadBoardTypeA").offsetHeight
        );

        if (
          document
            .getElementById("campaignLeadBoardTypeA")
            .classList.contains("active") == true &&
          $(".top_banner_new").css("display") == "block"
        ) {
          $(".top_banner_new").css({ top: linePopupHeight + "px" });
          $(".page-head").css({
            position: "fixed",
            top: linePopupHeight + lineHeadHeight + "px",
          });
          $(".page-head ~ .page-body").css({
            "margin-top": linePopupHeight + linecontainerHeight + "px",
          });
          $("#container").css({
            "padding-top": linePopupHeight + linecontainerHeight + "px",
          });
        } else if (
          document
            .getElementById("campaignLeadBoardTypeA")
            .classList.contains("active") == true &&
          $(".top_banner_new").css("display") == "none"
        ) {
          $(".top_banner_new").css({ top: linePopupHeight + "px" });
          $(".page-head").css({
            position: "fixed",
            top: linePopupHeight + "px",
          });
          $(".page-head ~ .page-body").css({
            "margin-top":
              linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
          });
          $("#container").css({
            "padding-top":
              linePopupHeight + linecontainerHeight - lineHeadHeight + "px",
          });
        } else if (
          document
            .getElementById("campaignLeadBoardTypeA")
            .classList.contains("active") == false &&
          $(".top_banner_new").css("display") == "block"
        ) {
          $(".top_banner_new").css({ top: "0px" });
          $(".page-head").css({
            position: "fixed",
            top: lineHeadHeight + "px",
          });
          $(".page-head ~ .page-body").css({
            "margin-top": linecontainerHeight + "px",
          });
          $("#container").css({ "padding-top": linecontainerHeight + "px" });
        } else {
          $(".page-head").css({ position: "fixed", top: "0px" });
          $(".page-head ~ .page-body").css({
            "margin-top": linecontainerHeight - lineHeadHeight + "px",
          });
          $("#container").css({
            "padding-top": linecontainerHeight - lineHeadHeight + "px",
          });
        }
      }
    }, 80);
  }
});

const previewDomsPopup2 = (doms_ca_no) => {
  let result = multiPreviewJson;

  let multiList = "";
  let resultData = "";

  if (typeof result.data.campaign !== "undefined") {
    resultData = result.data.campaign;

    if (typeof result.data.multiList !== "undefined") {
      multiList = result.data.multiList;
    }
  } else {
    resultData = result.data;
  }

  console.log(result.data);
  //console.log(result.data.CA_MSG_TYPE);

  let preview = true;

  let strPopup = makePopupHtml(resultData, preview, multiList);

  let container = document.getElementsByTagName("body")[0];

  if (resultData.CA_MSG_TYPE == "6") {
    if (
      window.location.href.includes("bdirectshop.com") &&
      doms_is_mobile == true
    ) {
      container = document.getElementById("Document");
    } else {
      container = document.getElementById("page");
    }

    container.prepend(stringToHTML(strPopup));
  } else {
    container.append(stringToHTML(strPopup));
  }

  domsPopupOpen(resultData.CA_MSG_TYPE);

  if (resultData.CA_MSG_TYPE === "8") {
    scrollSwiper = initSwiper();
  }
};

const matchDomsPopup2 = (doms_condition) => {
  // 전환 페이지에서 view count 가 한번 더 잡히기 때문에 예외 처리
  if (location.pathname === "/shop/com/cs/done.do") {
    return false;
  }

  console.log(doms_condition);
  let result = multiCampaignJson;

  let multiList = "";
  let resultData = "";

  if (typeof result.data.campaign !== "undefined") {
    resultData = result.data.campaign;

    if (typeof result.data.multiList !== "undefined") {
      multiList = result.data.multiList;
    }
  } else {
    resultData = result.data;
  }

  if (resultData == "undefined" || resultData == null || resultData == "") {
    // 리턴 되는 DOMS 없음
  } else {
    let preview = false;
    let strPopup = makePopupHtml(resultData[0], preview, multiList);

    let container = document.getElementsByTagName("body")[0];

    if (resultData[0].CA_MSG_TYPE == "6") {
      if (
        window.location.href.includes("bdirectshop.com") &&
        doms_is_mobile == true
      ) {
        container = document.getElementById("Document");
      } else {
        container = document.getElementById("page");
      }

      container.prepend(stringToHTML(strPopup));
    } else {
      container.append(stringToHTML(strPopup));
    }

    if (resultData[0].CA_MSG_TYPE === "8") {
      scrollSwiper = initSwiper();
    }

    //console.log(resultData[0]);

    // AT_ONCE 즉시
    // PERCENT 노출 페이지 스크롤
    // LOADING N 초 후
    // NONE_MOVE 초 후
    // BEFORE_OUT 이탈

    if (resultData[0].CA_VIEW_POINT == "AT_ONCE") {
      domsPopupOpen(resultData[0].CA_MSG_TYPE);
      start_exp_time();
      activateLink();

      saveDomsLog(
        "SHOW",
        resultData[0].CA_NO,
        resultData[0].CA_MSG_TYPE,
        resultData[0].EXPOSURE_LIMIT_CNT,
        resultData[0].CA_PURPOSE,
        resultData[0].CA_PURPOSE_URL
      );
    } else if (resultData[0].CA_VIEW_POINT == "PERCENT") {
      let viewPointPercentage = parseInt(resultData[0].VIEW_POINT_SUB);

      setInterval(function () {
        let scrollTo = $(window).scrollTop(),
          docHeight = $(document).height(),
          windowHeight = $(window).height(),
          scrollPercent = (scrollTo / (docHeight - windowHeight)) * 100;

        if (scrollPercent > viewPointPercentage) {
          if (doms_open == true) {
            domsPopupOpen(resultData[0].CA_MSG_TYPE);
            start_exp_time();
            activateLink();
            saveDomsLog(
              "SHOW",
              resultData[0].CA_NO,
              resultData[0].CA_MSG_TYPE,
              resultData[0].EXPOSURE_LIMIT_CNT,
              resultData[0].CA_PURPOSE,
              resultData[0].CA_PURPOSE_URL
            );
            doms_open = false;
          }
        }
      }, 300);
    } else if (resultData[0].CA_VIEW_POINT == "LOADING") {
      setTimeout(() => {
        domsPopupOpen(resultData[0].CA_MSG_TYPE);
        start_exp_time();
        activateLink();
        saveDomsLog(
          "SHOW",
          resultData[0].CA_NO,
          resultData[0].CA_MSG_TYPE,
          resultData[0].EXPOSURE_LIMIT_CNT,
          resultData[0].CA_PURPOSE,
          resultData[0].CA_PURPOSE_URL
        );
      }, parseInt(resultData[0].VIEW_POINT_SUB) * 1000);
    } else if (resultData[0].CA_VIEW_POINT == "NONE_MOVE") {
      setInterval(function () {
        //console.log(doms_loading_time);

        if (doms_loading_time > parseInt(resultData[0].VIEW_POINT_SUB)) {
          if (doms_open == true) {
            domsPopupOpen(resultData[0].CA_MSG_TYPE);
            start_exp_time();
            activateLink();
            saveDomsLog(
              "SHOW",
              resultData[0].CA_NO,
              resultData[0].CA_MSG_TYPE,
              resultData[0].EXPOSURE_LIMIT_CNT,
              resultData[0].CA_PURPOSE,
              resultData[0].CA_PURPOSE_URL
            );
            doms_open = false;
          }
        }
      }, 1000);
    } else if (resultData[0].CA_VIEW_POINT == "BEFORE_OUT") {
      if (resultData[0].VIEW_POINT_SUB == "OTHER_PAGE") {
        let click_url = "";

        //$(document).on("click", ".dom-header a, .page-head a", function() {
        $(document).on("click", "#wrap a", function () {
          if (doms_open == true) {
            domsPopupOpen(resultData[0].CA_MSG_TYPE);
            start_exp_time();
            activateLink();
            saveDomsLog(
              "SHOW",
              resultData[0].CA_NO,
              resultData[0].CA_MSG_TYPE,
              resultData[0].EXPOSURE_LIMIT_CNT,
              resultData[0].CA_PURPOSE,
              resultData[0].CA_PURPOSE_URL
            );
            doms_open = false;

            click_url = $(this).attr("href");
            $(this).attr("href", "javascript:void(0)");
          } else {
            if ($(this).attr("href") == "javascript:void(0)") {
              $(this).attr("href", click_url);
            }
          }
        });
      }

      if (resultData[0].VIEW_POINT_SUB == "BACK_BTN") {
        let is_head = false;
        //$(document).on("click", ".dom-header a, .page-head a", function() {
        $(document).on("click", "#wrap a", function () {
          is_head = true;
        });

        window.addEventListener(
          "popstate",
          function (event) {
            setTimeout(() => {
              if (doms_open == true && is_head == false) {
                domsPopupOpen(resultData[0].CA_MSG_TYPE);
                start_exp_time();
                activateLink();
                saveDomsLog(
                  "SHOW",
                  resultData[0].CA_NO,
                  resultData[0].CA_MSG_TYPE,
                  resultData[0].EXPOSURE_LIMIT_CNT,
                  resultData[0].CA_PURPOSE,
                  resultData[0].CA_PURPOSE_URL
                );
                doms_open = false;
              } else {
                if (is_head == false)
                  history.pushState(null, null, window.location.pathname);
              }
            }, 500);
          },
          false
        );
      }
    }
  }

  console.log(resultData);
};

const initSwiper = function () {
  $("#campaignScrollPopupTypeC .scroll-slides").append(
    `<div class="swiper-pagination"></div>`
  );

  return new Swiper("#campaignScrollPopupTypeC .scroll-slides", {
    // slidesPerView: 'auto',
    autoplay: {
      delay: 2500,
      disableOnInteraction: true,
    },
    loop: true,
    slidesPerView: 1,
    spaceBetween: 0,
    autoHeight: true,

    pagination: {
      el: ".scroll-slides .swiper-pagination",
      type: "fraction",
    },
  });
};
