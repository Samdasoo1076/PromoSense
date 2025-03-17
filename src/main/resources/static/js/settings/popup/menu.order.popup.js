(() => {
    const btnClose = document.querySelector("#btnClose");
    const btnSave = document.querySelector("#btnSave");
    const btnX = document.querySelector("#btnX");

    return {
        init: function() {
            btnClose.addEventListener("click", this.close);
            btnSave && btnSave.addEventListener("click", this.save);
            btnX && btnX.addEventListener("click", this.close);
        },
        close: function() {
            Popup.close('contentPopupMenuOrder');
        },
        save: function() {
            Modal.confirm("상태값을 변경 하시겠습니까?", ()=> {
                const frm = document.querySelector("#popup_frm");
                frm.submit();
            })
        },
    }
})().init();