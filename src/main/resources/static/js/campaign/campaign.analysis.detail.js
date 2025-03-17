const campaignAnalysisDetail = (() => {
    const txtStartDate = document.querySelector('#startDate');
    const txtEndDate = document.querySelector('#endDate');
    const frm = document.querySelector('#frm');
    return {
        init: function() {
            document.querySelector('#btnSearch').addEventListener('click', this.search);
        },
        excelDown: function() {
            const ids = document.querySelector('#caId').value;
            const acl = document.querySelector('#acl').value;

            window.location.href = `/campaign/analysis/excel/download.do?acl=${acl}&ids=${ids}`;
        },
        searchValid: function(){
            const sDateInt = new Date(txtStartDate.value).getTime();
            const eDateInt = new Date(txtEndDate.value).getTime();
            if(sDateInt > eDateInt){
                Modal.alert('기간을 정확히 입력해 주세요.');
                return false;
            }else{
                return true;
            }
        },
        search: function() {
            if(campaignAnalysisDetail.searchValid()) {
                frm.action = `/campaign/analysis/detail.do`;
                frm.submit();
            }
        },
    }
})();

campaignAnalysisDetail.init();
