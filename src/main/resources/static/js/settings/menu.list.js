const menuList = (() => {
    const btnMainCat = document.querySelector("#btnMainCat");
    const btnMainSort = document.querySelector("#btnMainSort");
    const acl = document.querySelector("#acl").value;

    const linkMainMenuName = document.querySelectorAll("a[name='divMainMenuName']");
    const linkMiddleMenuName = document.querySelectorAll("a[name='divMiddleMenuName']");
    const btnMidCat = document.querySelectorAll("button[name='btnMidCat']");
    const btnMidSort = document.querySelectorAll("button[name='btnMidSort']");

    return {
        init: function() {
            btnMainCat && btnMainCat.addEventListener("click", this.saveMainCategory);
            btnMainSort && btnMainSort.addEventListener("click", this.saveMainSort);
            btnMidCat && btnMidCat.forEach(elem => elem.addEventListener("click", this.saveMiddleCategory));
            btnMidSort && btnMidSort.forEach(elem => elem.addEventListener("click", this.saveMiddleSort));
            linkMainMenuName && linkMainMenuName.forEach(elem =>  elem.addEventListener("click", this.modifyMainCategory))
            linkMiddleMenuName && linkMiddleMenuName.forEach(elem => elem.addEventListener("click", this.modifyMiddleCategory))
        },
        saveMainCategory: function(event) {
            Popup.open(`/settings/menu/popup/major/detail.view?acl=${acl}`, 'majorMenuSavePopup');
        },
        saveMainSort: function() {
            Popup.open(`/settings/menu/popup/order/detail.view?acl=${acl}&type=major`, 'contentPopupMenuOrder', {sortable: true});
        },
        saveMiddleCategory: function() {
            Popup.open(`/settings/menu/popup/middle/detail.view?acl=${acl}&parent_no=${event.currentTarget.dataset.parent_menu_id}`, 'middleMenuSavePopup');
        },
        saveMiddleSort: function(event) {
            Popup.open(`/settings/menu/popup/order/detail.view?acl=${acl}&type=middle&menu_id=${event.currentTarget.dataset.parent_menu_id}`, 'contentPopupMenuOrder', {sortable: true});
        },
        modifyMainCategory: function() {
            Popup.open(`/settings/menu/popup/major/detail.view?acl=${acl}&menu_id=${event.currentTarget.dataset.menu_id}`, 'majorMenuSavePopup');
        },
        modifyMiddleCategory: function() {
            Popup.open(`/settings/menu/popup/middle/detail.view?acl=${acl}&menu_id=${event.currentTarget.dataset.menu_id}&parent_no=${event.currentTarget.dataset.parent_menu_id}`, 'middleMenuSavePopup');
        },
    }
})();

menuList.init();