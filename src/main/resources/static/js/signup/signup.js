const signup = (() => {
    const frm = document.querySelector("#frm");
    const btnSave = document.querySelector("#btnSave");

    // text 필드
    const txtAdmId = document.querySelector("#admId");
    const txtPasswd = document.querySelector("#passwd");
    const txtAdmName = document.querySelector("#admName");
    const txtDept = document.querySelector("#dept");
    const txtAdmHphone = document.querySelector("#admHphone");
    const txtAdmEmail = document.querySelector("#admEmail");

    // message 필드
    const msgAdmId = document.querySelector("#msgAdmId");
    const msgPasswd = document.querySelector("#msgPasswd");
    const msgAdmName = document.querySelector("#msgAdmName");
    const msgDept = document.querySelector("#msgDept");
    const msgAdmHphone = document.querySelector("#msgAdmHphone");
    const msgAdmEmail = document.querySelector("#msgAdmEmail");

    // 유효성 조건
    const idCheck = new RegExp('^[a-zA-Z0-9]{4,20}$');
    const pwdCheck = new RegExp('^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$');
    const emailCheck = new RegExp('^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$');
    const phoneCheck = new RegExp('^[0-9]{10,11}$');

    // 유효성 여부
    const validates = {
        admId: false,
        passwd: false,
        admName: false,
        dept: false,
        admHphone: false,
        admEmail: false,
    }

    return {
        init: function() {
            btnSave.addEventListener("click", this.save);
            txtAdmId.addEventListener("blur", this.validate);
            txtPasswd.addEventListener("blur", this.validate);
            txtAdmName.addEventListener("blur", this.validate);
            txtDept.addEventListener("blur", this.validate);
            txtAdmHphone.addEventListener("blur", this.validate);
            txtAdmEmail.addEventListener("blur", this.validate);
        },
        save: function() {
            if(signup.canSubmit()) {
                frm.submit();
            }
        },
        canSubmit: function() {
            console.log(validates);
            const bool = Object.values(validates).reduce((acc, elem) => acc && elem, true);
            if(bool) {
                btnSave.classList.remove("disabled");
            }else{
                btnSave.classList.add("disabled");
            }
            return bool;
        },
        validate: async function(elem) {
            const target = elem.currentTarget || elem;
            if (target === txtAdmId) {
                if (!idCheck.test(txtAdmId.value)) {
                    msgAdmId.className = "form-noti invalid";
                    msgAdmId.textContent = "4~20자리의 영문, 숫자만 사용 가능합니다.";
                    validates.admId = false;
                } else {
                    msgAdmId.className = "form-noti valid";
                    msgAdmId.textContent = "";
                    validates.admId = true;
                }
                
                // 아이디 중복 체크
                if(validates.admId) {
                    const response = await domsFetch(`/signup/user/validate/${txtAdmId.value}`);
                    const data = (await response.json()).data;
                    if(data.exists) {
                        msgAdmId.className = "form-noti invalid";
                        msgAdmId.textContent = data.message;
                        validates.admId = false;
                    } else {
                        msgAdmId.className = "form-noti valid";
                        msgAdmId.textContent = data.message;
                        validates.admId = true;
                    }
                }
            } else if (target === txtPasswd) {
                if (!pwdCheck.test(txtPasswd.value)) {
                    msgPasswd.className = "form-noti invalid";
                    msgPasswd.textContent = "8~16자리 영문 대소문자, 숫자, 특수문자 중 3가지 이상 조합으로 만들어주세요.";
                    validates.passwd = false;
                } else if(/(\w)\1\1/.test(txtPasswd.value)) {
                    msgPasswd.className = "form-noti invalid";
                    msgPasswd.textContent = "3자리 이상 반복되는 영문, 숫자, 특수문자는 비밀번호로 사용할 수 없습니다.";
                    validates.passwd = false;
                } else {
                    msgPasswd.className = "form-noti valid";
                    msgPasswd.textContent = "";
                    validates.passwd = true;
                }
            } else if (target === txtAdmName) {
                if (!txtAdmName.value.trim().length) {
                    msgAdmName.className = "form-noti invalid";
                    msgAdmName.textContent = "이름은 필수입니다.";
                    validates.admName = false;
                } else {
                    msgAdmName.className = "form-noti valid";
                    msgAdmName.textContent = "";
                    validates.admName = true;
                }
            } else if (target === txtDept) {
                if (!txtDept.value.trim().length) {
                    msgDept.className = "form-noti invalid";
                    msgDept.textContent = "부서는 필수입니다.";
                    validates.dept = false;
                } else {
                    msgDept.className = "form-noti valid";
                    msgDept.textContent = "";
                    validates.dept = true;
                }
            } else if (target === txtAdmHphone) {
                if (!txtAdmHphone.value.trim().length) {
                    msgAdmHphone.className = "form-noti invalid";
                    msgAdmHphone.textContent = "휴대폰번호는 필수입니다.";
                    validates.admHphone = false;
                } else if(!phoneCheck.test(txtAdmHphone.value)) {
                    msgAdmHphone.className = "form-noti invalid";
                    msgAdmHphone.textContent = "잘못된 휴대전화번호입니다.";
                    validates.admHphone = false;
                } else {
                    msgAdmHphone.className = "form-noti valid";
                    msgAdmHphone.textContent = "";
                    validates.admHphone = true;
                }
            } else if (target === txtAdmEmail) {
                if (!emailCheck.test(txtAdmEmail.value)) {
                    msgAdmEmail.className = "form-noti invalid";
                    msgAdmEmail.textContent = "잘못된 이메일 주소입니다. 이메일 주소를 정확하게 입력해 주세요.";
                    validates.admEmail = false;
                } else {
                    msgAdmEmail.className = "form-noti valid";
                    msgAdmEmail.textContent = "";
                    validates.admEmail = true;
                }

                //이메일 중복 체크
                if(validates.admEmail) {
                    const response = await domsFetch(`/signup/email/validate/${txtAdmEmail.value}`);
                    const data = (await response.json()).data;
                    if(data.exists) {
                        msgAdmEmail.className = "form-noti invalid";
                        msgAdmEmail.textContent = data.message;
                        validates.admId = false;
                    } else {
                        msgAdmEmail.className = "form-noti valid";
                        msgAdmEmail.textContent = data.message;
                        validates.admId = true;
                    }
                }
            }
            signup.canSubmit();
        },
    }
})();

signup.init();


