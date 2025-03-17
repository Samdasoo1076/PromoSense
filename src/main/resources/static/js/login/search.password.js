const searchPassword = (() => {
    const frm = document.querySelector("#frm");

    const btnSearch = document.querySelector('#btnSearch');
    const txtAdmId = document.querySelector('#admId');
    const txtAdmEmail = document.querySelector('#admEmail');

    const msgAdmId = document.querySelector('#msgAdmId');
    const msgAdmEmail = document.querySelector('#msgAdmEmail');

    const isRegEmail = new RegExp('^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$');

    // 유효성 여부
    const validates = {
        admId: false,
        admEmail: false,
    }

    return {
        init: function() {
            btnSearch.addEventListener('click', this.findId);

            txtAdmId.addEventListener('blur', this.validate);
            txtAdmEmail.addEventListener('blur', this.validate);
            // blur 로만 하면 button클릭시 클릭이벤트가 먼저 먹어 blur이벤트는 작동을 하지 않아 mouseout도 같이 걸어준다.
            // txtAdmId.addEventListener('mouseout', this.validate);
            // txtAdmEmail.addEventListener('mouseout', this.validate);
        },
        validate: async function(e) {
            const target = e.target || e;

            if(target === txtAdmId) {
                if (!txtAdmId.value.trim().length) {
                    msgAdmId.className = 'form-noti';
                    msgAdmId.textContent = '아이디를 입력해 주세요.';
                    validates.admId = false;
                } else {
                    /* 보안 진단결과 조치 1-2 취약한 인증과 권한 관리 */
                    /*await domsFetch('/search/id/' + txtAdmId.value)
                        .then((res) => res.json())
                        .then((response) => {
                            if(response.data.exists) {
                                msgAdmId.className = 'form-noti valid';
                                msgAdmId.textContent = response.data.message;
                                validates.admId = true;
                            } else {
                                msgAdmId.className = 'form-noti invalid';
                                msgAdmId.textContent = response.data.message;
                                validates.admId = false;
                            }
                        });*/

                    msgAdmId.textContent = '';
                    validates.admId = true;
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
            searchPassword.canSubmit();
        },
        findId: function() {
            if(searchPassword.canSubmit()) {
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

searchPassword.init();
