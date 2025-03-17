const login = (() => {
    const frm = document.querySelector("#frm");
    const btnLogin = document.querySelector("#btnLogin");
    const remember = document.querySelector("#remember");

    const txtUserName = document.querySelector("#admId");
    const txtPasswd = document.querySelector("#passwd");

    return {
        init: function() {
            const remember = document.cookie.split(';')
                .map(elem => {
                    const key = elem.substring(0, elem.indexOf('=')).replace(/^\s+|\s+$/g, '');
                    const value = elem.substring(elem.indexOf('=') + 1);
                    return {key, value}
                })
                .filter(({key, value}) => key === "remember")
                .pop();

            if(remember) {
                //txtUserName.value = remember.value;
            }

            btnLogin.addEventListener("click", this.login);
        },
        login: function() {
            if(!txtUserName.value.trim().length) {
                Modal.alert("아이디를 입력해 주세요.");
                return false;
            } else if(!txtPasswd.value.trim().length) {
                Modal.alert("비밀번호를 입력해 주세요.");
                return false;
            }

            frm.submit();
        }
    }
})();

login.init();


