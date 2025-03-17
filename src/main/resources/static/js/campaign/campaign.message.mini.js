const campaignMessageMini = (() => {
    const btnSave = document.querySelector('#btnSave');
    const txtMsgImgAlter01 = document.querySelector('#msgImgAlter01');          // 대체 텍스트
    const txtMsgTitle = document.querySelector('#msgTitle');                    // 메인 문구
    const txtMsgTitleColor = document.querySelector('#msgTitleColor');          // 메인 문구 색상
    const txtMsgContents = document.querySelector('#msgContents');              // 서브 문구
    const txtMsgContentsColor = document.querySelector('#msgContentsColor');    // 서브 문구 색상
    const txtMsgButtonPcUrl = document.querySelector('#msgButtonPcUrl');        // 랜딩 url pc용
    const txtMsgButtonMoUrl = document.querySelector('#msgButtonMoUrl');        // 랜딩 url pc용
    const txtMsgSaveTitle = document.querySelector('#msgSaveTitle');            // 메시지 저장 제목


    //const fileMsgImgRnm01 = document.querySelector('#msgImgRnm01');             // 이미지 파일
    const fileMsgImgRnm01 = document.querySelector('input[name=msgImgRnm01]');
    const chkMsgButtonTf = document.querySelector('#msgButtonTf');              // 랜딩 버튼 사용 여부
    const chkMsgBackDimTf = document.querySelector('#msgBackDimTf');            // 화면 dim처리 사용 여부
    const chkMsgSaveTf = document.querySelector('#msgSaveTf');                  // 메시지 저장 사용 여부

    const spanContentLength = document.querySelector('#contentLength');         // 서브 문구 글자숫자 표시
    
    const viewImgs = document.querySelectorAll('.board-cover');                  // 이미지 선택시 미리보기

    return {
        init: function() {
            btnSave.addEventListener('click', this.save);
            fileMsgImgRnm01.addEventListener('change', this.imageView);
            txtMsgImgAlter01.addEventListener('change', this.setImageAlter);
            txtMsgTitle.addEventListener('keyup', this.setMsgTitle);
            txtMsgTitle.addEventListener('blur', this.setMsgTitle);
            txtMsgTitleColor.addEventListener('change', this.setMsgTitle);
            txtMsgContents.addEventListener('keyup', this.setMsgContents);
            txtMsgContents.addEventListener('blur', this.setMsgContents);
            txtMsgContentsColor.addEventListener('change', this.setMsgContents);

            chkMsgButtonTf.addEventListener('click', this.setDisplay);

            this.setMsgTitle(txtMsgTitleColor);
            this.setMsgContents(txtMsgContentsColor);

            this.setDisplay(chkMsgButtonTf);
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
        setImageAlter: function() {
            viewImgs.forEach(element => {
                element.firstElementChild.alt = txtMsgImgAlter01.value;
            });
        },
        setMsgTitle: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            document.querySelectorAll('.board-name').forEach(element => {
                if(elem.id == 'msgTitle') {
                    element.textContent = txtMsgTitle.value;
                } else if(elem.id == 'msgTitleColor') {
                    element.style.color = txtMsgTitleColor.value;
                }
            });
        },
        setContentsRow: function(e) {
            let textStr = txtMsgContents.value;
            let numberOfLines = textStr.split('\n').length;
            let maxRows = e.target.rows;

            if(numberOfLines > maxRows) {
                let lastChar = textStr.slice(0, -1);
                txtMsgContents.value = lastChar;
            }
        },
        setMsgContents: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            const cnt = txtMsgContents.value.length;
            spanContentLength.textContent = String(cnt);

            document.querySelectorAll('.board-inform').forEach(element => {
            const view = element.querySelector('.board-summary');
                if(elem.id == 'msgContents') {
                    campaignMessageMini.setContentsRow(e);
                    view.innerHTML = txtMsgContents.value.replaceAll('\n', '<br />');
                } else if(elem.id == 'msgContentsColor') {
                    view.style.color = txtMsgContentsColor.value;
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
            } else if(elem.id == 'msgTitleTf') {
                document.querySelectorAll('.board-inform').forEach(element => {
                    const view = element.querySelector('.board-subject');
                    if(elem.checked) view.style.display = 'block'; else view.style.display = 'none';
                });
            } else if(elem.id == 'msgContentsTf') {
                document.querySelectorAll('.board-inform').forEach(element => {
                    const view = element.querySelector('.board-summary');
                    if(elem.checked) view.style.display = 'block'; else view.style.display = 'none';
                });
            } else if(elem.id == 'msgButtonTf') {
                document.querySelectorAll('.type-cta.attr-move').forEach(element => {
                    if(elem.checked) element.style.display = 'inline-flex'; else element.style.display = 'none';
                });
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
            if (fileMsgImgRnm01.value != '') {
                let fileLength = fileMsgImgRnm01.value.length;
                let fileDot = fileMsgImgRnm01.value.lastIndexOf('.');
                let fileType = fileMsgImgRnm01.value.substring(fileDot + 1, fileLength).toLowerCase();

                if (constants.imageType.indexOf(fileType) == -1) {
                    await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                    return false;
                }
            }
            // 이미지 대체 문구
            if(txtMsgImgAlter01.value == '') { await Modal.alert('이미지 대체 텍스트를 입력해 주세요.'); return false; }
            // 메인 문구
            if(txtMsgTitle.value == '') { await Modal.alert('메인 문구 내용을 입력해 주세요.'); return false; }
            // 서브 문구
            if(txtMsgContents.value == '') { await Modal.alert('서브 문구 내용을 입력해 주세요.'); return false; }
            // CTA 버튼
            if(chkMsgButtonTf.checked) {
                if(txtMsgButtonMoUrl.value == '' && txtMsgButtonPcUrl.value == '') {
                    await Modal.alert('랜딩 페이지 URL을 입력해 주세요.'); return false;
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
            data.append('msgTitleTf', 'Y');
            data.append('msgTitle', txtMsgTitle.value);
            data.append('msgTitleColor', txtMsgTitleColor.value);
            data.append('msgContentsTf', 'Y');
            data.append('msgContents', txtMsgContents.value);
            data.append('msgContentsColor', txtMsgContentsColor.value);
            data.append('msgButtonTf', (chkMsgButtonTf.checked)? 'Y' : 'N');
            if(chkMsgButtonTf.checked) {
                data.append('msgButtonPcUrl', txtMsgButtonPcUrl.value);
                data.append('msgButtonMoUrl', txtMsgButtonMoUrl.value);
                data.append('msgButtonUrlTargetP', radMsgButtonUrlTargetP.value);
                data.append('msgButtonUrlTargetM', radMsgButtonUrlTargetM.value);
            }
            data.append('msgBackDimTf', (chkMsgBackDimTf.checked)? 'Y' : 'N');
            data.append('msgSaveTf', (chkMsgSaveTf.checked)? 'Y' : 'N');
            if(chkMsgSaveTf.checked) {
                data.append('msgName', txtMsgSaveTitle.value);
            }
            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageMini.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageMini.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);
            if(chkMsgSaveTf.checked) {
                await capture.img(document.querySelector("#campaignMessagePreviewLaptop .campaign-popup"), (img) => {
                    data.append('msgCapture', img);
                });
            }

            let msgActionEffectType = document.querySelector('input[name="effect-0001"]:checked').value ?? 'a';

            data.append('msgActionEffectType', msgActionEffectType);

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
            Popup.open(`/campaign/popup/historyMessage?acl=${acl}&msgType=${msgType}`, 'contentPopupMessageMini');
        },
        saveTemp: async function() {
            const data = new FormData();

            const radMsgButtonUrlTargetP = document.querySelector('input[name=msgButtonUrlTargetP]:checked');
            const radMsgButtonUrlTargetM = document.querySelector('input[name=msgButtonUrlTargetM]:checked');

            // 이미지 형식체크 2023-04-12 보안점검
            if (fileMsgImgRnm01.value != '') {
                let fileLength = fileMsgImgRnm01.value.length;
                let fileDot = fileMsgImgRnm01.value.lastIndexOf('.');
                let fileType = fileMsgImgRnm01.value.substring(fileDot + 1, fileLength).toLowerCase();

                if (constants.imageType.indexOf(fileType) == -1) {
                    await Modal.alert('이미지 파일만 업로드 가능 합니다.(png, jpg, jpeg, gif)');
                    return false;
                }
            }

            data.append('msgImgTf', 'Y');
            data.append('file', fileMsgImgRnm01.files[0]);
            data.append('msgImgRnm01', document.querySelector('#hidMsgImgRnm01').value);
            data.append('msgImgNm01', document.querySelector('#msgImgNm01').value);
            data.append('msgImgAlter01', txtMsgImgAlter01.value);
            data.append('msgTitleTf', 'Y');
            data.append('msgTitle', txtMsgTitle.value);
            data.append('msgTitleColor', txtMsgTitleColor.value);
            data.append('msgContentsTf', 'Y');
            data.append('msgContents', txtMsgContents.value);
            data.append('msgContentsColor', txtMsgContentsColor.value);
            data.append('msgButtonTf', (chkMsgButtonTf.checked)? 'Y' : 'N');
            if(chkMsgButtonTf.checked) {
                data.append('msgButtonPcUrl', txtMsgButtonPcUrl.value);
                data.append('msgButtonMoUrl', txtMsgButtonMoUrl.value);
                data.append('msgButtonUrlTargetP', radMsgButtonUrlTargetP.value);
                data.append('msgButtonUrlTargetM', radMsgButtonUrlTargetM.value);
            }
            data.append('msgBackDimTf', (chkMsgBackDimTf.checked)? 'Y' : 'N');
            data.append('msgSaveTf', (chkMsgSaveTf.checked)? 'Y' : 'N');
            if(chkMsgSaveTf.checked) {
                data.append('msgName', txtMsgSaveTitle.value);
            }
            data.append('caNo.id', document.querySelector('#caNo').value);
            data.append('msgType', document.querySelector('#msgType').value);
            data.append('id', document.querySelector('#msgId').value);
            const pcHtml = campaignMessageMini.setReplaceSrc(document.querySelector('#campaignMessagePreviewLaptop .campaign-popup').outerHTML);
            const moHtml = campaignMessageMini.setReplaceSrc(document.querySelector('#campaignMessagePreviewMobile .campaign-popup').outerHTML);
            data.append('msgPcHtml', pcHtml);
            data.append('msgMoHtml', moHtml);

            let msgActionEffectType = document.querySelector('input[name="effect-0001"]:checked').value ?? 'a';

            data.append('msgActionEffectType', msgActionEffectType);
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

campaignMessageMini.init();