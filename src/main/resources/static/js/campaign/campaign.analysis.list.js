const campaignAnalysisList = (() => {
    const frm = document.querySelector('#frm');

    const btnSearch = document.querySelector('#btnSearch');

    const chkAllCheck = document.querySelector('#allCheck');

    const txtKeyword = document.querySelector('#keyword');
    const txtStartDate = document.querySelector('#startDate');
    const txtEndDate = document.querySelector('#endDate');

    const hidSortItem = document.querySelector('#sort_item');
    const hidSortGubun = document.querySelector('#sort_gubun');

    const chkExcels = document.querySelectorAll('#chkExcel');


    return {
        init: function() {
            btnSearch.addEventListener('click', this.search);
            txtKeyword && txtKeyword.addEventListener("keypress", function(e){if(e.keyCode === 13) {campaignAnalysisList.search()}});
            if(document.querySelector('.attr-nodata'))
                document.querySelector('#btnInit').addEventListener('click', this.searchInit);

            chkAllCheck.addEventListener('click', this.chkAll);
            chkExcels.forEach(element => {
                element.addEventListener('click', this.isAllCheck);
            });

            if(txtEndDate.value == '') {
                const today = new Date();
                // txtEndDate.value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
                //     + '-' + ('0' + today.getDate()).slice(-2);
                txtEndDate.placeholder = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + today.getDate()).slice(-2);
            }

            if(txtStartDate.value == '') {
                const today = new Date();
                // const monthAgo = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 30);
                // txtStartDate.value = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                //     + '-' + ('0' + monthAgo.getDate()).slice(-2);
                const monthAgo = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 30);
                txtStartDate.placeholder = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + monthAgo.getDate()).slice(-2);
                // const monthAgo = new Date(today.setMonth(today.getMonth() - 1));
                // txtStartDate.value = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                //     + '-' + ('0' + monthAgo.getDate()).slice(-2);
            }
        },
        searchInit: function() {
            txtKeyword.value = '';
            hidSortItem.value = '';
            hidSortGubun.value = '';
            document.querySelector('#size').value = 30;
            // if(txtEndDate.value == '') {
            //     const today = new Date();
            //     txtEndDate.value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
            //         + '-' + ('0' + today.getDate()).slice(-2);
            // }
            // if(txtStartDate.value == '') {
            //     const today = new Date();
            //     const monthAgo = new Date(today.setMonth(today.getMonth() - 1));
            //     txtStartDate.value = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
            //         + '-' + ('0' + monthAgo.getDate()).slice(-2);
            // }

            campaignAnalysisList.search();
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


            const data = campaignAnalysisList.searchValid();

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

            if(data) {
                frm.page.value = 0;
                frm.action = '/campaign/analysis/list.do';
                frm.submit();
            }
        },
        isAllCheck: function() {
            const chkLen = chkExcels.length;
            let checkedLen = 0;

            chkExcels.forEach(element => {
                if(element.checked) checkedLen++;
            });

            if(chkLen == checkedLen) chkAllCheck.checked = true; else chkAllCheck.checked = false;
        },
        chkAll: function() {
            chkExcels.forEach(element => {
                if(chkAllCheck.checked) element.checked = true; else element.checked = false;
            });
        },
        detail: function(id) {
            let param = '';
            const url = window.location.href;
            const acl = document.querySelector('#acl').value;
            const size = document.querySelector('#size').value;
            const sort_item = document.querySelector('#sort_item').value;
            const sort_gubun = document.querySelector('#sort_gubun').value;
            const page = document.querySelector('#pageNum').value;
            const keyword = document.querySelector('#keyword').value;
            let startDate = document.querySelector('#startDate').value;
            let endDate = document.querySelector('#endDate').value;
            const caGubun = document.querySelector('#caGubun').value;

            const today = new Date();
            const oneWeekBefore = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
            const analysisStartDate = `${oneWeekBefore.getFullYear()}-${('0' + (oneWeekBefore.getMonth() + 1)).slice(-2)}-${('0' + oneWeekBefore.getDate()).slice(-2)}`;
            const analysisEndDate = `${today.getFullYear()}-${('0' + (today.getMonth() + 1)).slice(-2)}-${('0' + today.getDate()).slice(-2)}`;

            if(url.indexOf('size') != -1) param += `&size=${size}`;
            if(url.indexOf('sort_item') != -1) param += `&sort_item=${sort_item}`;
            if(url.indexOf('sort_gubun') != -1) param += `&sort_gubun=${sort_gubun}`;
            if(url.indexOf('page') != -1) param += `&page=${page}`;
            if(url.indexOf('keyword') != -1) param += `&keyword=${keyword}`;
            if(url.indexOf('caGubun') != -1) param += `&caGubun=${caGubun}`;

            if(url.indexOf('startDate') === -1) {
                startDate = analysisStartDate;
            }
            if(url.indexOf('endDate') === -1) {
                endDate = analysisEndDate;
            }

            param += `&startDate=${startDate}`;
            param += `&endDate=${endDate}`;

            location.href = `/campaign/analysis/detail.do?acl=${acl}&id=${id}` + param;
        },
        excelDown: function() {
            const acl = document.querySelector('#acl').value;
            let cnt = 0;
            let ids = '';
            chkExcels.forEach(element => {
                if(element.checked) {
                    ids += ',' + element.value;
                    cnt++;
                }
            });
            ids = ids.substring(1);

            if(cnt > 10) {
                Modal.alert('동시 엑셀다운은 최대 10개 캠페인까지 선택 가능합니다.');
                return;
            }

            window.location.href = `/campaign/analysis/excel/download.do?acl=${acl}&ids=${ids}`;
        },
        excelDownOne: function(ids) {
            const acl = document.querySelector('#acl').value;

            window.location.href = `/campaign/analysis/excel/download.do?acl=${acl}&ids=${ids}`;
        }
    }
})();

campaignAnalysisList.init();
