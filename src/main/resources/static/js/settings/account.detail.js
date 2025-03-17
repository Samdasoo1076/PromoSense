const accountList = (() => {
    const frm = document.querySelector('#frm');
    const btnSave = document.querySelector('#btnSave');
    const btnDelete = document.querySelector('#btnDelete');
    const acl = document.querySelector('#acl').value;

    const txtPassword = document.querySelector('#password');
    const txtDept = document.querySelector('#dept');
    const txtAdmHphone = document.querySelector('#admHphone');
    const txtAdmEmail = document.querySelector('#admEmail');
    const txtMemo = document.querySelector('#memo');

    const hidAdmFlag = document.querySelector('#admFlag');
    const hidPassowrd = document.querySelector('#tmpPassword');
    const hidEmail = document.querySelector('#tmpEmail');
    const hidAdmNo = document.querySelector('#admNo');

    const radAdmFlag = document.querySelectorAll('input[type="radio"][name="radAdmFlag"]');

    const selGroupNo = document.querySelector('#groupNo');

    const chkPassword = document.querySelector('#chkPassword');
    const chkEmail = document.querySelector('#chkEmail');

    const msgPassword = document.querySelector('#msgPassword');
    const msgEmail = document.querySelector('#msgEmail');
    const msgHphone = document.querySelector('#msgHphone');
    const msgDept = document.querySelector('#msgDept');
    const msgGroupNo = document.querySelector('#msgGroupNo');
    const radLoginFailCnt = document.querySelector('#radLoginFailCnt');

    // const pwdCheck = new RegExp('^[a-zA-Z0-9\d`~!@#$%^&*()-_=+]{8,16}$');
    const pwdCheck = new RegExp('^(?=.*[A-Za-z])(?=.*[`~!@#$%^&*()])(?=.*\\d)(?=\\S+$).{8,16}$');
    const emailCheck = new RegExp('^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$');

    const validates = {
        passwd: true,
        dept: true,
        admHphone: true,
        admEmail: true,
        groupNo: true,
    }

    return {
        init: function() {
            btnSave && btnSave.addEventListener('click', this.update);
            btnDelete && btnDelete.addEventListener('click', this.delete);

            chkPassword.addEventListener('click', this.isPassword);
            chkEmail.addEventListener('click', this.isEmail);

            selGroupNo.addEventListener('change', this.validate);
            for(let i = 0; i < radAdmFlag.length; i++) {
                radAdmFlag[i].addEventListener('change', function(e) {
                    hidAdmFlag.value = this.value;
                    accountList.validate(e);
                });
            }

            txtPassword.addEventListener('blur', this.validate);
            txtAdmEmail.addEventListener('blur', this.validate);
            txtAdmHphone.addEventListener('blur', this.validate);
            txtDept.addEventListener('blur', this.validate);

            txtPassword.addEventListener('mouseout', this.validate);
            txtAdmEmail.addEventListener('mouseout', this.validate);
            txtAdmHphone.addEventListener('mouseout', this.validate);
            txtDept.addEventListener('mouseout', this.validate);

            if(txtPassword.value == '') {
                msgPassword.className = 'form-noti';
                msgPassword.textContent = '8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.';
                validates.passwd = false;
            }
            if(txtDept.value == '') {
                msgDept.className = 'form-noti invalid';
                msgDept.textContent = '부서는 필수입니다.';
                validates.dept = false;
            }
            if(txtAdmHphone.value == '') {
                msgHphone.className = 'form-noti invalid';
                msgHphone.textContent = '휴대폰번호는 필수입니다.';
                validates.admHphone = false;
            }
            if(txtAdmEmail.value == '') {
                msgEmail.className = 'form-noti';
                msgEmail.textContent = '이메일 주소를 입력해주세요.';
                validates.admEmail = false;
            }
            if(selGroupNo.value == '') {
                msgGroupNo.className = 'form-noti invalid';
                msgGroupNo.textContent = '사용자그룹 선택은 필수입니다.';
                validates.groupNo = false;
            }
        },
        isEmail: function() {
            if(chkEmail.checked == false) {
                txtAdmEmail.value = hidEmail.value;
                txtAdmEmail.readOnly = true;
                msgEmail.textContent = '';
                validates.admEmail = true;
            } else {
                txtAdmEmail.value = '';
                txtAdmEmail.readOnly = false;
                msgEmail.className = 'form-noti';
                msgEmail.textContent = '이메일 주소를 입력해주세요.';
                validates.admEmail = false;
            }
        },
        isPassword: function() {
            if(chkPassword.checked == false) {
                txtPassword.value = hidPassowrd.value;
                txtPassword.readOnly = true;
                msgPassword.textContent = '';
                validates.passwd = true;
            } else {
                txtPassword.value = '';
                txtPassword.readOnly = false;
                msgPassword.className = 'form-noti';
                msgPassword.textContent = '8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.';
                validates.passwd = false;
            }
        },
        validate: async function(e) {
            const target = e.target || e;

            if (target === txtPassword) {
                if(chkPassword.checked == false) {
                    return;
                }
                if(!txtPassword.value.trim().length) {
                    msgPassword.className = 'form-noti';
                    msgPassword.textContent = '8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.';
                    validates.passwd = false;
                } else if(/(\w)\1\1/.test(txtPassword.value)){
                    msgPassword.className = "form-noti invalid";
                    msgPassword.textContent = "3자리 이상 반복되는 영문, 숫자는 비밀번호로 사용할 수 없습니다.";
                    validates.passwd = false;
                } else if (!pwdCheck.test(txtPassword.value)) {
                    msgPassword.className = 'form-noti invalid';
                    msgPassword.textContent = '8~16자리 영문 대소문자, 숫자, 특수문자 중 3가진 이상 조합으로 만들어주세요.';
                    validates.passwd = false;
                } else {
                    msgPassword.className = 'form-noti valid';
                    msgPassword.textContent = '사용 가능한 비밀번호 입니다.';
                    validates.passwd = true;
                }
            } else if(target === txtAdmEmail) {
                if(chkEmail.checked == false) {
                    return;
                }
                if(!txtAdmEmail.value.trim().length) {
                    msgEmail.className = 'form-noti';
                    msgEmail.textContent = '이메일 주소를 입력해주세요.';
                    validates.admEmail = false;
                } else if (!emailCheck.test(txtAdmEmail.value)) {
                    msgEmail.className = 'form-noti invalid';
                    msgEmail.textContent = '잘못된 이메일 주소입니다. 이메일 주소를 정확하게 입력해 주세요.';
                    validates.admEmail = false;
                } else if (hidEmail.value == txtAdmEmail.value) {
                    msgEmail.className = 'form-noti invalid';
                    msgEmail.textContent = '기존 이메일과 동일합니다.';
                    validates.admEmail = false;
                } else {
                    await domsFetch('/search/email/' + txtAdmEmail.value)
                        .then((res) => res.json())
                        .then((response) => {
                            if(!response.data.exists) {
                                msgEmail.className = 'form-noti valid';
                                // msgEmail.textContent = response.data.message;
                                msgEmail.textContent = '사용 가능한 이메일 입니다.';
                                validates.admEmail = true;
                            } else {
                                msgEmail.className = 'form-noti invalid';
                                msgEmail.textContent = '이미 사용중인 이메일 입니다.';
                                validates.admEmail = false;
                            }
                        });
                }

            } else if(target === txtAdmHphone) {
                if (!txtAdmHphone.value.trim().length) {
                    msgHphone.className = 'form-noti invalid';
                    msgHphone.textContent = '휴대폰번호는 필수입니다.';
                    validates.admHphone = false;
                } else {
                    msgHphone.className = 'form-noti valid';
                    msgHphone.textContent = '';
                    validates.admHphone = true;
                }
            } else if (target === txtDept) {
                if (!txtDept.value.trim().length) {
                    msgDept.className = 'form-noti invalid';
                    msgDept.textContent = '부서는 필수입니다.';
                    validates.dept = false;
                } else {
                    msgDept.className = 'form-noti valid';
                    msgDept.textContent = '';
                    validates.dept = true;
                }
            } else if(target === selGroupNo) {
                if(!selGroupNo.value.trim().length) {
                    msgGroupNo.className = 'form-noti invalid';
                    msgGroupNo.textContent = '사용자그룹 선택은 필수입니다.';
                    validates.groupNo = false;
                } else {
                    msgGroupNo.className = 'form-noti valid';
                    msgGroupNo.textContent = '';
                    validates.groupNo = true;
                }
            }
        },
        update: async function() {
            const bool = Object.values(validates).reduce((acc, elem) => acc && elem, true);

            if(bool) {
                await Modal.confirm('상태값을 변경하시겠습니까?', ()=> {
                    const formData = new FormData(frm);
                    formData.append('chkPassword', chkPassword.checked);
                    formData.append('chkEmail', chkEmail.checked);
                    formData.append('radLoginFailCnt', radLoginFailCnt.checked);

                    domsFetch(`/settings/account/update.do?acl=${acl}&id=${hidAdmNo.value}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(Object.fromEntries(formData.entries())),
                    })
                        .then(async (response) => {
                            if (response.ok) {
                                await Modal.alert('계정이 정상적으로 변경 되었습니다.', () => {
                                    window.location.reload();
                                });
                            } else {
                                const data = await response.json();
                                await Modal.alert(data.message);
                            }
                        })
                        .catch(e => console.log('error: ', e));
                });
            }
        },
        delete: async function () {
            await Modal.confirm('계정을 삭제 하시겠습니까?', ()=> {
                domsFetch(`/settings/account/delete.do?acl=${acl}&id=${hidAdmNo.value}`)
                    .then(async (response) => {
                        if (response.ok) {
                            await Modal.alert('계정이 정상적으로 삭제 되었습니다.', () => {
                                window.location.href = `/settings/account/list.do?acl=${acl}`;
                            });
                        } else {
                            const data = await response.json();
                            await Modal.alert(data.message);
                        }
                    })
                    .catch(e => console.log('error: ', e));
            });
        }
    }
})();

accountList.init();