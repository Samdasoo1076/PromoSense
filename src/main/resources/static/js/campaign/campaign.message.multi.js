const chkMsgLeaveTf = document.querySelector('#msgLeaveTf');                // 가입상담 신청 사용 여부
const chkMsgRegistTf = document.querySelector('#msgRegistTf');              // 바로가입 사용 여부
const chkMsgMobileCallingTf = document.querySelector('#msgMobileCallingTf');// 모바일 전용 전화연결 사용 여부
let changeImages = [];

/* 이미지 대체 텍스트 */
const setImageAlterAll = function () {
    document.querySelectorAll('#msgImgAlter01').forEach(function(element, key) {
        element.addEventListener('change', function () {
            document.querySelectorAll('#campaignMessagePreviewLaptop .board-cover img')[key].alt = element.value;
            document.querySelectorAll('#campaignMessagePreviewMobile .board-cover img')[key].alt = element.value;
        });

        element.addEventListener('focus', function () {
            slideTo(key);
        })
    });
}

/* 랜딩 페이지 이동 버튼 관련 이벤트 처리 */
const setMsgButtonAll = function() {
    document.querySelectorAll('#msgButton').forEach(function(element, key) {
        element.addEventListener('keyup', function() {
            setButtonPreviewLandingAll();
        });

        element.addEventListener('focus', function () {
            slideTo(key);
        });

        // 랜딩 페이지 URL 부분
        document.querySelectorAll('#msgButtonPcUrl')[key].addEventListener('focus', function () {
            slideTo(key);
        });
        document.querySelectorAll('#msgButtonMoUrl')[key].addEventListener('focus', function () {
            slideTo(key);
        });

        // 이미지 파일 등록 input
        document.querySelectorAll('#msgImgRnm01')[key].addEventListener('focus', function () {
            slideTo(key);
        });

        // 이미지 파일 등록 버튼
        document.querySelectorAll('.file-to-upload button')[key].addEventListener('focus', function () {
            slideTo(key);
        });

        // 타겟 지정 버튼
        document.getElementsByName(`msgButtonUrlTargetP${ (key+1) }`).forEach(function(element) {
            element.addEventListener('focus', function () {
                slideTo(key);
            });
        });
        document.getElementsByName(`msgButtonUrlTargetM${ (key+1) }`).forEach(function(element) {
            element.addEventListener('focus', function () {
                slideTo(key);
            });
        });
    })
}

/* 랜딩 페이지 이동 버튼 css */
const setMsgButtonCssAll = function() {
    document.querySelectorAll('#msgButtonBgColor').forEach(function(element, key) {
        element.addEventListener('change', function() {
            setButtonPreviewLandingAll();
        });

        element.addEventListener('focus', function () {
            slideTo(key);
        });

        document.querySelectorAll('#msgButtonColor')[key].addEventListener('change', function() {
            setButtonPreviewLandingAll();
        });

        document.querySelectorAll('#msgButtonColor')[key].addEventListener('focus', function() {
            slideTo(key);
        });
    })
}

/* 랜딩 페이지로 이동 버튼 기본 셋팅 */
const setButtonPreviewLandingAll = function () {
    document.querySelectorAll('#msgButtonTf').forEach(function (element, key) {
        let moveHtml = "";

        let msgButton = document.querySelectorAll('#msgButton')[key].value === '' ?
            '랜딩페이지 이동' : document.querySelectorAll('#msgButton')[key].value;
        let msgButtonBgColor = document.querySelectorAll('#msgButtonBgColor')[key].value;
        let msgButtonColor = document.querySelectorAll('#msgButtonColor')[key].value;

        if (element.checked) {
            moveHtml = `
                <a class="btn type-cta attr-move" href="#" 
                    style="--campaign-button-border-color: ${ msgButtonBgColor };
                           --campaign-button-background-color:${ msgButtonBgColor };">
                    <span class="btn-text" style="color: ${ msgButtonColor };">${ msgButton }</span>
                </a>
            `;
        }

        let pcPreview = document.querySelectorAll('#campaignMessagePreviewLaptop .board-util .button-area')[key];
        let moPreview = document.querySelectorAll('#campaignMessagePreviewMobile .board-util .button-area')[key];

        if (!pcPreview || !moPreview) return true;

        if (pcPreview.querySelector('.attr-move')) pcPreview.querySelector('.attr-move').remove();
        if (moPreview.querySelector('.attr-move')) moPreview.querySelector('.attr-move').remove();

        if ( moveHtml === ''
            && !chkMsgLeaveTf.checked
            && !chkMsgRegistTf.checked
        ) {
            if (chkMsgMobileCallingTf.checked) {
                document.querySelectorAll('#campaignMessagePreviewLaptop .board-inform')[key].style.display = 'none';
                document.querySelectorAll('#campaignMessagePreviewMobile .board-inform')[key].style.display = 'block';
            } else {
                document.querySelectorAll('#campaignMessagePreviewLaptop .board-inform')[key].style.display = 'none';
                document.querySelectorAll('#campaignMessagePreviewMobile .board-inform')[key].style.display = 'none';
            }
        } else {
            document.querySelectorAll('#campaignMessagePreviewLaptop .board-inform')[key].style.display = 'block';
            document.querySelectorAll('#campaignMessagePreviewMobile .board-inform')[key].style.display = 'block';
        }

        pcPreview.insertAdjacentHTML('afterbegin', moveHtml);
        moPreview.insertAdjacentHTML('afterbegin', moveHtml);
    })

    setButtonPreviewAll();
}

const setButtonPreviewAll = function () {
    let pcHtml = "";
    let moHtml = "";

    // 가입상담 신청
    if (document.querySelector('#msgLeaveTf').checked) {
        let msgLeaveLineColor = document.querySelector('#msgLeaveLineColor').value;
        let msgLeaveColor = document.querySelector('#msgLeaveColor').value;

        pcHtml += `
            <a class="btn type-cta attr-send" href="#" style="--campaign-button-border-color: ${ msgLeaveLineColor }">
                <span class="btn-text" style="color: ${ msgLeaveColor };">가입상담 신청</span>
            </a>
        `;
    }

    // 바로가입
    if (document.querySelector('#msgRegistTf').checked) {
        let msgRegistLineColor = document.querySelector('#msgRegistLineColor').value;
        let msgRegistColor = document.querySelector('#msgRegistColor').value;

        pcHtml += `
            <a class="btn type-cta attr-join" href="#" style="--campaign-button-border-color: ${ msgRegistLineColor }">
                <span class="btn-text" style="color: ${ msgRegistColor };">바로가입</span>
            </a>
        `;
    }

    moHtml = pcHtml

    // 모바일 전용 전화 연결 버튼
    // 가입상담 전화
    if (document.querySelector('#msgMobileCallingTf').checked) {
        let msgRegistLineColor = document.querySelector('#msgRegistLineColor').value;
        let msgRegistColor = document.querySelector('#msgRegistColor').value;

        moHtml += `
            <a class="btn type-cta attr-call" href="#" style="--campaign-button-border-color: ${ msgRegistLineColor }">
                <span class="btn-text" style="color: ${ msgRegistColor };">가입상담 전화</span>
            </a>
        `;
    }

    document.querySelectorAll('#campaignMessagePreviewLaptop .board-util .button-area').forEach(element => {
        if (element.querySelector('.attr-send')) element.querySelector('.attr-send').remove();
        if (element.querySelector('.attr-join')) element.querySelector('.attr-join').remove();

        element.insertAdjacentHTML('beforeend', pcHtml);
    });

    document.querySelectorAll('#campaignMessagePreviewMobile .board-util .button-area').forEach(element => {
        if (element.querySelector('.attr-send')) element.querySelector('.attr-send').remove();
        if (element.querySelector('.attr-join')) element.querySelector('.attr-join').remove();
        if (element.querySelector('.attr-call')) element.querySelector('.attr-call').remove();

        element.insertAdjacentHTML('beforeend', moHtml);
    });

    // #campaignMessagePreviewLaptop
    // #campaignMessagePreviewMobile
    if (typeof updateSlide !== 'undefined') {
        updateSlide();
    }

    setImageAlterAll();
}

