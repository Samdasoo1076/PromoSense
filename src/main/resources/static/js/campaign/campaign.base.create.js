const campaignBaseCreate = (() => {
    let timeValue = '';

    const btnStartTimePicker = document.querySelector('#btnStartTimePicker');   // 타임피커 start
    const btnEndTimePicker = document.querySelector('#btnEndTimePicker');       // 타임피커 end
    const btnTg1SpecUrlP = document.querySelector('#btnTg1SpecUrlP');           // 1차 타겟 특정 경로로 유입 관련 추가버튼(pc url)
    const btnTg1SpecUrlM = document.querySelector('#btnTg1SpecUrlM');           // 1차 타겟 특정 경로로 유입 관련 추가버튼(mobile url)
    const btnTg2SpecUrlP = document.querySelector('#btnTg2SpecUrlP');           // 2차 타겟 특정 경로로 유입 관련 추가버튼(pc url)
    const btnTg2SpecUrlM = document.querySelector('#btnTg2SpecUrlM');           // 2차 타겟 특정 경로로 유입 관련 추가버튼(mobile url)
    const btnTimeInit = document.querySelector('#btnTimeInit');                 // 시간설정 초기화
    const btnCaExposureUrlP = document.querySelector('#btnCaExposureUrlP');     // 노출 위치 특정 페이지 제외 추가버튼(pc url)
    const btnCaExposureUrlM = document.querySelector('#btnCaExposureUrlM');     // 노출 위치 특정 페이지 제외 추가버튼(mobile url)
    const btnCaExposureAppointUrlP = document.querySelector('#btnCaExposureAppointUrlP'); // 노출 위치 특정 페이지 지정 추가버튼(pc url)
    const btnCaExposureAppointUrlM = document.querySelector('#btnCaExposureAppointUrlM'); // 노출 위치 특정 페이지 지정 추가버튼(pc url)
    const btnSave = document.querySelector('#btnSave');                         // 저장 버튼

    const txtStartTime = document.querySelector('#startTime');                  // timePicker 시작시간
    const txtStartMinute = document.querySelector('#startMinute');              // timePicker 시작분
    const txtEndTime = document.querySelector('#endTime');                      // timePicker 종료시간
    const txtEndMinute = document.querySelector('#endMinute');                  // timePicker 종료분
    const txtCaName = document.querySelector('#caName');                        // 캠페인 명
    const txtCaMemo = document.querySelector('#caMemo');                        // 캠페인 설명
    const txtCaStartDateStr = document.querySelector('#caStartDateStr');              // 캠페인 시작일
    const txtCaStartTime = document.querySelector('#caStartTime');              // 캠페인 시작 시분
    const txtCaEndDateStr = document.querySelector('#caEndDateStr');                  // 캠페인 종료일
    const txtCaEndTime = document.querySelector('#caEndTime');                  // 캠페인 종료 시분
    const txtTargetUrlP = document.querySelector('#targetUrlP');                // 캠페인 목표 전환 지점 설정(pc url)
    const txtTargetUrlM = document.querySelector('#targetUrlM');                // 캠페인 목표 전환 지점 설정(mobile url)
    const txtRevisitUrlP = document.querySelector('#revisitUrlP');              // 재방문시 url(pc url)
    const txtRevisitUrlM = document.querySelector('#revisitUrlM');              // 재방문시 url(mobile url)
    const txtTg2RevisitUrlP = document.querySelector('#tg2RevisitUrlP');        // 2차 재방문시 url(pc url) (추가 부분)
    const txtTg2RevisitUrlM = document.querySelector('#tg2RevisitUrlM');        // 2차 재방문시 url(mobile url) (추가 부분)
    const txtTg1FixedWord = document.querySelector('#tg1FixedWord');            // 1차 타겟 특정 검색어 유입
    const txtTg2FixedWord = document.querySelector('#tg2FixedWord');            // 2차 타겟 특정 검색어 유입

    const chkCaTermAllDay = document.querySelector('#chkCaTermAllDay');         // 캠페인 노출기간 종일여부
    const chkTarget2 = document.querySelector('#chkTarget2');                   // 2차 타겟 선택시
    const chkCaWeeks = document.querySelectorAll('#caWeek');                    // 노출 정보 요일
    const chkCaTimeSubs = document.querySelectorAll('#caTimeSub');              // 노출 정보 시간
    const chkCaPlaceFixed = document.querySelector('#chkCaPlaceFixed');         // 노출 위치 특정 페이지 제외 버튼

    const divTarget1View = document.querySelector('#target1View');              // 1타겟 선택시 화면 설정
    const divReturnView = document.querySelector('#returnView');                // 재방문 고개 선택시
    const divTg2ReturnView = document.querySelector('#tg2ReturnView');          // 2타겟 재방문 고개 선택시  (추가 부분)
    const divFixedView = document.querySelector('#fixedView');                  // 특정 경로로 유입한 고객 선택시
    const divTarget1Fixed = document.querySelector('#target1Fixed');            // 1차 타겟에서 특정 경로유입시 2차타겟 화면 제어
    const divTarget1Other = document.querySelector('#target1Other');            // 1차 타겟에서 나머지유입시 2차타겟 화면 제어
    const divTimeDetailView = document.querySelector('#timeDetailView');        // 노출시간 상세 화면 제어
    const divCaPlaceFixed = document.querySelector('#divCaPlaceFixed');         // 노출 위치 제외버튼 화면 제어
    const divFixedPageExceptP = document.querySelector('#divFixedPageExceptP'); // 노출 위치 특정 페이지 제외 웹url 화면 제어
    const divFixedPageExceptM = document.querySelector('#divFixedPageExceptM'); // 노출 위치 특정 페이지 제외 모바일url 화면 제어
    const divFixedPageAppointP = document.querySelector('#divFixedPageAppointP'); // 노출 위치 특정 페이지 지정 웹url 화면 제어
    const divFixedPageAppointM = document.querySelector('#divFixedPageAppointM'); // 노출 위치 특정 페이지 지정 모바일url 화면 제어
    const divCaViewPointPercent = document.querySelector('#divCaViewPointPercent'); // 노출 시점 페센트 화면 제어
    const divCaViewPointSecond = document.querySelector('#divCaViewPointSecond'); // 노출 시점 초 화면 제어
    const divCaViewPointOut = document.querySelector('#divCaViewPointOut');     // 노출 시점 이탈 화면 제어

    const radTgType1s = document.querySelectorAll('#tgType1');                  // 1차 타겟
    const radTgType2Fixeds = document.querySelectorAll('#tgType2Fixed');        // 2차 타겟
    const radCaTimes = document.querySelectorAll('#caTime');                    // 노출 시간
    const radCaPlaces = document.querySelectorAll('#caPlace');                  // 노출 위치
    const radCaViewPoints = document.querySelectorAll('#caViewPoint');          // 노출 시점

    return {
        init: function() {
            btnStartTimePicker.addEventListener('click', this.setStartTime);
            btnEndTimePicker.addEventListener('click', this.setEndTime);
            // 항목이 있을때만 이벤트 적용 03-31
            if(btnTg1SpecUrlP) {
                btnTg1SpecUrlP.addEventListener('click', this.viewUrlAdd);
            }
            if(btnTg1SpecUrlM) {
                btnTg1SpecUrlM.addEventListener('click', this.viewUrlAdd);
            }
            if(btnTg2SpecUrlP) {
                btnTg2SpecUrlP.addEventListener('click', this.viewUrlAdd);
            }
            if(btnTg2SpecUrlM) {
                btnTg2SpecUrlM.addEventListener('click', this.viewUrlAdd);
            }
            btnTimeInit.addEventListener('click', this.timeReset);
            btnCaExposureUrlP.addEventListener('click', this.viewUrlAdd);
            btnCaExposureUrlM.addEventListener('click', this.viewUrlAdd);
            btnCaExposureAppointUrlP.addEventListener('click', this.viewUrlAdd);
            btnCaExposureAppointUrlM.addEventListener('click', this.viewUrlAdd);
            btnSave.addEventListener('click', this.save);

            chkCaTermAllDay.addEventListener('click', this.viewTermAllDay);
            chkCaPlaceFixed.addEventListener('click', this.viewCaPlaceExcept);

            radTgType1s.forEach(element => {
                element.addEventListener('click', this.viewTarget);
            });

            radTgType2Fixeds.forEach(element => {  // 추가 부분
                element.addEventListener('change', this.viewTarget2);
            });

            radCaTimes.forEach(element => {
                element.addEventListener('click', this.viewCaTime);
            });

            radCaPlaces.forEach(element => {
                element.addEventListener('click', this.viewCaPlace);
            });

            radCaViewPoints.forEach(element => {
                element.addEventListener('click', this.viewCaViewPoint);
            });

            const today = new Date();
            txtCaStartDateStr.value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
                + '-' + ('0' + today.getDate()).slice(-2);

            const monthAgo = new Date(today.setMonth(today.getMonth() + 3));
            txtCaEndDateStr.value = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                + '-' + ('0' + monthAgo.getDate()).slice(-2);

        },
        setStartTime: function() {
            txtCaStartTime.value = ((txtStartTime.value.length == 1)? '0' + txtStartTime.value : txtStartTime.value)
                + ':' + ((txtStartMinute.value.length == 1)? '0' + txtStartMinute.value : txtStartMinute.value);
            txtCaStartTime.parentElement.classList.remove('active');
        },
        setEndTime: function() {
            txtCaEndTime.value = ((txtEndTime.value.length == 1)? '0' + txtEndTime.value : txtEndTime.value)
                + ':' + ((txtEndMinute.value.length == 1)? '0' + txtEndMinute.value : txtEndMinute.value);
            txtCaEndTime.parentElement.classList.remove('active');
        },
        viewTermAllDay: function() {
            if(chkCaTermAllDay.checked) {
                txtCaStartTime.disabled = true;
                txtCaEndTime.disabled = true;
            } else {
                txtCaStartTime.disabled = false;
                txtCaEndTime.disabled = false;
            }

        },
        viewTarget: function (e) {
            if(e.target.value == 'RETURN') {
                divTarget1View.style.display = 'block';

                divReturnView.style.display = 'block';
                divTg2ReturnView.style.display = 'none';  // 추가 부분
                divFixedView.style.display = 'none';
                divTarget1Fixed.style.display = 'none';
                divTarget1Other.style.display = 'block';
            } else if(e.target.value == 'FIXED') {
                divTarget1View.style.display = 'block';

                divReturnView.style.display = 'none';
                divFixedView.style.display = 'block';
                divTarget1Fixed.style.display = 'block';
                divTarget1Other.style.display = 'none';

                if(document.querySelector('input[name=tgType2Fixed]:checked').value == 'RETURN') { // 추가 부분
                    divTg2ReturnView.style.display = 'block';
                } else {
                    divTg2ReturnView.style.display = 'none';
                }
            } else {
                divTarget1View.style.display = 'none';
                divTg2ReturnView.style.display = 'none';  // 추가 부분
                divTarget1Fixed.style.display = 'none';
                divTarget1Other.style.display = 'block';
            }
        },
        viewTarget2: function(e) { // 추가 부분
            if(e.target.value == 'RETURN') {
                divTg2ReturnView.style.display = 'block';
            } else {
                divTg2ReturnView.style.display = 'none';
            }
        },
        viewCaTime: function(e) {
            timeValue = e.target.getAttribute('data');
            const time = timeValue.split(':');

            chkCaTimeSubs.forEach(elem => {
                const timeSub = elem.value.split(':');

                if(time[0] != 'null') {
                    if(parseInt(time[0]) <= parseInt(timeSub[0])) elem.checked = true; else elem.checked = false;
                    if(time[0] > time[1]) {
                        if(parseInt(time[1]) >= parseInt(timeSub[1])) elem.checked = true;
                    } else {
                        if(parseInt(time[1]) < parseInt(timeSub[1])) elem.checked = false;
                        if(time[1] != '23') {
                            if(timeSub[1] == '00') elem.checked = false;
                        }
                    }
                }
            });

            if(time[0] == 'null') {
                divTimeDetailView.style.display = 'block';
            } else {
                divTimeDetailView.style.display = 'none';
            }
        },
        viewCaPlaceExcept: function() {
            const radCaPlace = document.querySelector('input[name="caPlace"]:checked');

            divCaPlaceFixed.style.display = 'block';
            divFixedPageAppointP.style.display = 'none';
            divFixedPageAppointM.style.display = 'none';

            if(chkCaPlaceFixed.checked) {
                if(radCaPlace.value == 'ALL_PAGE') {
                    divFixedPageExceptP.style.display = 'block';
                    divFixedPageExceptM.style.display = 'block';
                } else if(radCaPlace.value == 'PC_ONLY') {
                    divFixedPageExceptP.style.display = 'block';
                    divFixedPageExceptM.style.display = 'none';
                } else if(radCaPlace.value == 'MOBILE_ONLY') {
                    divFixedPageExceptP.style.display = 'none';
                    divFixedPageExceptM.style.display = 'block';
                }
            } else {
                divFixedPageExceptP.style.display = 'none';
                divFixedPageExceptM.style.display = 'none';
            }
        },
        viewCaPlace: function(e) {
            if(e.target.value == 'FIXED_PAGE') {
                divFixedPageAppointP.style.display = 'block';
                divFixedPageAppointM.style.display = 'block';

                divFixedPageExceptP.style.display = 'none';
                divFixedPageExceptM.style.display = 'none';
                divCaPlaceFixed.style.display = 'none';
            } else {
                campaignBaseCreate.viewCaPlaceExcept();
            }
        },
        viewCaViewPoint: function(e) {
            if(e.target.value == 'PERCENT') {
                divCaViewPointPercent.style.display = 'block';
                divCaViewPointSecond.style.display = 'none';
                divCaViewPointOut.style.display = 'none';
            } else if(e.target.value == 'NONE_MOVE' || e.target.value == 'LOADING') {
                divCaViewPointPercent.style.display = 'none';
                divCaViewPointSecond.style.display = 'block';
                divCaViewPointOut.style.display = 'none';
            } else if(e.target.value == 'BEFORE_OUT') {
                divCaViewPointPercent.style.display = 'none';
                divCaViewPointSecond.style.display = 'none';
                divCaViewPointOut.style.display = 'block';
            } else {
                divCaViewPointPercent.style.display = 'none';
                divCaViewPointSecond.style.display = 'none';
                divCaViewPointOut.style.display = 'none';
            }
        },
        viewUrlAdd: function(e) {
            const viewTargetDraw = e.target.parentNode.parentNode;
            const viewCopy = e.target.parentNode.cloneNode(true);
            const elButton = viewCopy.lastElementChild;
            const btnClild = elButton.firstElementChild;
            const spanChild = elButton.previousElementSibling;

            spanChild.firstElementChild.value = '';
            spanChild.firstElementChild.parentElement.classList.remove('typed');
            spanChild.lastElementChild.firstElementChild.remove();
            elButton.className = elButton.className.replace('attr-plus', 'attr-minus');
            elButton.setAttribute('onclick', 'campaignBaseCreate.viewUrlMinus(this);');
            btnClild.textContent = '삭제';

            viewTargetDraw.lastElementChild.before(viewCopy);

            const formModuleSM = new buiForm(".form.module-b .form-elem[type='text'], .form.module-b .form-elem[type='url'], .form.module-b .form-elem[type='search'], .form.module-b.textarea .form-elem");
            formModuleSM.update();
        },
        viewUrlMinus: function(elem) {
            elem.parentNode.remove();
        },
        timeReset: function() {
            chkCaTimeSubs.forEach(elem => {
                elem.checked = false;
            });
        },
        makeDataAndVaild: function() {
            const data = {};

            const radCaGubun = document.querySelector('input[name=caGubun]:checked');
            const radCaPurpose = document.querySelector('input[name=caPurpose]:checked');
            const radTgType1 = document.querySelector('input[name=tgType1]:checked');
            const radTgType2Other = document.querySelector('input[name=tgType2Other]:checked');
            const radTargetReturn = document.querySelector('input[name=targetReturn]:checked');
            const radTargetFixed = document.querySelector('input[name=targetFixed]:checked');
            const radTgType2Fixed = document.querySelector('input[name=tgType2Fixed]:checked');
            const radTg2TargetReturn = document.querySelector('input[name=tg2TargetReturn]:checked');   // 추가 부분
            const radCaTime = document.querySelector('input[name=caTime]:checked');                    // 노출 시간
            const radCaPlace = document.querySelector('input[name=caPlace]:checked');
            const radCaViewPoint = document.querySelector('input[name=caViewPoint]:checked');
            const radCaViewSubPercent = document.querySelector('input[name=caViewSubPercent]:checked');
            const radCaViewSubSecond = document.querySelector('input[name=caViewSubSecond]:checked');
            const radCaViewSubOut = document.querySelector('input[name=caViewSubOut]:checked');
            const radExposureLimitCnt = document.querySelector('input[name=exposureLimitCnt]:checked');
            const radCaMsgType = document.querySelector('input[name=caMsgType]:checked');

            const txtTg1SpecUrlPs = document.querySelectorAll('#tg1SpecUrlP');
            const txtTg1SpecUrlMs = document.querySelectorAll('#tg1SpecUrlM');
            const txtTg2SpecUrlPs = document.querySelectorAll('#tg2SpecUrlP');
            const txtTg2SpecUrlMs = document.querySelectorAll('#tg2SpecUrlM');
            const txtCaExposureAppointUrlPs = document.querySelectorAll('#caExposureAppointUrlP');
            const txtCaExposureAppointUrlMs = document.querySelectorAll('#caExposureAppointUrlM');
            const txtCaExposureUrlPs = document.querySelectorAll('#caExposureUrlP');
            const txtCaExposureUrlMs = document.querySelectorAll('#caExposureUrlM');

            // 캐페인 명
            if(txtCaName.value == '') { Modal.alert('캠페인명을 입력해 주세요.'); return false; }
            // 캠페인 시작일
            if(txtCaStartDateStr.value == '') { Modal.alert('캠페인 시작일을 입력해 주세요'); return false; }
            // 캠페인 종료일
            if(txtCaEndDateStr.value == '') { Modal.alert('캠페인 종료일을 입력해 주세요'); return false; }
            if(new Date(txtCaStartDateStr.value) > new Date(txtCaEndDateStr.value)) {
                Modal.alert('캠페인 기간을 정확히 입력해 주세요.'); return false;
            }
            if(!chkCaTermAllDay.checked) { // 종일이 아닌경우
                // 시작 시간
                if(txtCaStartTime.value == '') { Modal.alert('캐페인 시작시간을 입력해 주세요.'); return false; }
                // 종료 시간
                if(txtCaEndTime.value == '') { Modal.alert('캐페인 종료시간을 입력해 주세요.'); return false; }
                if(new Date(txtCaStartDateStr.value + ' ' + txtCaStartTime.value + ':00') >=
                    new Date(txtCaEndDateStr.value + ' ' + txtCaEndTime.value + ':00')) {
                    Modal.alert('캠페인 시간을 정확히 입력해 주세요.'); return false;
                }
            }

            // 목표전환지점 설정 미선택 03-31
            if(radCaPurpose == null) { Modal.alert('캠페인 목표 전환 지점 설정을 선택해 주세요.'); return false; }
            // 목표전환지점 설정
            if(radCaPurpose.value == 'ETC') {
                if(txtTargetUrlP.value == '' && txtTargetUrlM.value == '') { Modal.alert('캠페인 목표 전환 지점 설정의 상세 정보를 입력해 주세요.'); return false; }
            }

            // 1차 타겟 미선택 03-31
            if(radTgType1 == null) { Modal.alert('1차 타겟을 선택해 주세요.'); return false; }
            // 1차타겟 재방문 고객인 경우
            if(radTgType1.value == 'RETURN') {
                // 1차 타겟 서브 미선택시 03-31
                if(radTargetReturn == null) { Modal.alert('1차 타겟 상세정보를 선택해 주세요.'); return false; }
                if(radTargetReturn.value == 'TAR1_FIXED') {
                    if(txtRevisitUrlP.value == '' && txtRevisitUrlM.value == '') { Modal.alert('재방문 고객 타겟의 상세 정보를 입력해 주세요.'); return false;}
                }
            }
            // 1차타겟 특정경로 유입고객인 경우
            if(radTgType1.value == 'FIXED') {
                // 1차 타겟 서브 미선택시 03-31
                if(radTargetFixed == null) { Modal.alert('1차 타겟 상세정보를 선택해 주세요.'); return false; }
                if(radTargetFixed.value == 'OUTLINK') {
                    let isSpecUrlP = true;
                    let isSpecUrlM = true;
                    txtTg1SpecUrlPs.forEach(element => {
                        if(element.value != '') { isSpecUrlP = false; return false; }
                    });
                    txtTg1SpecUrlMs.forEach(element => {
                        if(element.value != '') { isSpecUrlM = false; return false; }
                    });
                    if(isSpecUrlP && isSpecUrlM) { Modal.alert('특정 경로로 유입한 고객 타겟의 상세 정보를 입력해 주세요.'); return false; }
                } else {
                    if(txtTg1FixedWord.value == '') { Modal.alert('특정 경로로 유입한 고객 타겟의 상세 정보를 입력해 주세요.'); return false; }
                }
            }
            // 2차타겟이 체크 된경우
            if(chkTarget2.checked) {
                if(radTgType1.value != 'FIXED') {
                    // 2차 타겟 미선택 03-31
                    if(radTgType2Other == null) { Modal.alert('2차 타겟을 선택해 주세요.'); return false; }
                    if(radTgType2Other.value == 'OUTLINK') {
                        let isSpecUrlP = true;
                        let isSpecUrlM = true;
                        txtTg2SpecUrlPs.forEach(element => {
                            if (element.value != '') { isSpecUrlP = false; return false; }
                        });
                        txtTg2SpecUrlMs.forEach(element => {
                            if (element.value != '') { isSpecUrlM = false; return false; }
                        });
                        if (isSpecUrlP && isSpecUrlM) { Modal.alert('특정 경로로 유입한 고객 타겟의 상세 정보를 입력해 주세요.'); return false; }
                    } else {
                        if (txtTg2FixedWord.value == '') { Modal.alert('특정 경로로 유입한 고객 타겟의 상세 정보를 입력해 주세요.'); return false; }
                    }
                } else {  // 추가 부분
                    // 2차 타겟 미선택 03-31
                    if(radTgType2Fixed == null) { Modal.alert('2차 타겟을 선택해 주세요.'); return false; }
                    if(radTgType2Fixed.value == 'RETURN') {
                        // 2차 타겟 상세정보를 미선택 03-31
                        if(radTg2TargetReturn == null) { Modal.alert('2차 타겟 상세정보를 선택해 주세요.'); return false; }
                        if(radTg2TargetReturn.value == 'TAR1_FIXED') {
                            if(txtTg2RevisitUrlP.value == '' && txtTg2RevisitUrlM.value == '') { Modal.alert('재방문 고객 타겟의 상세 정보를 입력해 주세요.'); return false;}
                        }
                    }
                }
            }
            // 노출 요일
            let isWeekChecked = true;
            chkCaWeeks.forEach(element => {
                if(element.checked) { isWeekChecked = false; return false; }
            });
            if(isWeekChecked) { Modal.alert('메시지 노출 요일을 선택해 주세요.'); return false; }
            // 노출 시간
            let isTimeChecked = true;
            // 메시지 노출시간 미선택 03-31
            if(radCaTime == null) { Modal.alert('메시지 노출 시간을 선택해 주세요.'); return false; }
            chkCaTimeSubs.forEach(element => {
                if(element.checked) { isTimeChecked = false; return false; }
            });
            if(isTimeChecked) { Modal.alert('메시지 노출 시간을 선택해 주세요.'); return false; }
            // 노출 위치
            // 노출 위치 미선택 03-31
            if(radCaPlace == null) { Modal.alert('메시지 노출 위치를 선택해 주세요.'); return false; }
            if(radCaPlace.value == 'FIXED_PAGE') {
                let isExposureAppointUrlP = true;
                let isExposureAppointUrlM = true;
                txtCaExposureAppointUrlPs.forEach(element => {
                    if (element.value != '') { isExposureAppointUrlP = false; return false; }
                });
                txtCaExposureAppointUrlMs.forEach(element => {
                    if (element.value != '') { isExposureAppointUrlM = false; return false; }
                });
                if (isExposureAppointUrlP && isExposureAppointUrlM) { Modal.alert('메시지 노출 위치의 상세 정보를 입력해 주세요.'); return false; }
            } else {
                if(chkCaPlaceFixed.checked) {
                    let isExposureUrlP = true;
                    let isExposureUrlM = true;
                    txtCaExposureUrlPs.forEach(element => {
                        if (element.value != '') { isExposureUrlP = false; return false; }
                    });
                    txtCaExposureUrlMs.forEach(element => {
                        if (element.value != '') { isExposureUrlM = false; return false; }
                    });
                    if(radCaPlace.value == 'ALL_PAGE') {
                        if (isExposureUrlP && isExposureUrlM) { Modal.alert('메시지 노출 위치의 상세 정보를 입력해 주세요.'); return false; }
                    } else if(radCaPlace.value == 'MOBILE_ONLY') {
                        if (isExposureUrlM) { Modal.alert('메시지 노출 위치의 상세 정보를 입력해 주세요.'); return false; }
                    } else if(radCaPlace.value == 'PC_ONLY') {
                        if (isExposureUrlP) { Modal.alert('메시지 노출 위치의 상세 정보를 입력해 주세요.'); return false; }
                    }
                }
            }
            // 노출 시점 미선택 03-31
            if(radCaViewPoint == null) { Modal.alert('메시지 노출 시점을 선택해 주세요.'); return false; }
            if(radCaViewPoint.value == 'PERCENT') {
                if(radCaViewSubPercent == null) { Modal.alert('메시지 노출 시점 상세정보를 선택해 주세요.'); return false; }
            }
            if(radCaViewPoint.value == 'LOADING' || radCaViewPoint.value == 'NONE_MOVE') {
                if(radCaViewSubSecond == null) { Modal.alert('메시지 노출 시점 상세정보를 선택해 주세요.'); return false; }
            }
            if(radCaViewPoint.value == 'BEFORE_OUT') {
                if(radCaViewSubOut == null) { Modal.alert('메시지 노출 시점 상세정보를 선택해 주세요.'); return false; }
            }
            // 노출 빈도 미선택 03-31
            if(radExposureLimitCnt == null) { Modal.alert('메시지 노출 노출빈도를 선택해 주세요.'); return false; }
            // 노출 빈도 미선택 03-31
            if(radCaMsgType == null) { Modal.alert('메시지 유형을 선택해 주세요.'); return false; }

            data['caGubun'] = radCaGubun.value;                             // 캠페인 구분
            data['caName'] = txtCaName.value;                               // 캠페인 명
            data['caMemo'] = txtCaMemo.value;                               // 캠페인 설명
            data['caStartDateStr'] = txtCaStartDateStr.value;                     // 시작일
            data['caEndDateStr'] = txtCaEndDateStr.value;                         // 종료일
            if(!chkCaTermAllDay.checked) {                                  // 종일 checkbox가 체크 해제된 경우 사용
                data['caStartHour'] = txtCaStartTime.value.split(':')[0];   // 시작시
                data['caStartMin'] = txtCaStartTime.value.split(':')[1];    // 시작분
                data['caEndHour'] = txtCaEndTime.value.split(':')[0];       // 종료시
                data['caEndMin'] = txtCaEndTime.value.split(':')[1];        // 종료분
            }
            data['caAlldayYn'] = (chkCaTermAllDay.checked)? 'Y' : 'N';      // 종일 여부
            data['caPurpose'] = radCaPurpose.value;                         // 캠페인 목표 전환 지점 설정값

            // 선택 안함이 아닌경우
            if(radCaPurpose.value != 'NONE') {
                const targetUrls = [];
                if (radCaPurpose.value == 'ONLINE') { // 온라인 상담 가입상담 신청 완료 (PC/Mobile) 선택시
                    let targetUrldata = {}
                    targetUrldata['gubun'] = 'P';
                    if(radCaGubun.value == 'BD') targetUrldata['targetUrl'] = constants.pcPurposeUrl;
                    else if(radCaGubun.value == 'BW') targetUrldata['targetUrl'] = constants.pcPurposeUrlBW;
                    else targetUrldata['targetUrl'] = constants.pcPurposeUrlTB;
                    targetUrls.push(targetUrldata);
                    
                    targetUrldata = {}
                    targetUrldata['gubun'] = 'M';
                    if(radCaGubun.value == 'BD') targetUrldata['targetUrl'] = constants.mobilePurposeUrl;
                    else if(radCaGubun.value == 'BW') targetUrldata['targetUrl'] = constants.mobilePurposeUrlBW;
                    else targetUrldata['targetUrl'] = constants.mobilePurposeUrlTB;
                    targetUrls.push(targetUrldata);
                } else if (radCaPurpose.value == 'ETC') { //기타 페이지 도달 완료 선택시
                    let targetUrldata = {}
                    if(txtTargetUrlP.value != '') {
                        targetUrldata['gubun'] = 'P';
                        targetUrldata['targetUrl'] = txtTargetUrlP.value;
                        targetUrls.push(targetUrldata);
                    }

                    if(txtTargetUrlM.value != '') {
                        targetUrldata = {}
                        targetUrldata['gubun'] = 'M';
                        targetUrldata['targetUrl'] = txtTargetUrlM.value;
                        targetUrls.push(targetUrldata);
                    }
                }
                data['campaignTargetUrlDtos'] = targetUrls;
            }

            // 1차 타겟 설정
            data['tgType01'] = radTgType1.value;
            if(radTgType1.value == 'RETURN') {
                data['tg1Sub'] = radTargetReturn.value;
                const revisitUrls = [];
                if(radTargetReturn.value == 'TAR1_FIXED') {
                    let revisitUrldata = {}
                    if(txtRevisitUrlP.value != '') {
                        revisitUrldata['gubun'] = 'P';
                        revisitUrldata['revisitUrl'] = txtRevisitUrlP.value;
                        revisitUrls.push(revisitUrldata);
                    }

                    if(txtRevisitUrlM.value != '') {
                        revisitUrldata = {}
                        revisitUrldata['gubun'] = 'M';
                        revisitUrldata['revisitUrl'] = txtRevisitUrlM.value;
                        revisitUrls.push(revisitUrldata);
                    }
                    data['campaignRevisitUrlDtos'] = revisitUrls;
                } else if(radTargetReturn.value == 'TAR1_ONLINE') {
                    let revisitUrldata = {}
                    revisitUrldata['gubun'] = 'P';
                    if(radCaGubun.value == 'BD') revisitUrldata['revisitUrl'] = constants.pcPurposeUrl;
                    else revisitUrldata['revisitUrl'] = constants.pcPurposeUrlTB;
                    revisitUrls.push(revisitUrldata);

                    revisitUrldata = {}
                    revisitUrldata['gubun'] = 'M';
                    if(radCaGubun.value == 'BD') revisitUrldata['revisitUrl'] = constants.mobilePurposeUrl;
                    else revisitUrldata['revisitUrl'] = constants.mobilePurposeUrlTB;
                    revisitUrls.push(revisitUrldata);
                    data['campaignRevisitUrlDtos'] = revisitUrls;
                }
            } else if(radTgType1.value == 'FIXED') {
                data['tg1Sub'] = radTargetFixed.value;
                if(radTargetFixed.value == 'OUTLINK') {
                    const specUrls = [];
                    txtTg1SpecUrlPs.forEach(element => {
                        if(element.value != '') {
                            let specUrlData = {};
                            specUrlData['gubun'] = 'P';
                            specUrlData['specUrl'] = element.value;
                            specUrls.push(specUrlData);
                        }
                    });
                    txtTg1SpecUrlMs.forEach(element => {
                        if(element.value != '') {
                            let specUrlData = {};
                            specUrlData['gubun'] = 'M';
                            specUrlData['specUrl'] = element.value;
                            specUrls.push(specUrlData);
                        }
                    });
                    data['campaignSpecUrlDtos'] = specUrls;
                } else {
                    data['tgFixedWord'] = txtTg1FixedWord.value;
                }
            }
            
            // 2차 타겟 설정
            data['tgType02Yn'] = (chkTarget2.checked)? 'Y' : 'N';
            if(chkTarget2.checked) {
                if(radTgType1.value == 'FIXED') {
                    data['tgType02'] = radTgType2Fixed.value;
                    if(radTgType2Fixed.value == 'RETURN') {  // 추가 부분
                        data['tg2Sub'] = radTg2TargetReturn.value;
                        const revisitUrls = [];
                        if(radTg2TargetReturn.value == 'TAR1_FIXED') {
                            let revisitUrldata = {}
                            if(txtTg2RevisitUrlP.value != '') {
                                revisitUrldata['gubun'] = 'P';
                                revisitUrldata['revisitUrl'] = txtTg2RevisitUrlP.value;
                                revisitUrls.push(revisitUrldata);
                            }
                            if(txtTg2RevisitUrlM.value != '') {
                                revisitUrldata = {}
                                revisitUrldata['gubun'] = 'M';
                                revisitUrldata['revisitUrl'] = txtTg2RevisitUrlM.value;
                                revisitUrls.push(revisitUrldata);
                            }
                            data['campaignRevisitUrlDtos'] = revisitUrls;
                        } else if(radTg2TargetReturn.value == 'TAR1_ONLINE') {
                            let revisitUrldata = {}
                            revisitUrldata['gubun'] = 'P';
                            if(radCaGubun.value == 'BD') revisitUrldata['revisitUrl'] = constants.pcPurposeUrl;
                            else revisitUrldata['revisitUrl'] = constants.pcPurposeUrlTB;
                            revisitUrls.push(revisitUrldata);

                            revisitUrldata = {}
                            revisitUrldata['gubun'] = 'M';
                            if(radCaGubun.value == 'BD') revisitUrldata['revisitUrl'] = constants.mobilePurposeUrl;
                            else revisitUrldata['revisitUrl'] = constants.mobilePurposeUrlTB;
                            revisitUrls.push(revisitUrldata);
                            data['campaignRevisitUrlDtos'] = revisitUrls;
                        }
                    }
                } else {
                    data['tgType02'] = 'FIXED';  // 추가 부분
                    data['tg2Sub'] = radTgType2Other.value;  // 추가 부분
                    if(radTgType2Other.value == 'OUTLINK') {
                        const specUrls = [];
                        txtTg2SpecUrlPs.forEach(element => {
                            if(element.value != '') {
                                let specUrlData = {};
                                specUrlData['gubun'] = 'P';
                                specUrlData['specUrl'] = element.value;
                                specUrls.push(specUrlData);
                            }
                        });
                        txtTg2SpecUrlMs.forEach(element => {
                            if(element.value != '') {
                                let specUrlData = {};
                                specUrlData['gubun'] = 'M';
                                specUrlData['specUrl'] = element.value;
                                specUrls.push(specUrlData);
                            }
                        });
                        data['campaignSpecUrlDtos'] = specUrls;
                    } else {
                        data['tgFixedWord'] = txtTg2FixedWord.value;
                    }
                }
            }
            // 메시지 노출 요일 설정
            let caWeeks = '';
            chkCaWeeks.forEach(element => {
                if(element.checked) { caWeeks += element.value + ','}
            });
            data['caWeek'] = caWeeks.slice(0, -1);
            // 메시지 노출 시간 설정
            data['caTime'] = radCaTime.value;
            const exposureTimes = [];
            chkCaTimeSubs.forEach(element => {
                if(element.checked) {
                    let exposureTimeData = {};
                    exposureTimeData['startTime'] = element.value.split(':')[0];
                    exposureTimeData['endTime'] = element.value.split(':')[1];
                    exposureTimes.push(exposureTimeData);
                }
            });
            data['campaignExposureTimeDtos'] = exposureTimes;
            // 메시지 노출 페이지 (위치)
            data['caPlace'] = radCaPlace.value;
            const exposureUrls = [];
            if(radCaPlace.value == 'FIXED_PAGE') {
                txtCaExposureAppointUrlPs.forEach(element => {
                    if(element.value != '') {
                        let exposureUrlData = {};
                        exposureUrlData['gubun'] = 'P';
                        exposureUrlData['includeYn'] = element.parentElement.previousElementSibling.firstElementChild.value;
                        exposureUrlData['exposureUrl'] = element.value;
                        exposureUrls.push(exposureUrlData);
                    }
                });
                txtCaExposureAppointUrlMs.forEach(element => {
                    if(element.value != '') {
                        let exposureUrlData = {};
                        exposureUrlData['gubun'] = 'M';
                        exposureUrlData['includeYn'] = element.parentElement.previousElementSibling.firstElementChild.value;
                        exposureUrlData['exposureUrl'] = element.value;
                        exposureUrls.push(exposureUrlData);
                    }
                });
                data['campaignExposureUrlDtos'] = exposureUrls;
            } else {
                data['caPlaceYn'] = (chkCaPlaceFixed.checked)? 'Y' : 'N';
                if(chkCaPlaceFixed.checked) {
                    if(radCaPlace.value == 'ALL_PAGE') {
                        txtCaExposureUrlPs.forEach(element => {
                            if(element.value != '') {
                                let exposureUrlData = {};
                                exposureUrlData['gubun'] = 'P';
                                exposureUrlData['includeYn'] = 'N';
                                exposureUrlData['exposureUrl'] = element.value;
                                exposureUrls.push(exposureUrlData);
                            }
                        });
                        txtCaExposureUrlMs.forEach(element => {
                            if(element.value != '') {
                                let exposureUrlData = {};
                                exposureUrlData['gubun'] = 'M';
                                exposureUrlData['includeYn'] = 'N';
                                exposureUrlData['exposureUrl'] = element.value;
                                exposureUrls.push(exposureUrlData);
                            }
                        });
                    } else if(radCaPlace.value == 'MOBILE_ONLY') {
                        txtCaExposureUrlMs.forEach(element => {
                            if(element.value != '') {
                                let exposureUrlData = {};
                                exposureUrlData['gubun'] = 'M';
                                exposureUrlData['includeYn'] = 'N';
                                exposureUrlData['exposureUrl'] = element.value;
                                exposureUrls.push(exposureUrlData);
                            }
                        });
                    } else if(radCaPlace.value == 'PC_ONLY') {
                        txtCaExposureUrlPs.forEach(element => {
                            if(element.value != '') {
                                let exposureUrlData = {};
                                exposureUrlData['gubun'] = 'P';
                                exposureUrlData['includeYn'] = 'N';
                                exposureUrlData['exposureUrl'] = element.value;
                                exposureUrls.push(exposureUrlData);
                            }
                        });
                    }
                    data['campaignExposureUrlDtos'] = exposureUrls;
                }
            }
            // 노출 시점
            data['caViewPoint'] = radCaViewPoint.value;
            if(radCaViewPoint.value == 'PERCENT') {
                data['viewPointSub'] = radCaViewSubPercent.value;
            } else if(radCaViewPoint.value == 'LOADING' || radCaViewPoint.value == 'NONE_MOVE') {
                data['viewPointSub'] = radCaViewSubSecond.value;
            } else if(radCaViewPoint.value == 'BEFORE_OUT') {
                data['viewPointSub'] = radCaViewSubOut.value;
            }
            // 노출 빈도
            data['exposureLimitCnt'] = radExposureLimitCnt.value;
            // 메시지 유형
            data['caMsgType'] = radCaMsgType.value;

            return data;
        },
        save: function() {
            const data = campaignBaseCreate.makeDataAndVaild();
            if(!data) return;
            const acl = document.querySelector('#acl').value;

            domsFetch(`/campaign/base/save.do?acl=${acl}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(async response => {
                    const data = await response.json();
                    if(response.ok) {
                        await Modal.alert('캠페인이 저장 되었습니다.', () => {
                            window.location.href = `/campaign/message/regist.view?acl=${acl}&id=${data.data}`;
                        });
                    } else {
                        await Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));
        }
    }
})();

campaignBaseCreate.init();