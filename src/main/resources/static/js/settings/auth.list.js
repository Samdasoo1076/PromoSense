const authList = (() => {
    const frm = document.querySelector('#frm');
    const btnSave = document.querySelector('#btnSave');
    const btnCancel = document.querySelector('#btnCancel');
    const acl = document.querySelector("#acl").value;

    const txtGroupName = document.querySelector('#groupName');


    return {
        init: function() {
            btnSave && btnSave.addEventListener("click", this.save);
            btnCancel.addEventListener('click', this.cancel);

            txtGroupName.addEventListener('keyup', this.validation);
        },
        save: async function() {
            Modal.confirm('그룹을 등록 하시겠습니까?', ()=> {
                domsFetch('/settings/auth/group/check/' + txtGroupName.value)
                    .then(async (response) => {
                        if (response.ok) {
                            await domsFetch(`/settings/auth/write.do?acl=${acl}`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({groupName: txtGroupName.value}),
                            })
                                .then(async (res) => {
                                    if(res.ok) {
                                        Modal.alert('정상적으로 권한그룹이 등록 되었습니다.', () => {
                                            window.location.reload();
                                        });
                                    } else {
                                        const data = await res.json();
                                        Modal.alert(data.message);
                                    }
                                })
                                .catch(e => console.log('error: ', e));
                        } else {
                            const data = await response.json();
                            Modal.alert(data.message);
                        }
                    })
                    .catch(e => console.log('error: ', e));
            });
        },
        cancel: function () {
            txtGroupName.value = '';
        },
        validation: function() {
            if(!txtGroupName.value.trim().length) {
                btnSave.classList.add('disabled');
            } else {
                btnSave.classList.remove('disabled');
            }
        },
        delete: async function(id) {
            Modal.confirm('그룹을 삭제 하시겠습니까?', ()=> {
                domsFetch(`/settings/auth/delete.do?acl=${acl}&id=${id}`)
                    .then(async (response) => {
                        if(response.ok) {
                            Modal.alert('권한그룹이 정상적으로 삭제 되었습니다.', () => {
                                window.location.reload();
                            });
                        } else {
                            const data = await response.json();
                            Modal.alert(data.message);
                        }
                    })
                    .catch(e => console.log('error: ', e));
            });
        },
        setting: function(groupNo) {
            Popup.open(`/settings/auth/popup/detail.view?acl=${acl}&groupNo=${groupNo}`, 'contentPopupMenuPermission');
        }
    }
})();

authList.init();