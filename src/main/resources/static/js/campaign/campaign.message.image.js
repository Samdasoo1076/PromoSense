const campaignMessageImage = (() => {
    const btnSave = document.querySelector('#btnSave');
    const txtMsgImgAlter01 = document.querySelector('#msgImgAlter01');          // 대체 텍스트
    const txtMsgButton = document.querySelector('#msgButton');                  // 랜딩 버튼 문구
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
    const fileMsgImgRnm01 = document.querySelector('input[name=msgImgRnm01]');
    const chkMsgButtonTf = document.querySelector('#msgButtonTf');              // 랜딩 버튼 사용 여부
    const chkMsgLeaveTf = document.querySelector('#msgLeaveTf');                // 가입상담 신청 사용 여부
    const chkMsgRegistTf = document.querySelector('#msgRegistTf');              // 바로가입 사용 여부
    const chkMsgMobileCallingTf = document.querySelector('#msgMobileCallingTf');// 모바일 전용 전화연결 사용 여부
    const chkMsgBackDimTf = document.querySelector('#msgBackDimTf');            // 화면 dim처리 사용 여부
    const chkMsgReviewTf = document.querySelector('#msgReviewTf');              // 다시보지 않기 사용 여부
    const chkMsgSaveTf = document.querySelector('#msgSaveTf');                  // 메시지 저장 사용 여부

    const radMsgReviews = document.querySelectorAll('#radMsgReview');            // 다시 보지 않기
    
    const spanContentLength = document.querySelector('#contentLength');         // 서브 문구 글자숫자 표시
    
    const viewImgs = document.querySelectorAll('.board-cover');                  // 이미지 선택시 미리보기

    return {
        init: function() {
            btnSave.addEventListener('click', this.save);
            fileMsgImgRnm01.addEventListener('change', this.imageView);
            txtMsgImgAlter01.addEventListener('change', this.setImageAlter);
            txtMsgButton.addEventListener('keyup', this.setMsgButton);
            txtMsgButtonBgColor.addEventListener('change', this.setMsgButton);
            txtMsgButtonColor.addEventListener('change', this.setMsgButton);
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

            chkMsgReviewTf.addEventListener('click', this.setDisplay);
            chkMsgButtonTf.addEventListener('click', this.setDisplay);
            chkMsgLeaveTf.addEventListener('click', this.setDisplay);
            chkMsgRegistTf.addEventListener('click', this.setDisplay);
            chkMsgMobileCallingTf.addEventListener('click', this.setDisplay);

            this.setMsgButton(txtMsgButtonBgColor);
            this.setMsgButton(txtMsgButtonColor);
            this.setMsgLeave(txtMsgLeaveLineColor);
            this.setMsgLeave(txtMsgLeaveColor);
            this.setMsgRegist(txtMsgRegistLineColor);
            this.setMsgRegist(txtMsgRegistColor);
            this.setMsgMobileCalling(txtMsgMobileCallingLineColor);
            this.setMsgMobileCalling(txtMsgMobileCallingColor);

            this.setDisplay(chkMsgReviewTf);
            this.setDisplay(chkMsgButtonTf);
            this.setDisplay(chkMsgLeaveTf);
            this.setDisplay(chkMsgRegistTf);
            this.setDisplay(chkMsgMobileCallingTf);
            this.setPreviewButton(); // 0328수정
        },
        imageView: function(e) {
            const reader = new FileReader();
            const hidFile = document.querySelector('input[name=hidFile]');

            if(e.target.files && e.target.files[0]) {
                hidFile.files = fileMsgImgRnm01.files;
                reader.onload = function(e) {
                    viewImgs.forEach(element => {
                        element.firstElementChild.src = e.target.result;
                    });
                };

                reader.readAsDataURL(e.target.files[0]);
            } else {
                fileMsgImgRnm01.files = hidFile.files;
            }
        },
        setPreviewButton: function() { // 0328수정
            document.querySelectorAll('.board-util').forEach((element, cnt) => {
                let buttonLayout;
                let leaveLayout;
                let registLayout;
                let mobileCallLayout;

                if (chkMsgButtonTf.checked) {
                    let spanLayout = document.createElement('span');
                    spanLayout.classList.add('btn-text');
                    if (element.querySelector('.attr-move')) {
                        buttonLayout = element.querySelector('.attr-move');
                    } else {
                        buttonLayout = document.createElement('a');
                        buttonLayout.classList.add('btn', 'type-cta', 'attr-move');
                        buttonLayout.setAttribute('href', '#');
                        spanLayout.appendChild(document.createTextNode('랜딩 페이지 이동'));
                        buttonLayout.appendChild(spanLayout);
                    }
                }
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

                if (cnt == 0) {
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

            if(chkMsgButtonTf.checked) {
                this.setMsgButton(txtMsgButtonBgColor);
                this.setMsgButton(txtMsgButtonColor);
                this.setMsgButton(txtMsgButton);
            }
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
        setImageAlter: function() {
            viewImgs.forEach(element => {
                element.firstElementChild.alt = txtMsgImgAlter01.value;
            });
        },
        setMsgButton: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            document.querySelectorAll('.type-cta.attr-move').forEach(element => {
                if(elem.id == 'msgButton') {
                    element.firstElementChild.textContent = (txtMsgButton.value == '')? '랜딩 페이지 이동' : txtMsgButton.value; // 0328수정
                } else if(elem.id == 'msgButtonColor') {
                    element.firstElementChild.style.color = txtMsgButtonColor.value;
                } else if(elem.id == 'msgButtonBgColor') {
                    element.setAttribute('style'
                        , '--campaign-button-border-color: ' + txtMsgButtonBgColor.value +
                        ';--campaign-button-background-color: ' + txtMsgButtonBgColor.value);
                }
            });
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
                campaignMessageImage.setPreviewButton();
            } else if(elem.id == 'msgLeaveTf') {
                campaignMessageImage.setPreviewButton();
            } else if(elem.id == 'msgRegistTf') {
                campaignMessageImage.setPreviewButton();
            } else if(elem.id == 'msgMobileCallingTf') {
                campaignMessageImage.setPreviewButton();
            }
        },
        save: async function() {
            const data = new FormData();

            const radMsgButtonUrlTargetP = document.querySelector('input[name=msgButtonUrlTargetP]:checked');
            const radMsgButtonUrlTargetM = document.querySelector('input[name=msgButtonUrlTargetM]:checked');

            // 이미지
            if(fileMsgImgRnm01.value == '' && document.querySelector('#msgImgNm01').value == '') {
                await Modal.alert('이미지를 등록해 주세요.');
                return false;
            }
            // 이미지 형식체크 2023-04-12 보안점검
            if(fileMsgImgRnm01.value != '') {
                let fileLength = fileMsgImgRnm01.value.length;
                let fileDot = fileMsgImgRnm01.value.lastIndexOf('.');
                let fileType = fileMsgImgRnm01.value.substring(fileDot + 1, fileLength).toLowerCase();

                if(constants.imageType.indexOf(fileType) == -1) {
                    await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                    return false;
                }
            }
            // 이미지 대체 문구
            if(txtMsgImgAlter01.value == '') { await Modal.alert('이미지 대체 텍스트를 입력해 주세요.'); return false; }
            // CTA 버튼
            if(chkMsgButtonTf.checked) {
                if(txtMsgButton.value == '') { await Modal.alert('CTA 버튼명을 입력해 주세요.'); return false; }
                if(txtMsgButtonMoUrl.value == '' && txtMsgButtonPcUrl.value == '') {
                    await Modal.alert('CTA 버튼 랜딩 페이지 URL을 입력해 주세요.'); return false;
                }
            }
            // 다시 보지 않기
            if(chkMsgReviewTf.checked) {
                if(document.querySelector('input[name=radMsgReview]:checked').value == 'D') {
                    if(txtMsgReviewDays.value == '') { await Modal.alert('메시지 다시 보지 않기 설정 상세 정보를 입력해 주세요.'); return false; }
                }
            }
            // 메시지 보관 저장
            if(chkMsgSaveTf.checked) {
                if(txtMsgSaveTitle.value == '') { await Modal.alert('메시지 콘텐츠명을 입력해 주세요.'); return false; }
            }

            // 데이터 SET
            data.append('msgImgTf', 'Y');
            data.append('file', fileMsgImgRnm01.files[0]);
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
            data.append('msgSaveTf', (chkMsgSaveTf.checked)? 'Y' : 'N');
            if(chkMsgSaveTf.checked) {
                data.append('msgName', txtMsgSaveTitle.value);
            }
            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageImage.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageImage.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);
            if(chkMsgSaveTf.checked) {
                await capture.img(document.querySelector("#campaignMessagePreviewLaptop .campaign-popup"), (img) => {
                    data.append('msgCapture', img);
                });
            }

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
        showHisMessage: function() {
            const acl = document.querySelector('#acl').value;
            const msgType = document.querySelector('#msgType').value;
            Popup.open(`/campaign/popup/historyMessage?acl=${acl}&msgType=${msgType}`, 'contentPopupMessageImage');
        },
        saveTemp: async function() {
            const data = new FormData();

            const radMsgButtonUrlTargetP = document.querySelector('input[name=msgButtonUrlTargetP]:checked');
            const radMsgButtonUrlTargetM = document.querySelector('input[name=msgButtonUrlTargetM]:checked');

            // 이미지 형식체크 2023-04-12 보안점검
            if(fileMsgImgRnm01.value != '') {
                let fileLength = fileMsgImgRnm01.value.length;
                let fileDot = fileMsgImgRnm01.value.lastIndexOf('.');
                let fileType = fileMsgImgRnm01.value.substring(fileDot + 1, fileLength).toLowerCase();

                if(constants.imageType.indexOf(fileType) == -1) {
                    await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                    return false;
                }
            }

            data.append('msgImgTf', 'Y');
            data.append('file', fileMsgImgRnm01.files[0]);
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
            data.append('msgSaveTf', (chkMsgSaveTf.checked)? 'Y' : 'N');
            if(chkMsgSaveTf.checked) {
                data.append('msgName', txtMsgSaveTitle.value);
            }
            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageImage.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageImage.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);
            /*if(chkMsgSaveTf.checked) {
                await capture.img(document.querySelector("#campaignMessagePreviewLaptop .campaign-popup"), (img) => {
                    data.append('msgCapture', img);
                });
            }*/

            const acl = document.querySelector('#acl').value;
            const id = document.querySelector('#caNo').value;

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
            tmpHtml.querySelector('img').src = '';

            return tmpHtml.querySelector('.campaign-popup').outerHTML;
        }
    }
})();

campaignMessageImage.init();