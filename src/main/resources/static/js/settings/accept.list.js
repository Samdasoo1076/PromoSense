const acceptList = (() => {
    const frm = document.querySelector('#frm');
    // const btnSearch = document.querySelector('#btnSearch');

    const btnSave = document.querySelector('#btnSave');
    const btnCancel = document.querySelector('#btnCancel');
    const acl = document.querySelector('#acl').value;

    const txtAcceptIp = document.querySelector('#acceptIp');
    const msgAcceptIp = document.querySelector('#msgAcceptIp');

    const ipCheck = new RegExp('^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$');

    const validates = {
        acceptId: true,
    }

    return {
        init: function() {
            btnSave && btnSave.addEventListener("click", this.save);
            btnCancel.addEventListener("click", this.cancel, this.search);
        },
        search: function() {
            frm.method="get"
            frm.page.value = 0;
            frm.action = '/settings/accept/list';
            frm.submit();
        },
        save: async function(){

            if (ipCheck.test(txtAcceptIp.value)) {
                validates.acceptId = true;
            } else {
                msgAcceptIp.className = 'form-noti';
                msgAcceptIp.textContent = '입력하신 값은 IP형식이 아닙니다.';
                validates.acceptId = false;
            }
            
                    if(validates.acceptId) {
                        Modal.confirm('IP를 등록 하시겠습니까?', ()=> {

                            const formData = new FormData(frm);

                            console.log('formData :: ', JSON.stringify(Object.fromEntries(formData.entries())));
                            domsFetch(`/settings/accept/write.do?acl=${acl}`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(Object.fromEntries(formData.entries())),
                            })
                                .then(async (response) => {
                                    if (response.ok) {
                                        Modal.alert('정상적으로 등록 되었습니다.', () => {
                                            window.location.reload();
                                        });
                                    } else {
                                        const data = await response.json();
                                        Modal.alert(data.message);
                                    }
                                })
                                .catch(e => console.log('error: ', e));
                        });
                    }
        },
        cancel: function(){
            frm.acceptIp.value="";
        },
        delete: async function (seqNo) {
            // if(window.confirm('IP를 삭제 하시겠습니까?')) {
            Modal.confirm('IP를 삭제 하시겠습니까?', ()=> {
                domsFetch(`/settings/accept/delete.do?acl=${acl}&seqNo=${seqNo}`)
                    .then(async (response) => {
                        if (response.ok) {
                            Modal.alert("IP가 정상적으로 삭제 되었습니다.", ()=>{
                                window.location.href = `/settings/accept/list.do?acl=${acl}`;
                            });
                        } else {
                            const data = await response.json();
                            Modal.alert(data.message);
                        }
                    })
                    .catch(() => alert('에러 발생'));
            });
        }
    }
})();

acceptList.init();
