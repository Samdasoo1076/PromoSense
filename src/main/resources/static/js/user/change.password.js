const changepassword = (() => {
    const frm = document.querySelector("#frm");
    const btnSave = document.querySelector("#btnSave");

    // text 필드
    const txtPasswd = document.querySelector("#passwd");

    // message 필드
    const msgPasswd = document.querySelector("#msgPasswd");

    // 유효성 조건
    // const checkPwd = new RegExp('^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$');
    const checkPwd = new RegExp('^(?=.*[A-Za-z])(?=.*[`~!@#$%^&*()])(?=.*\\d)(?=\\S+$).{8,16}$');


    // msgPasswd.textContent = "8~16자리 영문 대소문자, 숫자, 특수문자 중\n 3가지 이상 조합으로 만들어주세요.";
    txtPasswd.addEventListener('keyup', function(){

        if(/(\w)\1\1/.test(txtPasswd.value)){
            msgPasswd.className = "form-noti invalid";
            msgPasswd.textContent = "3자리 이상 반복되는 영문, 숫자는 비밀번호로 사용할 수 없습니다.";
            validates.passwd = false;
        }else if  (!checkPwd.test(txtPasswd.value)) {
            msgPasswd.className = "form-noti";
            msgPasswd.textContent = "8~16자리 영문 대소문자, 숫자, 특수문자 중\n 3가지 이상 조합으로 만들어주세요.";
            validates.passwd = false;
        }else {
            msgPasswd.className = "form-noti valid";
            msgPasswd.textContent = "사용 가능한 비밀번호 입니다.";
            validates.passwd = true;
        }
    });



    // 유효성 여부
    const validates = {
        passwd: false,
    }

    return {
        init: function() {
            btnSave.addEventListener("click", this.save);
            txtPasswd.addEventListener("blur", this.validate);

        },
        save: function() {
            if(changepassword.canSubmit()) {
                frm.submit();
            }
        },
        canSubmit: function() {
            const bool = Object.values(validates).reduce((acc, elem) => acc && elem, true);
            if(bool) {
                btnSave.classList.remove("disabled");
            }else{
                btnSave.classList.add("disabled");
            }
            return bool;
        },
        validate: function(elem) {
            const target = elem.target || elem;
            const checkNumber = txtPasswd.value.search(/[0-9]/g);
            const checkEnglish = txtPasswd.value.search(/[a-z]/ig);
           if (target === txtPasswd) {
                if (!checkPwd.test(txtPasswd.value)) {
                    msgPasswd.className = "form-noti invalid";
                    msgPasswd.textContent = "8~16자리 영문 대소문자, 숫자, 특수문자 중\n 3가지 이상 조합으로 만들어주세요.";
                    validates.passwd = false;
                } else if(checkNumber <0 || checkEnglish <0){
                    msgPasswd.className = "form-noti invalid";
                    msgPasswd.textContent = "숫자와 영문자를 혼용하여야 합니다.";
                    validates.passwd = false;
                } else if(/(\w)\1\1/.test(txtPasswd.value)){
                    msgPasswd.className = "form-noti invalid";
                    msgPasswd.textContent = "3자리 이상 반복되는 영문, 숫자, 특수문자는\n 비밀번호로 사용할 수 없습니다.";
                    validates.passwd = false;
                } else {
                    msgPasswd.className = "form-noti valid";
                    msgPasswd.textContent = "";
                    validates.passwd = true;
                }
            }
            changepassword.canSubmit();
        },
    }
})();

changepassword.init();


