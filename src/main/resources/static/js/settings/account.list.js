const accountList = (() => {
    const frm = document.querySelector('#frm');
    const btnSearch = document.querySelector('#btnSearch');
    const txtKeyword = document.querySelector('#keyword');
    const txtStartDate = document.querySelector('#startDate');
    const txtEndDate = document.querySelector('#endDate');
    const acl = document.querySelector('#acl').value;

    return {
        init: function() {
            btnSearch && btnSearch.addEventListener("click", this.search);
            txtKeyword && txtKeyword.addEventListener("keypress", function(e){if(e.keyCode === 13) {accountList.search()}});

            if(txtEndDate.value == '') {
                const today = new Date();
                txtEndDate.value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + today.getDate()).slice(-2);
            }

            if(txtStartDate.value == '') {
                const today = new Date();
                const monthAgo = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 30);
                txtStartDate.value = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + monthAgo.getDate()).slice(-2);
            }
            //this.search();
        },
        search: function() {
            if(new Date(txtStartDate.value) > new Date(txtEndDate.value)) {
                Modal.alert('기간을 정확히 입력해 주세요.');
                return;
            }
            frm.page.value = 0;
            frm.action = '/settings/account/list.do';
            frm.submit();
        },
        excelDown: function () {
            location.href = `/settings/account/excel/download.do?acl=${acl}&keyword=` + txtKeyword.value
                + '&startDate=' + txtStartDate.value + '&endDate=' + txtEndDate.value;
        }
    }
})();

accountList.init();