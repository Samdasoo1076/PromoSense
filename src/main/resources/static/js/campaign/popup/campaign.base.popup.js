(() => {
    // const frm = document.querySelector('#frm');
    const btnDetail = document.querySelector('#btnDetail');
    const btnModify = document.querySelector('#btnModify');
    const acl = document.querySelector('#acl').value;

    return {
        init: function() {
            btnDetail && btnDetail.addEventListener("click", this.detail);
            btnModify && btnModify.addEventListener("click", this.modify);
        },
        close: function() {
            Popup.close('postsReadPopupCampaign');
        },
        modify: function() {
            const caNo = btnModify.value;
            parent.frm.caNo.value = caNo;
            parent.frm.id.value = caNo;
            parent.frm.action = `/campaign/base/modify.view?acl=${acl}`;
            parent.frm.submit();
        },
        detail: function() {
            const caNo = btnDetail.value;

            parent.frm.caNo.value = caNo;
            parent.frm.action = `/campaign/list/detail.do?acl=${acl}`;
            parent.frm.submit();
        },
    }
})().init();