(() => {
    const btnReset = document.querySelector('#btnReset');
    const btnSave = document.querySelector('#btnSave');
    const btnX = document.querySelector('#btnX');
    const acl = document.querySelector('#acl').value;
    const count = document.querySelector('#count').value;

    const txtKeyword = document.querySelector('#keyword').value;
    const txtStartDate = document.querySelector('#startDate').value;
    const txtEndDate = document.querySelector('#endDate').value;
    const txtCaGubun = document.querySelector('#caGubun').value;
    const txtPage = document.querySelector('#page').value;
    const txtSize = document.querySelector('#size').value;

    return {
        init: function() {

            btnReset && btnReset.addEventListener('click', this.reset);
            btnSave && btnSave.addEventListener('click', this.save);
            btnX && btnX.addEventListener('click', this.close);
        },
        close: function() {
            Popup.close('contentPopupExposureRank');
        },
        reset: function() {
            Popup.open(`/campaign/popup/view/order.do?acl=${acl}&count=${count}&keyword=${txtKeyword}&startDate=${txtStartDate}&endDate=${txtEndDate}&caFlag=2&caGubun=${txtCaGubun}&page=${txtPage}&size=${txtSize}`, 'contentPopupExposureRank', {sortable: true});
        },
        save: function() {
            Modal.confirm("노출 순위를 적용 하시겠습니까?", ()=> {
                const frm = document.querySelector("#popup_frm");
                frm.submit();

            })


        },
    }
})().init();