(() => {
    const btnPopClose = document.querySelector('#btnPopClose');
    const btnPopSave = document.querySelector('#btnPopSave');
    const btnX = document.querySelector("#btnX");
    const acl = document.querySelector('#acl').value;

    const hidMenuNo = document.querySelectorAll('#menuNo');
    const hidMenuCode = document.querySelectorAll('#menuCode');
    const hidGroupNo = document.querySelector('#groupNo');

    const chkRead = document.querySelectorAll('#chkRead');
    const chkReg = document.querySelectorAll('#chkReg');
    const chkUpd = document.querySelectorAll('#chkUpd');
    const chkDel = document.querySelectorAll('#chkDel');
    const chkFile = document.querySelectorAll('#chkFile');

    const elements = {
        chkRead,
        chkReg,
        chkUpd,
        chkDel,
        chkFile
    }

    return {
        init: function() {
            btnPopClose.addEventListener('click', this.close);
            btnPopSave && btnPopSave.addEventListener('click', this.save);
            btnX && btnX.addEventListener("click", this.close);

            chkRead.forEach(element => {
                element.addEventListener('click', this.setCheck)
            });
            chkReg.forEach(element => {
                element.addEventListener('click', this.setCheck)
            });
            chkUpd.forEach(element => {
                element.addEventListener('click', this.setCheck)
            });
            chkDel.forEach(element => {
                element.addEventListener('click', this.setCheck)
            });
            chkFile.forEach(element => {
                element.addEventListener('click', this.setCheck)
            });
        },
        close: function(event) {
            Popup.close('contentPopupMenuPermission');
        },
        save: function() {
            const chkDataList = [];

            for(let i = 0; i < hidMenuNo.length; i++) {
                const chkData = {};

                chkData['groupNo'] = {groupNo : hidGroupNo.value};
                chkData['menuNo'] = {id : hidMenuNo[i].value};
                chkData['readFlag'] = (chkRead[i].checked)? 'Y' : 'N';
                chkData['regFlag'] = (chkReg[i].checked)? 'Y' : 'N';
                chkData['updFlag'] = (chkUpd[i].checked)? 'Y' : 'N';
                chkData['delFlag'] = (chkDel[i].checked)? 'Y' : 'N';
                chkData['fileFlag'] = (chkFile[i].checked)? 'Y' : 'N';

                chkDataList.push(chkData);
            }

            domsFetch(`/settings/auth/popup/save.do?acl=${acl}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(chkDataList)
            })
                .then(async response => {
                    if(response.ok) {
                        Modal.alert('권한이 저장 되었습니다.', () => {
                            window.location.reload();
                        });
                    } else {
                        const data = await response.json();
                        Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));
        },
        setCheck: function(e) {
            const gubun = e.target.value.split('.');

            for(let i = 0; i < hidMenuNo.length; i++) {
                if(gubun[0] == '1') {
                    const v = '2.' + gubun[1];

                    if(elements[e.target.id][i].value == v) elements[e.target.id][i].checked = e.target.checked;
                } else if(gubun[0] == '2') {
                    const v = '1.' + gubun[1];

                    if(elements[e.target.id][i].value == v && !elements[e.target.id][i].checked) elements[e.target.id][i].checked = e.target.checked;
                }
            }
        },
    }
})().init();