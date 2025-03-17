(() => {
    const btnViewDel = document.querySelector('#btnViewDel');
    const btnCancel = document.querySelector('#btnCancel');
    const btnApply = document.querySelector('#btnApply');
    const btnDelete = document.querySelector('#btnDelete');
    const chkHistorys = document.querySelectorAll('#chkHistory');
    const btnX = document.querySelector('#btnX');

    return {
        init: function() {
            if(btnViewDel) {
                btnViewDel.addEventListener('click', this.viewDisplay);
            }
            btnCancel.addEventListener('click', this.chkCancel);
            btnApply.addEventListener('click', this.msgApply);
            btnDelete.addEventListener('click', this.msgDelete);
            btnX.addEventListener('click', this.popClose);
            btnDelete.style.display = 'none';
            chkHistorys.forEach(element => {
                element.addEventListener('click', this.chkOneSelect);
            });
        },
        viewDisplay: function() {
            let isDisabled = btnViewDel.classList.contains('disabled');
            btnViewDel.classList.toggle('disabled', !isDisabled);
            if(!isDisabled) {
                btnDelete.style.display = 'none';
                btnApply.style.display = 'block';
            } else {
                btnDelete.style.display = 'block';
                btnApply.style.display = 'none';
            }
            chkHistorys.forEach(element => {
                element.checked = false;
                element.closest('.goods-item').classList.remove('checked-within');
            });
        },
        msgApply: function() {
            const msgHisNo = document.querySelector('input[name=chkHistory]:checked').value;
            const acl = document.querySelector('#acl').value;
            const id = document.querySelector('#caNo').value;
            window.location.href = `/campaign/message/regist.view?acl=${acl}&id=${id}&msgHisNo=${msgHisNo}`;
        },
        chkCancel: function() {
            chkHistorys.forEach(element => {
                element.checked = false;
                element.closest('.goods-item').classList.remove('checked-within');
            });
        },
        msgDelete: function() {
            const acl = document.querySelector('#acl').value;
            let list = [];

            chkHistorys.forEach(element => {
                if(element.checked) {
                    let data = {};
                    data['id'] = element.value;
                    list.push(data);
                }
            });
            Modal.confirm('선택한 메시지 콘텐츠를 삭제 하시겠습니까?', () => {
                domsFetch(`/campaign/popup/history/delete?acl=${acl}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(list)
                })
                    .then(async response => {
                        const data = await response.json();
                        if(response.ok) {
                            Modal.alert('메시지 콘텐츠가 삭제 되었습니다.', () => {
                                chkHistorys.forEach(element => {
                                    if(element.checked) {
                                        element.closest('.goods-item').remove();
                                    }
                                });
                            });
                        } else {
                            Modal.alert(data.message);
                        }
                    })
                    .catch(e => console.log('error: ', e));
            })
        },
        chkOneSelect: function(e) {
            let elem = (e instanceof Event)? e.target : e;
            if(btnViewDel.classList.contains('disabled')) {
                chkHistorys.forEach(element => {
                    if(elem.value == element.value) {
                        element.checked = true;
                    } else {
                        element.checked = false;
                        element.closest('.goods-item').classList.remove('checked-within');
                    }
                });
            }
        },
        popClose: function() {
            Popup.close('contentPopupMessageHorizontal');
        }
    }
})().init();
