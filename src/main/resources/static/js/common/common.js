const constants = {
    pcPurposeUrl: 'https://www.bdirectshop.com/my/Cs_Complete.do',
    mobilePurposeUrl:'https://m.bdirectshop.com/my/Cs_Complete.do',
    pcPurposeUrlTB: 'https://btvcable.shop/my/Cs_Complete.do',
    mobilePurposeUrlTB: 'https://m.btvcable.shop/my/Cs_Complete.do',
    pcPurposeUrlBW: 'https://www.bworld.co.kr/shop/com/cs/done.do',
    mobilePurposeUrlBW: 'https://www.bworld.co.kr/shop/com/cs/done.do',
    imageType: 'png,jpg,gif,jpeg'
};

const popupType = Object.freeze({
    'content': 'contentPopup',
    'slide': 'postsReadPopup'
});

const Popup = {
    open: (url, id, options = {type: popupType.content, sortable:false}) => {
        const elem = document.querySelector(`#${id}`);
        if (elem) {
            document.body.removeChild(elem);
        }

        domsFetch(url)
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const popup = parser.parseFromString(html, 'text/html');
                // popup 생성
                document.body.appendChild(popup.documentElement.querySelector(`#${id}`));

                //script 생성
                for (const elem of popup.querySelectorAll('script')) {
                    const script = document.createElement('script')
                    if (elem.src) {
                        script.src = elem.src;
                    } else {
                        script.append(elem.text);
                    }
                    document.querySelector(`#${id}`).appendChild(script);
                }

                formModuleB.update();
                if(options.type === popupType.content || !options.type) {
                    contentPopup.update();
                    contentPopup.active(id);
                } else if(options.type === popupType.slide) {
                    postsReadPopup.update();
                    postsReadPopup.active(id);
                }

                if(options.sortable) {
                    sortable(".attr-draggable > .data-list");
                }
            })
            .catch(e => console.log(e));
    },
    close: (id) => {
        const element = document.querySelector(`#${id}`);
        const buiToggle = element.getAttribute('data-bui-toggle');
        if(buiToggle === popupType.content) {
            contentPopup.inactive(id);
        } else if (buiToggle === popupType.slide) {
            postsReadPopup.inactive(id);
        }

        const popup = document.querySelector(`#${id}`);
        document.body.removeChild(popup);
    }
}

const Preview = {
    info: async (url) => {
        await Modal.alert('PC화면으로 보이는 경우 원활한 미리보기를 위해\n개발자 도구(F12)에서 Device를 모바일 화면으로 변경\n후 메시지를 확인해 보세요.');
        const blankOpen = window.open('about:blank');
        blankOpen.location.href = url;
    }
}

const Modal = {
    alert: (message, fn_ok) => new Promise((resolve, reject) =>{
        const btnOk = document.getElementById('alert.btnOk');
        const cloneBtnOk = btnOk.cloneNode(true);

        btnOk.parentNode.replaceChild(cloneBtnOk, btnOk);

        const popupMessage = document.getElementById('alert.message');
        popupMessage.textContent = message;

        cloneBtnOk.addEventListener("click", () => infoPopup.inactive('alert'));
        if(fn_ok) {
            cloneBtnOk.addEventListener("click", () => fn_ok());
        }
        cloneBtnOk.addEventListener("click", () => resolve(true));

        infoPopup.active('alert');
    }),
    confirm: (message, fn_ok, fn_cancel) => new Promise((resolve, reject) =>{
        const btnOk = document.getElementById('confirm.btnOk');
        const btnCancel = document.getElementById('confirm.btnCancel');
        const cloneBtnOk = btnOk.cloneNode(true);
        const cloneBtnCancel = btnCancel.cloneNode(true);

        btnOk.parentNode.replaceChild(cloneBtnOk, btnOk);
        btnCancel.parentNode.replaceChild(cloneBtnCancel, btnCancel);

        const popupMessage = document.getElementById('confirm.message');
        popupMessage.textContent = message;

        cloneBtnOk.addEventListener("click", () => infoPopup.inactive('confirm'));
        cloneBtnCancel.addEventListener("click", () => infoPopup.inactive('confirm'));
        if(fn_ok) {
            cloneBtnOk.addEventListener("click", () => {
                fn_ok();
            });
        }
        cloneBtnOk.addEventListener("click", () => resolve(true));

        if(fn_cancel) {
            cloneBtnCancel.addEventListener("click", () => {
                fn_cancel();
            });
        }
        cloneBtnCancel.addEventListener("click", () => resolve(false));

        infoPopup.active('confirm');
    })
}

const domsFetch = function(...args) {
    return new Promise((resolve, reject) => {
        fetch(...args)
            .then(response => {
                if(response.ok || response.status === 400) {
                    resolve(response);
                } else if(response.status === 403) {
                    window.location.href = "/error?code=403";
                } else {
                    window.location.href = "/error?code=500";
                }
            })
            .catch(reason => console.log('exception: ', reject(reason)))
    });
}

const capture = {
    img: async (target, callback) => {
        let result = '';
        const test = await html2canvas(target)

        let myImg = test.toDataURL('image/png');
        myImg = myImg.replace('data:image/png;base64,', '');

        if (callback) {
            callback(myImg);
        }
    }
}

async function logout() {
    if (await Modal.confirm("로그아웃 하시겠습니까?")) {
        window.location.href = "/logout";
    }
}
