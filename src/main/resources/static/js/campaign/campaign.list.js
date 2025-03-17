const campaignList = (() => {
    const frm = document.querySelector('#frm');
    const btnSearch = document.querySelector('#btnSearch');
    const btnViewOrder = document.querySelector('#btnViewOrder');
    const txtKeyword = document.querySelector('#keyword');
    const txtStartDate = document.querySelector('#startDate');
    const txtEndDate = document.querySelector('#endDate');
    const txtTaskFlag = document.querySelector('#taskFlag');
    const txtOrderFlag = document.querySelector('#orderFlag');
    const txtActiveCnt = document.querySelector('#activeCnt');
    const caFlag = document.querySelector('#caFlag');
    const txtCaGubun = document.querySelector('#caGubun');
    const txtPage = document.querySelector('#page');
    const txtSize = document.querySelector('#size');



    const spanDels = document.querySelectorAll("span[name='spanDel']");
    const chkUseTf = document.querySelectorAll("input[name='useTf']");
    const spanView = document.querySelectorAll("span[name='spanView']");


    const btnReset = document.querySelector('#btnReset');

    const acl = document.querySelector('#acl').value;

    return {
        init: function() {
            btnSearch && btnSearch.addEventListener("click", this.search);
            txtKeyword && txtKeyword.addEventListener("keypress", function(e){if(e.keyCode === 13) {campaignList.search()}});
            btnReset && btnReset.addEventListener("click", this.reset);
            btnViewOrder && btnViewOrder.addEventListener("click", this.viewOrderPopup);

            spanDels.forEach(elem => {
                elem.addEventListener("click", this.delete)
            });
            chkUseTf.forEach(elem=>{
                elem.addEventListener("change", this.checkState)
            });
            spanView.forEach(elem => {
                elem.addEventListener("click", this.listDetail)
            });

            if(txtEndDate.value == '') {
                const today = new Date();
                txtEndDate.placeholder = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + today.getDate()).slice(-2);
            }

            if(txtStartDate.value == '') {
                const today = new Date();
                const monthAgo = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 30);
                txtStartDate.placeholder = monthAgo.getFullYear() + '-' + ('0' + (monthAgo.getMonth() + 1)).slice(-2)
                    + '-' + ('0' + monthAgo.getDate()).slice(-2);
            }
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
            const data = campaignList.searchValid();
            if(data) {
                frm.page.value = 0;
                frm.action = `/campaign/list/list.do?acl=${acl}`;
                frm.submit();
            }
        },
        delete: async function () {
            const caNo = event.currentTarget.dataset.id;
            // return;
            Modal.confirm('해당 캠페인을 삭제 하시겠습니까?', ()=> {
                fetch(`/campaign/list/delete.do?acl=${acl}&caNo=${caNo}`)
                    .then(async (response) => {
                        if (response.ok) {
                            Modal.alert("해당 캠페인이 정상적으로 삭제 되었습니다.", ()=>{
                                window.location.reload();
                            });
                        } else {
                            // const data = await response.json();
                            Modal.alert("에러 발생");
                        }
                    })
                    .catch(() => alert('에러 발생'));
            });
        },
        basePopup: function(caNo) {
            if(document.querySelector('#postsReadPopupCampaign')) {
                Popup.close('postsReadPopupCampaign');
            }
            Popup.open(`/campaign/popup/base.do?acl=${acl}&caNo=${caNo}&page=${txtPage.value}`, 'postsReadPopupCampaign', {type: popupType.slide});
        },
        viewOrderPopup: function() {
            const count =  txtActiveCnt.value;
            Popup.open(`/campaign/popup/view/order.do?acl=${acl}&count=${count}&keyword=${txtKeyword.value}&startDate=${txtStartDate.value}&endDate=${txtEndDate.value}&caFlag=${caFlag.value}&caGubun=${txtCaGubun.value}&page=${txtPage.value}&size=${txtSize.value}`, 'contentPopupExposureRank', {sortable: true});
        },
        listDetail: function() {
            const caNo = event.currentTarget.dataset.id;
            frm.caNo.value = caNo;
            frm.action = `/campaign/list/detail.do?acl=${acl}`;
            frm.submit();
        },
        reset: function() {

            txtKeyword.value = '';
            txtStartDate.value = '';
            txtEndDate.value = '';
            txtTaskFlag.value = '';
            txtOrderFlag.value = '';
            txtSize.value = '30';

            frm.action = `/campaign/list/list.do?acl=${acl}`;
            frm.submit();
        },
        checkState: async function() {
            const caNo = event.currentTarget.dataset.id;
            const check = event.currentTarget.checked;
            let useTf;

            if(check){
                useTf = "Y";
            }else{
                useTf = "N";
            }

            Modal.confirm('선택한 캠페인의 상태를 변경 하시겠습니까?', () => {
                domsFetch(`/campaign/campaign/switch.do?caNo=${caNo}&useTf=${useTf}`)
                    .then(async (response) => {
                        if (response.ok) {
                            window.location.reload();
                        } else {
                            // const data = await response.json();
                            Modal.alert("상태변경 실패");
                        }
                    })
                    .catch(e => console.log('error: ', e));
            }, () =>{
                window.location.reload();
            })


        },

    }
})();

campaignList.init();
