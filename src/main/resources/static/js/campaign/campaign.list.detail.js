const campaignListDetail = (() => {
    const frm = document.querySelector('#frm');
    const btnCamAnalysis = document.querySelector('#btnCamAnalysis');
    const caNo = document.querySelector('#caNo').value;
    const acl = document.querySelector('#acl').value;

    return {
        init: function() {
            btnModify && btnModify.addEventListener("click", this.modify);
            btnCamAnalysis && btnCamAnalysis.addEventListener("click", this.analysis);

        },
        analysis: function() {
            location.href = `/campaign/analysis/detail.do?acl=A0001&id=${caNo}`;

        },
        modify: function() {
            frm.caNo.value = caNo;
            frm.id.value = caNo;
            frm.action = `/campaign/base/modify.view?acl=${acl}`;
            frm.submit();
        },

    }
})();

campaignListDetail.init();
