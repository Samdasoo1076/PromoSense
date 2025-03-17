(() => {
    const btnClose = document.querySelector("#btnClose");
    const btnSave = document.querySelector("#btnSave");
    const btnDelete = document.querySelector("#btnDelete");
    const btnX = document.querySelector("#btnX");
    const acl = document.querySelector('#acl').value;

    return {
        init: function() {
            btnClose.addEventListener("click", this.close);
            btnSave && btnSave.addEventListener("click", this.save);
            btnDelete && btnDelete.addEventListener("click", this.delete);
            btnX && btnX.addEventListener("click", this.close);
        },
        close: function() {
            Popup.close('middleMenuSavePopup');
        },
        save: function() {
            const payload = new FormData(
                document.getElementById('popup_frm'));
            domsFetch('/settings/menu/middle/save.do', {
                method: 'POST',
                body: payload})
                .then(async response => {
                    if(response.ok) {
                        Modal.alert('적용 되었습니다.', () => window.location.reload());
                    } else {
                        const data = await response.json();
                        Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));
        },
        delete: function(event) {
            const menuId = event.currentTarget.dataset.menu_id;
            const deleteAction = () => domsFetch(`/settings/menu/delete.do?acl=${acl}&menuId=${menuId}`, {
                method: 'POST',
            })
                .then(async response => {
                    if(response.ok) {
                        Modal.alert('적용 되었습니다.', () => window.location.reload());
                    } else {
                        const data = await response.json();
                        Modal.alert(data.message);
                    }
                })
                .catch(e => console.log('error: ', e));

            Modal.confirm("메뉴를 삭제하시겠습니까?", () => deleteAction());
        }
    }
})().init();