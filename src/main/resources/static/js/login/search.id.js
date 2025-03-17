const searchId = (() => {
    const frm = document.querySelector("#frm");

    const btnSearch = document.querySelector('#btnSearch');
    const txtAdmName = document.querySelector('#admName');
    const txtAdmEmail = document.querySelector('#admEmail');

    const msgAdmName = document.querySelector('#msgAdmName');
    const msgAdmEmail = document.querySelector('#msgAdmEmail');

    const isRegEmail = new RegExp('^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$');

    // 유효성 여부
    const validates = {
        admName: false,
        admEmail: false,
    }

    return {
        init: function() {
            btnSearch.addEventListener('click', this.findId);

            txtAdmName.addEventListener('blur', this.validate);
            txtAdmEmail.addEventListener('blur', this.validate);
            // blur 로만 하면 button클릭시 클릭이벤트가 먼저 먹어 blur이벤트는 작동을 하지 않아 mouseout도 같이 걸어준다.
            // txtAdmName.addEventListener('mouseout', this.validate);
            // txtAdmEmail.addEventListener('mouseout', this.validate);
        },
        validate: async function(e) {
            const target = e.target || e;

            if(target === txtAdmName) {
                if (!txtAdmName.value.trim().length) {
                    msgAdmName.className = 'form-noti';
                    msgAdmName.textContent = '이름을 입력해 주세요.';
                    validates.admName = false;
                } else {
                    /* 보안 진단결과 조치 1-2 취약한 인증과 권한 관리 */
                    /* await domsFetch('/search/name/' + txtAdmName.value)
                    .then((res) => res.json())
                    .then((response) => {
                        if(response.data.exists) {
                            msgAdmName.className = 'form-noti valid';
                            msgAdmName.textContent = response.data.message;
                            validates.admName = true;
                        } else {
                            msgAdmName.className = 'form-noti invalid';
                            msgAdmName.textContent = response.data.message;
                            validates.admName = false;
                        }
                    }); */

                    msgAdmName.textContent = '';
                    validates.admName = true;
                }
            } else if(target === txtAdmEmail) {
                if (!txtAdmEmail.value.trim().length) {
                    msgAdmEmail.className = 'form-noti';
                    msgAdmEmail.textContent = '이메일을 입력해 주세요.';
                    validates.admEmail = false;
                } else {
                    if(!isRegEmail.test(txtAdmEmail.value)) {
                        msgAdmEmail.className = "form-noti invalid";
                        msgAdmEmail.textContent = "잘못된 이메일 주소입니다. 이메일 주소를 정확하게 입력해 주세요.";
                        validates.admEmail = false;
                    } else {
                        /* 보안 진단결과 조치 1-2 취약한 인증과 권한 관리 */
                        /*await domsFetch('/search/email/' + txtAdmEmail.value)
                        .then((res) => res.json())
                        .then((response) => {
                            if(response.data.exists) {
                                msgAdmEmail.className = 'form-noti valid';
                                msgAdmEmail.textContent = response.data.message;
                                validates.admEmail = true;
                            } else {
                                msgAdmEmail.className = 'form-noti invalid';
                                msgAdmEmail.textContent = response.data.message;
                                validates.admEmail = false;
                            }
                        });*/

                        msgAdmEmail.textContent = '';
                        validates.admEmail = true;
                    }
                }
            }
            searchId.canSubmit();
        },
        findId: function() {
            if(searchId.canSubmit()) {
                frm.submit();
            }
        },
        canSubmit: function() {

            const bool = Object.values(validates).reduce((acc, elem) => acc && elem, true);

            if(bool) {
                btnSearch.classList.remove("disabled");
            }else{
                btnSearch.classList.add("disabled");
            }

            return bool;
        }
    }
})();

searchId.init();