if (document.querySelectorAll('.mf-content').length === 0) {
    const emptyTitleTemplate = `
        <div class="heading-box module-a style-a type-a mf-title">
            <p class="heading-subject"><span class="heading-name">멀티형 메시지 1</span>
            </p>
            <div class="heading-dataset">
                <div class="button-area">
                    <button onclick="campaignMessageMulti.removeMessage(this);" class="btn delete" type="button"><span
                            class="btn-text">삭제</span></button>
                </div>
            </div>
            
            <input type="hidden" name="msgImgNm01" id="msgImgNm01" value="" />
            <input type="hidden" name="hidMsgImgRnm01" id="hidMsgImgRnm01" value="" />
            <input type="file" name="hidFile" id="hidFile" style="display: none;" />
        </div>
    `;

    const emptyContentTemplate = `
                <div class="form-list mf-content">
                    <div class="form-item checked-within">
                        <div class="form-wrap">
                            <div class="form-head">
                                <p class="form-subject"><span class="form-name">이미지</span></p>
                            </div>
                            <div class="form-body">
                                <div class="submit-form module-b style-a type-c large-4x">
                                    <div class="form-list">
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">이미지 파일 등록</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="
                                                    -upload module-a style-a type-a">
                                                        <div class="upload-wrap">
                                                            <div class="upload-head">
                                                                <div class="file-to-upload">
                                                                    <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                                        <input  id="msgImgRnm01" name="msgImgRnm01"
                                                                                class="form-elem" type="file" accept="image/*" />
                                                                    </span>
                                                                    <button class="btn module-c style-b type-fill normal-01 large-4x" type="button" onclick="this.parentElement.querySelector('input[type=file]').click();"><span class="btn-text">파일 등록</span></button>
                                                                </div>
                                                                <p class="file-noti">권장 사이즈 : 460x270</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">이미지 대체 텍스트</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                            <input type="text" class="form-elem" id="msgImgAlter01" name="msgImgAlter01"
                                                                   placeholder="웹접근성 준수를 위해 이미지에 맞는 대체 텍스트를 입력하세요."
                                                                   data-bui-form-cancel="true" value="" />
                                                            <span class="form-func"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 이미지 등록 끝 -->
                    <!-- CTA 버튼 시작 -->
                    <div class="form-item N checked-within">
                        <div class="form-wrap">
                            <div class="form-head">
                                <p class="form-subject">
                                    <span class="form-name">CTA 버튼</span>
                                    <label class="form module-a style-a switch">
                                        <input class="form-elem" type="checkbox" id="msgButtonTf" name="msgButtonTf" onchange="checkedWithin(this, '.form-item');" checked="checked"/>
                                    </label>
                                </p>
                            </div>
                            <div class="form-body">
                                <div class="submit-form module-b style-a type-c large-4x">
                                    <div class="form-list">
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼명</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                            <input type="text" class="form-elem" placeholder="버튼명" id="msgButton" name="msgButton"
                                                                   data-bui-form-cancel="true" value="" maxlength="20" />
                                                            <span class="form-func"></span>
                                                        </span>
                                                    </div>
                                                    <p class="form-noti">띄어쓰기 포함 최대 20자까지 입력 가능합니다.</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼 배경 컬러</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form colorpicker module-b style-b type-line normal-01 large-4x flex" data-bui-form-value="#4130DF" style="--bui-form-value: #4130DF">
                                                            <input class="form-elem" type="color" id="msgButtonBgColor" name="msgButtonBgColor" value="#4130DF" onchange="buiFormColorpicker(this);">
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼 텍스트 컬러</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form colorpicker module-b style-b type-line normal-01 large-4x flex" data-bui-form-value="#FFFFFF" style="--bui-form-value: #FFFFFF">
                                                            <input class="form-elem" type="color" id="msgButtonColor" name="msgButtonColor" value="#FFFFFF" onchange="buiFormColorpicker(this);">
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">랜딩 페이지 URL</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form url-pc module-b style-b type-line normal-01 large-4x flex">
                                                            <input class="form-elem" type="url" placeholder="https://www." id="msgButtonPcUrl" name="msgButtonPcUrl" data-bui-form-cancel="true" style="padding-right: 41rem;" value="">
                                                            <span class="form-func"><span class="form-cancel"></span></span>
                                                        </span>
                                                    </div>
                                                    <div class="option-list module-a style-a type-a">
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="S" id="msgButtonUrlTargetP" name="msgButtonUrlTargetP" checked="checked">
                                                                <span class="form-text">현재 창</span>
                                                            </label>
                                                        </span>
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="N" id="msgButtonUrlTargetP" name="msgButtonUrlTargetP">
                                                                <span class="form-text">새 창</span>
                                                            </label>
                                                        </span>
                                                    </div>
                                                    <div class="form-area">
                                                        <span class="form url-mo module-b style-b type-line normal-01 large-4x flex">
                                                            <input class="form-elem" type="url" placeholder="https://m." id="msgButtonMoUrl" name="msgButtonMoUrl" data-bui-form-cancel="true" style="padding-right: 41rem;" value="">
                                                            <span class="form-func"><span class="form-cancel"></span></span>
                                                        </span>
                                                    </div>
                                                    <div class="option-list module-a style-a type-a">
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="S" id="msgButtonUrlTargetM" name="msgButtonUrlTargetM" checked="checked">
                                                                <span class="form-text">현재 창</span>
                                                            </label>
                                                        </span>
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="N" id="msgButtonUrlTargetM" name="msgButtonUrlTargetM">
                                                                <span class="form-text">새 창</span>
                                                            </label>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- CTA 버튼 끝 -->
                </div>
            `;

    const legend = document.querySelector('#messageForm legend');

    legend.insertAdjacentHTML('afterend', emptyContentTemplate);
    legend.insertAdjacentHTML('afterend', emptyTitleTemplate);

    const emptySlideTemplate = `
                <div class="info-board">
                  <div class="board-wrap">
                   <div class="board-figure"><span class="board-cover"><img src="/assets/images/campaign/noimage_campaign_popup_b_a_03.png" alt="랜딩페이지 이동"></span>
                   </div>
                   <div class="board-inform">
                    <div class="board-util">
                        <span class="button-area"> 
                            <a class="btn type-cta attr-move" href="#" style="--campaign-button-border-color: #4130df;--campaign-button-background-color: #4130df">
                                <span class="btn-text" style="color: rgb(255, 255, 255);">랜딩페이지 이동</span>
                            </a>
                        </span>
                    </div>
                   </div>
                  </div>
                 </div>`;

    setTimeout(function (){
        addSlide(emptySlideTemplate);
        setButtonPreviewAll();
        setMsgButtonCssAll();
        setMsgButtonAll();
        setImageAlterAll();
    }, 100);
}

const campaignMessageMulti = (() => {
    const btnSave = document.querySelector('#btnSave');
    const txtMsgImgAlter01 = document.querySelectorAll('#msgImgAlter01');          // 대체 텍스트
    const txtMsgButton = document.querySelector('#msgButton');                  // 랜딩 버튼 문구
    const txtMsgButtonAll = document.querySelectorAll('#msgButton');                  // 랜딩 버튼 문구
    const txtMsgButtonBgColor = document.querySelector('#msgButtonBgColor');    // 랜딩 버튼 배경 색상
    const txtMsgButtonColor = document.querySelector('#msgButtonColor');        // 랜딩 버튼 문구 색상
    const txtMsgButtonPcUrl = document.querySelector('#msgButtonPcUrl');        // 랜딩 url pc용
    const txtMsgButtonMoUrl = document.querySelector('#msgButtonMoUrl');        // 랜딩 url pc용
    const txtMsgLeaveLineColor = document.querySelector('#msgLeaveLineColor');  // 가입상담 신청 라인 색상
    const txtMsgLeaveColor = document.querySelector('#msgLeaveColor');          // 가입상담 신청 문구 색상
    const txtMsgRegistLineColor = document.querySelector('#msgRegistLineColor');// 바로가입 라인 색상
    const txtMsgRegistColor = document.querySelector('#msgRegistColor');        // 바로가입 문구 색상
    const txtMsgMobileCallingLineColor = document.querySelector('#msgMobileCallingLineColor');// 모바일 전용 전화연결 라인 색상
    const txtMsgMobileCallingColor = document.querySelector('#msgMobileCallingColor');        // 모바일 전용 전화연결 문구 색상
    const txtMsgReviewDays = document.querySelector('#msgReviewDays');          // 다시보지 않기 일수
    const txtMsgSaveTitle = document.querySelector('#msgSaveTitle');            // 메시지 저장 제목


    //const fileMsgImgRnm01 = document.querySelector('#msgImgRnm01');             // 이미지 파일
    let fileMsgImgRnm01 = document.querySelectorAll('input[name=msgImgRnm01]');
    let chkMsgButtonTf = document.querySelectorAll('#msgButtonTf');              // 랜딩 버튼 사용 여부
    const chkMsgBackDimTf = document.querySelector('#msgBackDimTf');            // 화면 dim처리 사용 여부
    const chkMsgReviewTf = document.querySelector('#msgReviewTf');              // 다시보지 않기 사용 여부

    const radMsgReviews = document.querySelectorAll('#radMsgReview');            // 다시 보지 않기
    
    const spanContentLength = document.querySelector('#contentLength');         // 서브 문구 글자숫자 표시
    
    const viewImgs = document.querySelectorAll('.board-cover');                  // 이미지 선택시 미리보기

    return {
        init: function() {
            btnSave.addEventListener('click', this.save);

            txtMsgLeaveLineColor.addEventListener('change', this.setMsgLeave);
            txtMsgLeaveColor.addEventListener('change', this.setMsgLeave);
            txtMsgRegistLineColor.addEventListener('change', this.setMsgRegist);
            txtMsgRegistColor.addEventListener('change', this.setMsgRegist);
            txtMsgMobileCallingLineColor.addEventListener('change', this.setMsgMobileCalling);
            txtMsgMobileCallingColor.addEventListener('change', this.setMsgMobileCalling);
            txtMsgReviewDays.addEventListener('keyup', this.setDays);

            radMsgReviews.forEach(element => {
                element.addEventListener('click', this.setReview);
            });

            chkMsgReviewTf.addEventListener('click', setButtonPreviewLandingAll);
            chkMsgLeaveTf.addEventListener('click', setButtonPreviewLandingAll);
            chkMsgRegistTf.addEventListener('click', setButtonPreviewLandingAll);
            chkMsgMobileCallingTf.addEventListener('click', setButtonPreviewLandingAll);

            /*this.setMsgButton(txtMsgButtonBgColor);
            this.setMsgButton(txtMsgButtonColor);*/
            this.setMsgLeave(txtMsgLeaveLineColor);
            this.setMsgLeave(txtMsgLeaveColor);
            this.setMsgRegist(txtMsgRegistLineColor);
            this.setMsgRegist(txtMsgRegistColor);
            this.setMsgMobileCalling(txtMsgMobileCallingLineColor);
            this.setMsgMobileCalling(txtMsgMobileCallingColor);

            this.setDisplay(chkMsgReviewTf);
            this.setDisplay(chkMsgLeaveTf);
            this.setDisplay(chkMsgRegistTf);
            this.setDisplay(chkMsgMobileCallingTf);
            //this.setPreviewButton(); // 0328수정

            this.setDisplayAll();
            this.imageViewAll();
            setButtonPreviewLandingAll();
            setImageAlterAll();
            setMsgButtonAll();
            setMsgButtonCssAll();
        },
        setPreviewButton: function() { // 0328수정
            document.querySelectorAll('.board-util').forEach((element, cnt) => {
                let buttonLayout;
                let leaveLayout;
                let registLayout;
                let mobileCallLayout;

                if (chkMsgLeaveTf.checked) {
                    let spanLayout = document.createElement('span');
                    spanLayout.classList.add('btn-text');
                    if (element.querySelector('.attr-send')) {
                        leaveLayout = element.querySelector('.attr-send');
                    } else {
                        leaveLayout = document.createElement('a');
                        leaveLayout.classList.add('btn', 'type-cta', 'attr-send');
                        leaveLayout.setAttribute('href', '#');
                        spanLayout.appendChild(document.createTextNode('가입상담 신청'));
                        leaveLayout.appendChild(spanLayout);
                    }
                }
                if (chkMsgRegistTf.checked) {
                    let spanLayout = document.createElement('span');
                    spanLayout.classList.add('btn-text');
                    if (element.querySelector('.attr-join')) {
                        registLayout = element.querySelector('.attr-join');
                    } else {
                        registLayout = document.createElement('a');
                        registLayout.classList.add('btn', 'type-cta', 'attr-join');
                        registLayout.setAttribute('href', '#');
                        spanLayout.appendChild(document.createTextNode('바로가입'));
                        registLayout.appendChild(spanLayout);
                    }
                }

                if (element.querySelector('.attr-move')) element.querySelector('.attr-move').remove();
                if (element.querySelector('.attr-send')) element.querySelector('.attr-send').remove();
                if (element.querySelector('.attr-join')) element.querySelector('.attr-join').remove();

                if (cnt < (document.querySelectorAll('.board-util').length / 2)) {
                    if (buttonLayout && leaveLayout && registLayout) {
                        element.firstElementChild.append(buttonLayout, leaveLayout, registLayout);
                    } else if (buttonLayout && leaveLayout && !registLayout) {
                        element.firstElementChild.append(buttonLayout, leaveLayout);
                    } else if (buttonLayout && !leaveLayout && registLayout) {
                        element.firstElementChild.append(buttonLayout, registLayout);
                    } else if (!buttonLayout && leaveLayout && registLayout) {
                        element.firstElementChild.append(leaveLayout, registLayout);
                    } else if (buttonLayout && !leaveLayout && !registLayout) {
                        element.firstElementChild.append(buttonLayout);
                    } else if (!buttonLayout && leaveLayout && !registLayout) {
                        element.firstElementChild.append(leaveLayout);
                    } else if (!buttonLayout && !leaveLayout && registLayout) {
                        element.firstElementChild.append(registLayout);
                    }
                } else {
                    if (element.querySelector('.attr-call')) element.querySelector('.attr-call').remove();

                    if (chkMsgMobileCallingTf.checked) {
                        let spanLayout = document.createElement('span');
                        spanLayout.classList.add('btn-text');

                        if (element.querySelector('.attr-call')) {
                            mobileCallLayout = element.querySelector('.attr-call');
                        } else {
                            mobileCallLayout = document.createElement('a');
                            mobileCallLayout.classList.add('btn', 'type-cta', 'attr-call');
                            mobileCallLayout.setAttribute('href', '#');

                            spanLayout.appendChild(document.createTextNode('가입상담 전화'));
                            mobileCallLayout.appendChild(spanLayout);
                        }
                    }

                    if (buttonLayout && leaveLayout && registLayout && mobileCallLayout) { // case1 : 모두 존재 하는경우
                        element.firstElementChild.append(buttonLayout, leaveLayout, registLayout, mobileCallLayout);
                    } else if (buttonLayout && leaveLayout && registLayout && !mobileCallLayout) { // case2 : 상담전화걸기만 없는 경우
                        element.firstElementChild.append(buttonLayout, leaveLayout, registLayout);
                    } else if (buttonLayout && leaveLayout && !registLayout && mobileCallLayout) { // case2 : 바로가입만 없는 경우
                        element.firstElementChild.append(buttonLayout, leaveLayout, mobileCallLayout);
                    } else if (buttonLayout && !leaveLayout && registLayout && mobileCallLayout) { // case2 : 연락처남기기만 없는 경우
                        element.firstElementChild.append(buttonLayout, registLayout, mobileCallLayout);
                    } else if (!buttonLayout && leaveLayout && registLayout && mobileCallLayout) { // case2 : 랜딩버튼만 없는 경우
                        element.firstElementChild.append(leaveLayout, registLayout, mobileCallLayout);
                    } else if (buttonLayout && leaveLayout && !registLayout && !mobileCallLayout) { // case3 : 상담전화걸기, 바로가입 없는 경우
                        element.firstElementChild.append(buttonLayout, leaveLayout);
                    } else if (buttonLayout && !leaveLayout && registLayout && !mobileCallLayout) { // case3 : 상담전화걸기, 연락처남기기 없는 경우
                        element.firstElementChild.append(buttonLayout, registLayout);
                    } else if (!buttonLayout && leaveLayout && registLayout && !mobileCallLayout) { // case3 : 상담전화걸기, 랜딩버튼 없는 경우
                        element.firstElementChild.append(leaveLayout, registLayout);
                    } else if (buttonLayout && !leaveLayout && !registLayout && mobileCallLayout) { // case3 : 연락처남기기, 바로가입 없는 경우
                        element.firstElementChild.append(buttonLayout, mobileCallLayout);
                    } else if (!buttonLayout && leaveLayout && !registLayout && mobileCallLayout) { // case3 : 랜딩버튼, 바로가입 없는 경우
                        element.firstElementChild.append(leaveLayout, mobileCallLayout);
                    } else if (!buttonLayout && !leaveLayout && registLayout && mobileCallLayout) { // case3 : 랜딩버튼, 연락처남기기 없는 경우
                        element.firstElementChild.append(registLayout, mobileCallLayout);
                    } else if (!buttonLayout && !leaveLayout && !registLayout && mobileCallLayout) { // case4 : 상담전화걸기만 있는 경우
                        element.firstElementChild.append(mobileCallLayout);
                    } else if (!buttonLayout && !leaveLayout && registLayout && !mobileCallLayout) { // case4 : 바로가입만 있는 경우
                        element.firstElementChild.append(registLayout);
                    } else if (!buttonLayout && leaveLayout && !registLayout && !mobileCallLayout) { // case4 : 연락처남기기만 있는 경우
                        element.firstElementChild.append(leaveLayout);
                    } else if (buttonLayout && !leaveLayout && !registLayout && !mobileCallLayout) { // case4 : 랜딩버튼만 있는 경우
                        element.firstElementChild.append(buttonLayout);
                    }
                }
            });

            if(chkMsgLeaveTf.checked) {
                this.setMsgLeave(txtMsgLeaveLineColor);
                this.setMsgLeave(txtMsgLeaveColor);
            }
            if(chkMsgRegistTf.checked) {
                this.setMsgRegist(txtMsgRegistLineColor);
                this.setMsgRegist(txtMsgRegistColor);
            }
            if(chkMsgMobileCallingTf.checked) {
                this.setMsgMobileCalling(txtMsgMobileCallingLineColor);
                this.setMsgMobileCalling(txtMsgMobileCallingColor);
            }
        },
        setMsgLeave: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            document.querySelectorAll('.type-cta.attr-send').forEach(element => {
                if(elem.id == 'msgLeaveColor') {
                    element.firstElementChild.style.color = txtMsgLeaveColor.value;
                } else if(elem.id == 'msgLeaveLineColor') {
                    element.setAttribute('style', '--campaign-button-border-color: ' + txtMsgLeaveLineColor.value);
                }
            });
        },
        setMsgRegist: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            document.querySelectorAll('.type-cta.attr-join').forEach(element => {
                if(elem.id == 'msgRegistColor') {
                    element.firstElementChild.style.color = txtMsgRegistColor.value;
                } else if(elem.id == 'msgRegistLineColor') {
                    element.setAttribute('style', '--campaign-button-border-color: ' + txtMsgRegistLineColor.value);
                }
            });
        },
        setMsgMobileCalling: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            document.querySelectorAll('.type-cta.attr-call').forEach(element => {
                if(elem.id == 'msgMobileCallingColor') {
                    element.firstElementChild.style.color = txtMsgMobileCallingColor.value;
                } else if(elem.id == 'msgMobileCallingLineColor') {
                    element.setAttribute('style', '--campaign-button-border-color: ' + txtMsgMobileCallingLineColor.value);
                }
            });

            if (typeof updateSlide !== 'undefined') {
                updateSlide();
            }
        },
        setReview: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            if(elem.value == 'T') txtMsgReviewDays.disabled = true; else txtMsgReviewDays.disabled = false;

            document.querySelectorAll('.campaign-popup .popup-local-util').forEach(element => {
                const view = element.querySelector('.form-text');
                if(elem.value == 'T') {
                    view.textContent = '오늘 하루 보지 않기';
                } else {
                    view.textContent = String(txtMsgReviewDays.value) + '일 동안 보지 않기';
                }
            });
        },
        setDays: function() {
            document.querySelectorAll('.campaign-popup .popup-local-util').forEach(element => {
                const view = element.querySelector('.form-text');
                if(document.querySelector('input[name=radMsgReview]:checked').value == 'T') {
                    view.textContent = '오늘 하루 보지 않기';
                } else {
                    view.textContent = String(txtMsgReviewDays.value) + '일 동안 보지 않기';
                }
            });
        },
        setDisplay: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            if(elem.id == 'msgReviewTf') {
                document.querySelectorAll('.campaign-popup .popup-local-util').forEach(element => {
                    const view = element.querySelector('.option-list');
                    if(elem.checked) view.style.display = 'block'; else view.style.display = 'none';
                });
            } else if(elem.id == 'msgButtonTf') {
                campaignMessageMulti.setPreviewButton();
            } else if(elem.id == 'msgLeaveTf') {
                campaignMessageMulti.setPreviewButton();
            } else if(elem.id == 'msgRegistTf') {
                campaignMessageMulti.setPreviewButton();
            } else if(elem.id == 'msgMobileCallingTf') {
                campaignMessageMulti.setPreviewButton();
            }

            if (typeof updateSlide !== 'undefined') {
                updateSlide();
            }
        },
        save: async function() {
            const data = new FormData();

            const radMsgButtonUrlTargetP = document.querySelector('input[name=msgButtonUrlTargetP]:checked');
            const radMsgButtonUrlTargetM = document.querySelector('input[name=msgButtonUrlTargetM]:checked');

            // 이미지
            if(fileMsgImgRnm01.length === 0) {
                await Modal.alert('이미지를 등록해 주세요.');
                return false;
            }
            // 이미지 형식체크 2023-04-12 보안점검
            if(fileMsgImgRnm01.length > 0) {
                let fileLength = fileMsgImgRnm01.length;

                for (const files of fileMsgImgRnm01) {
                    let fileDot = files.value.lastIndexOf('.');
                    let fileType = files.value.substring(fileDot + 1).toLowerCase();

                    if(constants.imageType.indexOf(fileType) == -1) {
                        await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                        return false;
                    }
                }
            }

            for (const element of document.querySelectorAll('#msgImgAlter01')) {
                if (element.value === '') {
                    await Modal.alert('이미지 대체 텍스트를 입력해 주세요.');
                    element.focus();

                    return false;
                }
            }

            // CTA 버튼
            const msgButtonTfElements = document.querySelectorAll('#msgButtonTf');
            const txtMsgImgAlter01Elements = document.querySelectorAll('#msgImgAlter01');
            const msgButtonElements = document.querySelectorAll('#msgButton');
            const msgButtonPcUrlElements = document.querySelectorAll('#msgButtonPcUrl');
            const msgButtonMoUrlElements = document.querySelectorAll('#msgButtonMoUrl');
            for (const [index, element] of msgButtonTfElements.entries()) {
                if (element.checked && txtMsgImgAlter01Elements[index].value === '') {
                    await Modal.alert('이미지 대체 텍스트를 입력해 주세요.');
                    txtMsgImgAlter01Elements[index].focus();

                    return false;
                }

                if (element.checked && msgButtonElements[index].value === '') {
                    await Modal.alert('CTA 버튼명을 입력해 주세요.');
                    msgButtonElements[index].focus();

                    return false;
                }

                if ((element.checked &&
                    msgButtonPcUrlElements[index].value === '') ||
                    (element.checked &&
                        msgButtonMoUrlElements[index].value === '') )
                {
                    await Modal.alert('CTA 버튼 랜딩 페이지 URL을 입력해 주세요.');
                    msgButtonPcUrlElements[index].focus();

                    return false;
                }
            }

            // 다시 보지 않기
            if(chkMsgReviewTf.checked) {
                if(document.querySelector('input[name=radMsgReview]:checked').value == 'D') {
                    if(txtMsgReviewDays.value == '') { await Modal.alert('메시지 다시 보지 않기 설정 상세 정보를 입력해 주세요.'); return false; }
                }
            }

            // 데이터 SET
            data.append('msgImgTf', 'Y');

            const fileInputs = document.querySelectorAll('input[name=msgImgRnm01]');

            if (fileInputs.length > 0) {
                fileInputs.forEach((input, index) => {
                    // 입력된 파일 목록을 가져옵니다.
                    const files = input.files;

                    // 각 파일을 FormData에 추가
                    Array.from(files).forEach(file => {
                        data.append('file', file); // 파일 이름에 인덱스를 추가하여 유일하게 만듭니다.
                    });
                });
            }

            data.append('msgImgRnm01', document.querySelector('#hidMsgImgRnm01').value);
            data.append('msgImgNm01', document.querySelector('#msgImgNm01').value);
            data.append('msgImgAlter01', txtMsgImgAlter01[0].value);
            data.append('msgButtonTf', (chkMsgButtonTf.checked)? 'Y' : 'N');
            if(chkMsgButtonTf.checked) {
                data.append('msgButton', txtMsgButton.value);
                data.append('msgButtonBgColor', txtMsgButtonBgColor.value);
                data.append('msgButtonColor', txtMsgButtonColor.value);
                data.append('msgButtonPcUrl', txtMsgButtonPcUrl.value);
                data.append('msgButtonMoUrl', txtMsgButtonMoUrl.value);
                data.append('msgButtonUrlTargetP', radMsgButtonUrlTargetP.value);
                data.append('msgButtonUrlTargetM', radMsgButtonUrlTargetM.value);
            }
            data.append('msgLeaveTf', (chkMsgLeaveTf.checked)? 'Y' : 'N');
            if(chkMsgLeaveTf.checked) {
                data.append('msgLeaveColor', txtMsgLeaveColor.value);
                data.append('msgLeaveLineColor', txtMsgLeaveLineColor.value);
            }
            data.append('msgRegistTf', (chkMsgRegistTf.checked)? 'Y' : 'N');
            if(chkMsgRegistTf.checked) {
                data.append('msgRegistColor', txtMsgRegistColor.value);
                data.append('msgRegistLineColor', txtMsgRegistLineColor.value);
            }
            data.append('msgMobileCallingTf', (chkMsgMobileCallingTf.checked)? 'Y' : 'N');
            if(chkMsgMobileCallingTf.checked) {
                data.append('msgMobileCallingColor', txtMsgMobileCallingColor.value);
                data.append('msgMobileCallingLineColor', txtMsgMobileCallingLineColor.value);
            }
            data.append('msgBackDimTf', (chkMsgBackDimTf.checked)? 'Y' : 'N');
            data.append('msgReviewTf', (chkMsgReviewTf.checked)? 'Y' : 'N');
            if(chkMsgReviewTf.checked) {
                const radios = document.querySelector('input[name=radMsgReview]:checked');
                data.append('msgReviewType', radios.value);
                if(radios.value == 'D') {
                    data.append('msgReviewDays', txtMsgReviewDays.value);
                }
            }
            data.append('msgSaveTf', 'N');

            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageMulti.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageMulti.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);

            let multiList = [];
            let msgImgNm01List = [];
            let msgImgRnm01List = [];

            if (document.querySelectorAll('.heading-name').length > 0) {
                document.querySelectorAll('.heading-name').forEach(function(row, idx) {
                    const radMsgButtonUrlTargetP = document.querySelectorAll('#msgButtonUrlTargetP:checked')[idx].value;
                    const radMsgButtonUrlTargetM = document.querySelectorAll('#msgButtonUrlTargetM:checked')[idx].value;

                    msgImgNm01List.push(document.querySelectorAll('#msgImgNm01')[idx].value || '');
                    msgImgRnm01List.push(document.querySelectorAll('#hidMsgImgRnm01')[idx].value || '');

                    multiList.push({
                        caNo: document.querySelector('#caNo').value,
                        multiSeq: (idx+1),
                        msgImgNm01: document.querySelectorAll('#msgImgNm01')[idx].value || '',
                        msgImgRnm01: document.querySelectorAll('#hidMsgImgRnm01')[idx].value || '',
                        msgImgAlter01: document.querySelectorAll('#msgImgAlter01')[idx].value || '',
                        msgButtonTf: document.querySelectorAll('#msgButtonTf')[idx].checked ? 'Y' : 'N',
                        msgButton: document.querySelectorAll('#msgButton')[idx].value || '',
                        msgButtonBgColor: document.querySelectorAll('#msgButtonBgColor')[idx].value || '',
                        msgButtonColor: document.querySelectorAll('#msgButtonColor')[idx].value || '',
                        msgButtonPcUrl: document.querySelectorAll('#msgButtonPcUrl')[idx].value || '',
                        msgButtonMoUrl: document.querySelectorAll('#msgButtonMoUrl')[idx].value || '',
                        msgButtonUrlTargetP: document.querySelectorAll('#msgButtonUrlTargetP:checked')[idx].value || '',
                        msgButtonUrlTargetM: document.querySelectorAll('#msgButtonUrlTargetM:checked')[idx].value || '',
                    });
                });
            }

            data.append('multiList', JSON.stringify(multiList));
            data.append('msgImgNm01List', msgImgNm01List);
            data.append('msgImgRnm01List', msgImgRnm01List);

            const uniqueArr = changeImages.reduce((acc, currentValue, index) => {
                // 마지막으로 나타나는 인덱스가 현재 인덱스와 같으면 추가
                if (changeImages.lastIndexOf(currentValue) === index) {
                    acc.push(currentValue);
                }
                return acc;
            }, []);

            data.append('changeImages', uniqueArr);

            const acl = document.querySelector('#acl').value;

            domsFetch(`/campaign/message/save.do?acl=${acl}`, {
                method: 'POST',
                body: data
            })
                .then(async response => {
                    const data = await response.json();
                    if(response.ok) {
                        if(data.message == 'fail') {
                            await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                        } else {
                            await Modal.alert('캠페인이 저장 되었습니다.', () => {
                                window.location.href = `/campaign/list/list.do?acl=C0001`;
                            });
                        }
                    } else {
                        await Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));
        },
        saveTemp: async function() {
            const data = new FormData();

            const radMsgButtonUrlTargetP = document.querySelector('input[name=msgButtonUrlTargetP]:checked');
            const radMsgButtonUrlTargetM = document.querySelector('input[name=msgButtonUrlTargetM]:checked');

            // 이미지 형식체크 2023-04-12 보안점검
            if(fileMsgImgRnm01.length > 0) {
                let fileLength = fileMsgImgRnm01.length;

                for (const files of fileMsgImgRnm01) {
                    let fileDot = files.value.lastIndexOf('.');
                    let fileType = files.value.substring(fileDot + 1).toLowerCase();

                    if(constants.imageType.indexOf(fileType) == -1) {
                        await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                        return false;
                    }
                }
            }

            data.append('msgImgTf', 'Y');

            const fileInputs = document.querySelectorAll('input[name=msgImgRnm01]');

            if (fileInputs.length > 0) {
                fileInputs.forEach((input, index) => {
                    // 입력된 파일 목록을 가져옵니다.
                    const files = input.files;

                    // 각 파일을 FormData에 추가
                    Array.from(files).forEach(file => {
                        data.append('file', file); // 파일 이름에 인덱스를 추가하여 유일하게 만듭니다.
                    });
                });
            }

            data.append('msgImgRnm01', document.querySelector('#hidMsgImgRnm01').value);
            data.append('msgImgNm01', document.querySelector('#msgImgNm01').value);
            data.append('msgImgAlter01', txtMsgImgAlter01.value);
            data.append('msgButtonTf', (chkMsgButtonTf.checked)? 'Y' : 'N');
            if(chkMsgButtonTf.checked) {
                data.append('msgButton', txtMsgButton.value);
                data.append('msgButtonBgColor', txtMsgButtonBgColor.value);
                data.append('msgButtonColor', txtMsgButtonColor.value);
                data.append('msgButtonPcUrl', txtMsgButtonPcUrl.value);
                data.append('msgButtonMoUrl', txtMsgButtonMoUrl.value);
                data.append('msgButtonUrlTargetP', radMsgButtonUrlTargetP.value);
                data.append('msgButtonUrlTargetM', radMsgButtonUrlTargetM.value);
            }
            data.append('msgLeaveTf', (chkMsgLeaveTf.checked)? 'Y' : 'N');
            if(chkMsgLeaveTf.checked) {
                data.append('msgLeaveColor', txtMsgLeaveColor.value);
                data.append('msgLeaveLineColor', txtMsgLeaveLineColor.value);
            }
            data.append('msgRegistTf', (chkMsgRegistTf.checked)? 'Y' : 'N');
            if(chkMsgRegistTf.checked) {
                data.append('msgRegistColor', txtMsgRegistColor.value);
                data.append('msgRegistLineColor', txtMsgRegistLineColor.value);
            }
            data.append('msgMobileCallingTf', (chkMsgMobileCallingTf.checked)? 'Y' : 'N');
            if(chkMsgMobileCallingTf.checked) {
                data.append('msgMobileCallingColor', txtMsgMobileCallingColor.value);
                data.append('msgMobileCallingLineColor', txtMsgMobileCallingLineColor.value);
            }
            data.append('msgBackDimTf', (chkMsgBackDimTf.checked)? 'Y' : 'N');
            data.append('msgReviewTf', (chkMsgReviewTf.checked)? 'Y' : 'N');
            if(chkMsgReviewTf.checked) {
                const radios = document.querySelector('input[name=radMsgReview]:checked');
                data.append('msgReviewType', radios.value);
                if(radios.value == 'D') {
                    data.append('msgReviewDays', txtMsgReviewDays.value);
                }
            }
            data.append('msgSaveTf', 'N');

            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageMulti.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageMulti.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);

            let multiList = [];
            let msgImgNm01List = [];
            let msgImgRnm01List = [];

            if (document.querySelectorAll('.heading-name').length > 0) {
                document.querySelectorAll('.heading-name').forEach(function(row, idx) {
                    const radMsgButtonUrlTargetP = document.querySelectorAll('#msgButtonUrlTargetP:checked')[idx].value;
                    const radMsgButtonUrlTargetM = document.querySelectorAll('#msgButtonUrlTargetM:checked')[idx].value;

                    msgImgNm01List.push(document.querySelectorAll('#msgImgNm01')[idx].value || '');
                    msgImgRnm01List.push(document.querySelectorAll('#hidMsgImgRnm01')[idx].value || '');

                    multiList.push({
                        caNo: document.querySelector('#caNo').value,
                        multiSeq: (idx+1),
                        msgImgNm01: document.querySelectorAll('#msgImgNm01')[idx].value || '',
                        msgImgRnm01: document.querySelectorAll('#hidMsgImgRnm01')[idx].value || '',
                        msgImgAlter01: document.querySelectorAll('#msgImgAlter01')[idx].value || '',
                        msgButtonTf: document.querySelectorAll('#msgButtonTf')[idx].checked ? 'Y' : 'N',
                        msgButton: document.querySelectorAll('#msgButton')[idx].value || '',
                        msgButtonBgColor: document.querySelectorAll('#msgButtonBgColor')[idx].value || '',
                        msgButtonColor: document.querySelectorAll('#msgButtonColor')[idx].value || '',
                        msgButtonPcUrl: document.querySelectorAll('#msgButtonPcUrl')[idx].value || '',
                        msgButtonMoUrl: document.querySelectorAll('#msgButtonMoUrl')[idx].value || '',
                        msgButtonUrlTargetP: document.querySelectorAll('#msgButtonUrlTargetP:checked')[idx].value || '',
                        msgButtonUrlTargetM: document.querySelectorAll('#msgButtonUrlTargetM:checked')[idx].value || '',
                    });
                });
            }

            data.append('multiList', JSON.stringify(multiList));
            data.append('msgImgNm01List', msgImgNm01List);
            data.append('msgImgRnm01List', msgImgRnm01List);

            const acl = document.querySelector('#acl').value;
            const id = document.querySelector('#caNo').value;

            const uniqueArr = changeImages.reduce((acc, currentValue, index) => {
                // 마지막으로 나타나는 인덱스가 현재 인덱스와 같으면 추가
                if (changeImages.lastIndexOf(currentValue) === index) {
                    acc.push(currentValue);
                }
                return acc;
            }, []);

            data.append('changeImages', uniqueArr);

            domsFetch(`/campaign/message/temp/save.do?acl=${acl}`, {
                method: 'POST',
                body: data
            })
                .then(async response => {
                    const data = await response.json();
                    if(response.ok) {
                        if(data.message == 'fail') {
                            await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                        } else {
                            await Modal.alert('캠페인이 저장 되었습니다.', () => {
                                window.location.href = `/campaign/base/modify.view?acl=${acl}&id=${id}`;
                            });
                        }
                    } else {
                        await Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));
        },
        setReplaceSrc: function(htmlStr) {
            let tmpHtml = document.createElement('div');
            tmpHtml.innerHTML = htmlStr;
            /*tmpHtml.querySelector('img').src = '';*/

            tmpHtml.querySelectorAll('img').forEach(function(row, idx) {
                row.src = '';
            });

            return tmpHtml.querySelector('.campaign-popup').outerHTML;
        },
        removeMessage: function(el) {
            const allElements = document.querySelectorAll('.btn.delete');
            const index = Array.prototype.indexOf.call(allElements, el);

            if (document.querySelectorAll('.mf-title').length === 1) {
                alert('더 이상 삭제할 수 없습니다.');
                return false;
            }

            /* 스와이퍼 슬라이드 삭제 */
            this.removeSwiperSlide(index);

            let mfTitle = this.findParentWithClass(el, 'mf-title');
            let mfContent = this.findNextSiblingWithClass(mfTitle, 'mf-content');
            mfTitle.remove();
            mfContent.remove();

            /* 멀티형 메시지 제목 정렬 */
            this.sortTitle();
        },
        sortTitle: function () {
            /* 멀티형 메세지 입력 폼 삭제 후 제목 다시 맞춰줌 */
            let mfTitles = document.querySelectorAll('.mf-title');
            mfTitles.forEach(function(row, idx) {
                let headingName = row.querySelector('.heading-name');
                headingName.textContent = `멀티형 메시지 ${(idx + 1)}`;

                let msgButtonUrlTargetP = row.nextElementSibling.querySelectorAll('#msgButtonUrlTargetP');
                let msgButtonUrlTargetM = row.nextElementSibling.querySelectorAll('#msgButtonUrlTargetM');

                msgButtonUrlTargetP.forEach(function (element) {
                    element.setAttribute('name', `msgButtonUrlTargetP${(idx + 1)}`);
                });
                msgButtonUrlTargetM.forEach(function(element) {
                    element.setAttribute('name', `msgButtonUrlTargetM${(idx + 1)}`);
                });
            });
        },
        addMessage: function() {
            let mfContentCnt = document.querySelectorAll('.mf-content').length;

            if (mfContentCnt === 10) {
                alert('최대 10개까지 추가 가능합니다.');
                return false;
            }

            const titleTemplate = `
                <div class="heading-box module-a style-a type-a mf-title">
                    <p class="heading-subject"><span class="heading-name">멀티형 메시지 ${ mfContentCnt + 1 }</span>
                    </p>
                    <div class="heading-dataset">
                        <div class="button-area">
                            <button onclick="campaignMessageMulti.removeMessage(this);" class="btn delete" type="button"><span
                                    class="btn-text">삭제</span></button>
                        </div>
                    </div>
                    
                    <input type="hidden" name="msgImgNm01" id="msgImgNm01" value="" />
                    <input type="hidden" name="hidMsgImgRnm01" id="hidMsgImgRnm01" value="" />
                    <input type="file" name="hidFile" id="hidFile" style="display: none;" />
                </div>
            `;

            const contentTemplate = `
                <div class="form-list mf-content">
                    <div class="form-item checked-within">
                        <div class="form-wrap">
                            <div class="form-head">
                                <p class="form-subject"><span class="form-name">이미지</span></p>
                            </div>
                            <div class="form-body">
                                <div class="submit-form module-b style-a type-c large-4x">
                                    <div class="form-list">
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">이미지 파일 등록</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="file-upload module-a style-a type-a">
                                                        <div class="upload-wrap">
                                                            <div class="upload-head">
                                                                <div class="file-to-upload">
                                                                    <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                                        <input  id="msgImgRnm01" name="msgImgRnm01"
                                                                                class="form-elem" type="file" accept="image/*" />
                                                                    </span>
                                                                    <button class="btn module-c style-b type-fill normal-01 large-4x" type="button" onclick="this.parentElement.querySelector('input[type=file]').click();"><span class="btn-text">파일 등록</span></button>
                                                                </div>
                                                                <p class="file-noti">권장 사이즈 : 460x270</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">이미지 대체 텍스트</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                            <input type="text" class="form-elem" id="msgImgAlter01" name="msgImgAlter01"
                                                                   placeholder="웹접근성 준수를 위해 이미지에 맞는 대체 텍스트를 입력하세요."
                                                                   data-bui-form-cancel="true" value="" />
                                                            <span class="form-func"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 이미지 등록 끝 -->
                    <!-- CTA 버튼 시작 -->
                    <div class="form-item N checked-within">
                        <div class="form-wrap">
                            <div class="form-head">
                                <p class="form-subject">
                                    <span class="form-name">CTA 버튼</span>
                                    <label class="form module-a style-a switch">
                                        <input class="form-elem" type="checkbox" id="msgButtonTf" name="msgButtonTf" onchange="checkedWithin(this, '.form-item');" checked="checked" />
                                    </label>
                                </p>
                            </div>
                            <div class="form-body">
                                <div class="submit-form module-b style-a type-c large-4x">
                                    <div class="form-list">
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼명</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form textfield module-b style-b type-line normal-01 large-4x flex">
                                                            <input type="text" class="form-elem" placeholder="버튼명" id="msgButton" name="msgButton"
                                                                   data-bui-form-cancel="true" value="" maxlength="20" />
                                                            <span class="form-func"></span>
                                                        </span>
                                                    </div>
                                                    <p class="form-noti">띄어쓰기 포함 최대 20자까지 입력 가능합니다.</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼 배경 컬러</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form colorpicker module-b style-b type-line normal-01 large-4x flex" data-bui-form-value="#4130DF" style="--bui-form-value: #4130DF">
                                                            <input class="form-elem" type="color" id="msgButtonBgColor" name="msgButtonBgColor" value="#4130DF" onchange="buiFormColorpicker(this);">
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">버튼 텍스트 컬러</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form colorpicker module-b style-b type-line normal-01 large-4x flex" data-bui-form-value="#FFFFFF" style="--bui-form-value: #FFFFFF">
                                                            <input class="form-elem" type="color" id="msgButtonColor" name="msgButtonColor" value="#FFFFFF" onchange="buiFormColorpicker(this);">
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-item">
                                            <div class="form-wrap">
                                                <div class="form-head">
                                                    <p class="form-subject"><label class="form-name">랜딩 페이지 URL</label></p>
                                                </div>
                                                <div class="form-body">
                                                    <div class="form-area">
                                                        <span class="form url-pc module-b style-b type-line normal-01 large-4x flex">
                                                            <input class="form-elem" type="url" placeholder="https://www." id="msgButtonPcUrl" name="msgButtonPcUrl" data-bui-form-cancel="true" style="padding-right: 41rem;" value="">
                                                            <span class="form-func"><span class="form-cancel"></span></span>
                                                        </span>
                                                    </div>
                                                    <div class="option-list module-a style-a type-a">
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="S" id="msgButtonUrlTargetP" name="msgButtonUrlTargetP${ mfContentCnt + 1 }" checked="checked">
                                                                <span class="form-text">현재 창</span>
                                                            </label>
                                                        </span>
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="N" id="msgButtonUrlTargetP" name="msgButtonUrlTargetP${ mfContentCnt + 1 }">
                                                                <span class="form-text">새 창</span>
                                                            </label>
                                                        </span>
                                                    </div>
                                                    <div class="form-area">
                                                        <span class="form url-mo module-b style-b type-line normal-01 large-4x flex">
                                                            <input class="form-elem" type="url" placeholder="https://m." id="msgButtonMoUrl" name="msgButtonMoUrl" data-bui-form-cancel="true" style="padding-right: 41rem;" value="">
                                                            <span class="form-func"><span class="form-cancel"></span></span>
                                                        </span>
                                                    </div>
                                                    <div class="option-list module-a style-a type-a">
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="S" id="msgButtonUrlTargetM" name="msgButtonUrlTargetM${ mfContentCnt + 1 }" checked="checked">
                                                                <span class="form-text">현재 창</span>
                                                            </label>
                                                        </span>
                                                        <span class="option-item">
                                                            <label class="form radio module-a style-c accent-01 small-2x">
                                                                <input type="radio" class="form-elem" value="N" id="msgButtonUrlTargetM" name="msgButtonUrlTargetM${ mfContentCnt + 1 }">
                                                                <span class="form-text">새 창</span>
                                                            </label>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- CTA 버튼 끝 -->
                </div>
            `;

            let lastContentIdx = mfContentCnt - 1;
            let lastContent = document.querySelectorAll('.mf-content')[lastContentIdx];

            lastContent.insertAdjacentHTML('afterend', contentTemplate);
            lastContent.insertAdjacentHTML('afterend', titleTemplate);

            this.addSwiperSlide();
        },
        findParentWithClass: function(element, className) {
            while (element && element !== document) {
                if (element.classList && element.classList.contains(className)) {
                    return element;
                }

                element = element.parentNode;
            }

            return null;
        },
        findNextSiblingWithClass: function(element, className) {
            let  sibling = element.nextElementSibling;

            while (sibling) {
                if (sibling.classList && sibling.classList.contains(className)) {
                    return sibling;
                }

                sibling = sibling.nextElementSibling;
            }

            return null;
        },
        addSwiperSlide: function() {
            const slideTemplate =
                `<div class="info-board">
                  <div class="board-wrap">
                   <div class="board-figure"><span class="board-cover"><img src="/assets/images/campaign/noimage_campaign_popup_b_a_03.png" alt="랜딩페이지 이동"></span>
                   </div>
                   <div class="board-inform">
                    <div class="board-util">
                        <span class="button-area">
                        </span>
                    </div>
                   </div>
                  </div>
                 </div>`;

            addSlide(slideTemplate);

            setTimeout(function () {
                setButtonPreviewLandingAll();
                campaignMessageMulti.imageViewAll();
                campaignMessageMulti.setDisplayAll();
                setMsgButtonCssAll();
                setMsgButtonAll();
                setImageAlterAll();
            }, 100);
        },
        removeSwiperSlide: function(index) {
            /* 스와이퍼 슬라이드 삭제 */
            removeSlide(index);

            setTimeout(function () {
                setButtonPreviewLandingAll();
                campaignMessageMulti.imageViewAll();
                campaignMessageMulti.setDisplayAll();
                setMsgButtonCssAll();
                setMsgButtonAll();
                setImageAlterAll();
            }, 100);
        },
        imageViewAll: function() {
            document.querySelectorAll('input[name=msgImgRnm01]').forEach(function(el, key, parent) {
                el.removeEventListener('change', el.changeHandler);

                el.addEventListener('change', function(e) {
                    const reader = new FileReader();
                    const hidFile = document.querySelectorAll('input[name=hidFile]')[key];

                    if(el.files && el.files[0]) {
                        hidFile.files = e.files;
                        reader.onload = function(e) {
                            campaignDetailPopSwiper[0].slides[key].querySelector('img').src = e.target.result;
                            campaignDetailPopSwiper[1].slides[key].querySelector('img').src = e.target.result;
                            updateSlide();
                        };

                        reader.readAsDataURL(el.files[0]);
                        changeImages.push(key);
                    } else {
                        e.files = hidFile.files;
                    }
                    slideTo(key);
                });
            })
        },
        setDisplayAll: function() {
            document.querySelectorAll('#msgButtonTf').forEach(function (el, key) {
                let buttonLayout;

                el.addEventListener('click', function(e) {
                    let moElement = document.querySelectorAll('#campaignMessagePreviewMobile .board-util .button-area')[key];
                    let pcElement = document.querySelectorAll('#campaignMessagePreviewLaptop .board-util .button-area')[key];
                    if (el.checked) {
                        let spanLayout = document.createElement('span');
                        spanLayout.classList.add('btn-text');

                        buttonLayout = document.createElement('a');
                        buttonLayout.classList.add('btn', 'type-cta', 'attr-move');
                        buttonLayout.setAttribute('href', '#');
                        spanLayout.appendChild(document.createTextNode('랜딩 페이지 이동'));
                        buttonLayout.appendChild(spanLayout);

                        let moButtonLayout = buttonLayout.cloneNode(true);

                        if (pcElement.querySelector('.attr-move')) pcElement.querySelector('.attr-move').remove();
                        if (moElement.querySelector('.attr-move')) moElement.querySelector('.attr-move').remove();

                        pcElement.prepend(buttonLayout);
                        moElement.prepend(moButtonLayout);
                    } else {
                        pcElement.querySelector('.attr-move') != null ? pcElement.querySelector('.attr-move').remove() : '';
                        moElement.querySelector('.attr-move') != null ? moElement.querySelector('.attr-move').remove() : '';
                    }

                    setButtonPreviewLandingAll();
                    updateSlide();

                    slideTo(key);
                });
            });
        }
    }
})();

campaignMessageMulti.init();