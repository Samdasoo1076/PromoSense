
/**
 * @layout checkDevice
 **/
function checkDevice() {
	if(navigator.userAgent.match(/Mobile/)) {
		document.querySelector('html').classList.remove('laptop');
		document.querySelector('html').classList.add('mobile');

	} else {
		document.querySelector('html').classList.remove('mobile');
		document.querySelector('html').classList.add('laptop');
	}
}
checkDevice();


const buiFormfileAdd = function(formElem) {
	formElem.parentElement.dataset.buiFormFileName = formElem.files[0].name;
	formElem.parentElement.dataset.buiFormFileType = formElem.files[0].type;
	formElem.parentElement.style.setProperty('--background-image', 'url(' + URL.createObjectURL(formElem.files[0]) + ')');
}

/**
 * @name	buiFormfileUploader
 * @update	2022-08-05
 **/
const buiFormfileUploader = function(selectors) {
	if (formElem.value.length > 0) {
		buiFormfileAdd(formElem);
	} else {
		buiFormfileAdd(formElem);
	}
}


/**
 * @name clickOut
 **/
const clickOut = function(event, selector, activeClass) {
	if (!selector.contains(event.target)) {
		selector.classList.remove(activeClass);
	}
}

/**
 * @name focusOut
 **/
const focusOut = function(event, selector, activeClass) {
	if(event.code === 'Tab') {
		selector.querySelector('*:focus') != null ? selector.classList.add(activeClass) : selector.classList.remove(activeClass);
	}
}


/**
 * @name focusWithin
 **/
const focusWithin = function(selector) {
	selector.classList.add('focus-within');

	document.addEventListener('keyup', function(event) {
		focusOut(event, selector, 'focus-within');
	}, false);
}


// 라디오 버튼 및 체크박스 체크여부 확인
function checkedWithin(selector, closestSelector) {
	if (selector.type == 'radio') {
		Array.prototype.forEach.call(selector.closest(closestSelector).parentElement.querySelectorAll(closestSelector), function(siblings) {
			siblings.classList.remove('checked-within');
		});

		selector.closest(closestSelector).classList.add("checked-within");
	}

	if (selector.type == 'checkbox') {
		if (selector.checked == true) {
			selector.closest(closestSelector).classList.add("checked-within");
		} else {
			selector.closest(closestSelector).classList.remove("checked-within");
		}
	}
}

/**
 * @layout localSide
 **/
const localSide = new buiExpand('[data-bui-expand="localSide"]', {
	accordion: false,
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">펼치기</span>',
	buttonActiveText: '<span class="btn-text">접기</span>',
	buttonAppendArea: '.section-func .button-area',
	onloadCallBack: function(self) {
		let funcContainer = document.createElement('div');
		funcContainer.className = 'section-func';
		funcContainer.innerHTML= '<span class="button-area"></span>';
		self.target.querySelector('.section-wrap').appendChild(funcContainer);
	}
});


/**
 * @layout localNavigations
 **/
const localNavigations = new buiNav("#localSide .lnb-item", {
	// mode: 'toggle',
	navName: '.lnb-name',
	subList: '.snb-list',
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">펼치기</span>',
	buttonActiveText: '<span class="btn-text">접기</span>',

	activeAfterCallBack: function() {
		localSide.active(0);
	},

	// inactiveAfterCallBack: function() {
	// 	localSide.inactive(0);
	// },
});


/**
 * @layout subNavigations
 **/
const subNavigations = new buiNav("#localSide .snb-item", {
	mode: 'toggle',
	navName: '.snb-name',
	subList: '.sub-list',
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">펼치기</span>',
	buttonActiveText: '<span class="btn-text">접기</span>',
});

/**
 * @layout campaignAnalysis
 **/
const campaignAnalysis = new buiExpand('[data-bui-expand="campaignAnalysis"]', {
	buttonClass: "btn expand",
	buttonText: '<span class="btn-text">펼치기</span>',
	buttonActiveText: '<span class="btn-text">접기</span>',
	buttonAppendArea: ".data-head",
});


/**
 * @module
 **/
function findElement(selector, target, height) {
	var elem = document.querySelector(selector);
	if (!elem) return;

	document.querySelector(target).style.setProperty('padding-bottom', height);
}

function buiFormDncrementor(elem) {
	const formElem = elem.parentElement.querySelector('.form-elem');
	formElem.stepDown();
}

function buiFormIncrementor(elem) {
	const formElem = elem.parentElement.querySelector('.form-elem');
	formElem.stepUp();
}


function buiFormColorpicker(selector) {
	selector.parentElement.dataset.buiFormValue = selector.value;
	selector.parentElement.style.setProperty("--bui-form-value", selector.value);
}


const datepickerLanguage = {
	days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
	daysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
	daysMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	months: ['January','February','March','April','May','June', 'July','August','September','October','November','December'],
	monthsShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	today: 'Today',
	clear: 'Clear',
	dateFormat: 'yyyy-MM-dd',
	timeFormat: 'hh:ii aa',
	firstDay: 0
};

let dataPickerArray = [];

const datepicker = function(selector) {
	const datepickers = document.querySelectorAll(selector);
	if (!datepickers) return;

	datepickers.forEach(function(datepicker, index) {
		let createDatepicker = new AirDatepicker(datepicker, {
			locale: datepickerLanguage,
			navTitles: {
				days: "yyyy.MM",
				months: "yyyy",
			},
			autoClose: true,
			selectedDates: new Date(datepicker.value),
		});

		dataPickerArray.push(createDatepicker);
	});

	dataPickerArray.forEach(function(datepicker, index) {
		dataPickerArray[index].update({
			onSelect({date}) {
				if (datepicker.$el.dataset.buiFormDateStart != undefined) {
					dataPickerArray.forEach(function(datepickerDateEnd, index) {
						if (datepickerDateEnd.$el.dataset.buiFormDateEnd != undefined) {
							dataPickerArray[index].update({
								minDate: date,
							});

							// start의 값을 end에 반영
							// dataPickerArray[index].selectDate(date);
						}
					});
				}
			}
		});
	});
};
datepicker(".form.datepicker .form-elem:not(:read-only)");

/**
 * @layout checkScrollStart
 **/
window.scrollY > 0 ? document.querySelector('html').classList.add('active-scroll-start') : document.querySelector('html').classList.remove('active-scroll-start');
window.addEventListener('scroll', function() {
	window.scrollY > 0 ? document.querySelector('html').classList.add('active-scroll-start') : document.querySelector('html').classList.remove('active-scroll-start');
});

/**
 * @layout infoPopup
 * @module buiToggle
 **/
const infoPopup = new buiToggle('[data-bui-toggle="infoPopup"]', {
	inactiveButton: true,
	inactiveButtonClass: "btn popup-close",
	inactiveButtonText: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24px" height="24px"><title>닫기</title><path d="M17.9569 7.45717C18.3475 7.06664 18.3475 6.43348 17.9569 6.04295C17.5664 5.65243 16.9332 5.65243 16.5427 6.04295L12.1843 10.4014L7.82771 6.04479C7.43719 5.65427 6.80402 5.65427 6.4135 6.04479C6.02297 6.43532 6.02297 7.06848 6.4135 7.45901L10.7701 11.8156L6.41444 16.1712C6.02392 16.5618 6.02392 17.1949 6.41444 17.5854C6.80497 17.976 7.43813 17.976 7.82866 17.5854L12.1843 13.2298L16.5418 17.5873C16.9323 17.9778 17.5655 17.9778 17.956 17.5873C18.3465 17.1968 18.3465 16.5636 17.956 16.1731L13.5985 11.8156L17.9569 7.45717Z"/></svg>',
	inactiveButtonArea: ".popup-local-func .button-area",
	reactTarget: "html",
	reactTargetActiveClass: "active-info-popup",
	focusin: true,
	focusout: true,
	onloadCallBack: function(myToggle) {
		const popupFuncContainer = myToggle.toggleTarget.querySelector(".popup-local");
		const popupFunc = document.createElement("div");
		popupFunc.className = "popup-local-func";
		popupFunc.innerHTML = '<span class="button-area"></span>';
		popupFuncContainer.appendChild(popupFunc);
	},
});


/**
 * @layout contentsPopup
 * @module buiToggle
 **/
const contentPopup = new buiToggle('[data-bui-toggle="contentPopup"]', {
//   inactiveButton: true,
//   inactiveButtonClass: "btn popup-close",
//   inactiveButtonText:
//     '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" width="28px" height="28px"><title>닫기</title><path d="M20.7339 8.91573C21.1895 8.46012 21.1895 7.72142 20.7339 7.26581C20.2782 6.8102 19.5396 6.8102 19.0839 7.26581L13.9991 12.3506L8.91643 7.26796C8.46082 6.81234 7.72212 6.81234 7.26651 7.26796C6.8109 7.72357 6.8109 8.46226 7.26651 8.91787L12.3492 14.0006L7.26762 19.0821C6.81201 19.5377 6.81201 20.2764 7.26762 20.732C7.72323 21.1877 8.46192 21.1877 8.91753 20.732L13.9991 15.6505L19.0828 20.7342C19.5384 21.1898 20.2771 21.1898 20.7327 20.7342C21.1884 20.2786 21.1884 19.5399 20.7327 19.0843L15.649 14.0006L20.7339 8.91573Z"/></svg>',
//   inactiveButtonArea: ".popup-local-func .button-area",
	reactTarget: "html",
	reactTargetActiveClass: "active-content-popup",
	focusin: true,
	focusout: true,

//   onloadCallBack: function (myToggle) {
//     const popupFuncContainer =
//       myToggle.toggleTarget.querySelector(".popup-local");
//     const popupFunc = document.createElement("div");
//     popupFunc.className = "popup-local-func";
//     popupFunc.innerHTML = '<span class="button-area"></span>';
//     popupFuncContainer.appendChild(popupFunc);
//   },
});

/**
 * @layout postsReadPopup
 * @module buiToggle
 **/
const postsReadPopup = new buiToggle('[data-bui-toggle="postsReadPopup"]', {
	inactiveButton: true,
	inactiveButtonClass: "btn popup-close",
	inactiveButtonText: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" width="28px" height="28px"><title>닫기</title><path d="M20.7339 8.91573C21.1895 8.46012 21.1895 7.72142 20.7339 7.26581C20.2782 6.8102 19.5396 6.8102 19.0839 7.26581L13.9991 12.3506L8.91643 7.26796C8.46082 6.81234 7.72212 6.81234 7.26651 7.26796C6.8109 7.72357 6.8109 8.46226 7.26651 8.91787L12.3492 14.0006L7.26762 19.0821C6.81201 19.5377 6.81201 20.2764 7.26762 20.732C7.72323 21.1877 8.46192 21.1877 8.91753 20.732L13.9991 15.6505L19.0828 20.7342C19.5384 21.1898 20.2771 21.1898 20.7327 20.7342C21.1884 20.2786 21.1884 19.5399 20.7327 19.0843L15.649 14.0006L20.7339 8.91573Z"/></svg>',
	inactiveButtonArea: ".popup-local-func .button-area",
	reactTarget: "html",
	reactTargetActiveClass: "active-postsRead-popup",
	focusin: true,
	focusout: true,

	onloadCallBack: function (myToggle) {
		const popupFuncContainer = myToggle.toggleTarget.querySelector(".popup-local");
		const popupFunc = document.createElement("div");
		popupFunc.className = "popup-local-func";
		popupFunc.innerHTML = '<span class="button-area"></span>';
		popupFuncContainer.appendChild(popupFunc);
	},
});


/**
 * @layout noticePopup
 * @module buiToggle
 */
const noticePopup = new buiToggle('[data-bui-toggle="noticePopup"]', {
	reactTarget: 'html',
	reactTargetActiveClass: 'active-notice-popup',
	focusin: true,
	focusout: true,
});


/**
 * @layout defaultTab
 * @module buiTab
 **/
const defaultTab = new buiTab('[data-bui-tab="defaultTab"]');


/**
 * @layout
 * @module buiToggle
 **/
const formFold = new buiToggle('[data-bui-toggle="dataFinderFilter"]', {
	reactTarget: "html",
	focusin: true,
	focusout: true,
});


//dropdown
const dropdown = function(selector) {
	let elems = document.querySelectorAll(selector);

	Array.prototype.forEach.call(elems, function(elem) {
		if (!elem) return;

		// dropdown setup
		let dropdownSubject = elem.querySelector(".dropdown-subject");
		dropdownSubject.tabIndex = 0;

		dropdownSubject.addEventListener("click", function() {
			if (!elem.classList.contains("active")) {
				elem.classList.add("active");
			} else {
				elem.classList.remove("active");
			}
		});

		document.addEventListener("mouseup", function(event) {
			if (elem.classList.contains("active") && !elem.contains(event.target)) {
				elem.classList.remove("active");
			}
		});

		Array.prototype.forEach.call(elem.querySelectorAll(".navi-item"), function(naviItem) {
			let dropdownNaviName = naviItem.querySelector(".navi-name");
			if (naviItem.getAttribute("title") != null) dropdownSubject.dataset.buiDropdownSelected = dropdownNaviName.textContent;

			dropdownNaviName.addEventListener("click", function(event) {
				// event.preventDefault();
				Array.prototype.forEach.call(elem.querySelectorAll(".navi-item"), function(selectedItem) {
					selectedItem.removeAttribute("title");
					selectedItem.classList.remove("selected");
				});

				dropdownSubject.dataset.buiDropdownSelected = this.textContent;
				this.parentElement.title = "선택됨";
				this.parentElement.classList.add("selected");
				elem.classList.remove("active");
			}),
				false;
		});
	});
}
dropdown(".dropdown.module-c");


// timepicker
const timepicker = function(selector) {
	let elems = document.querySelectorAll(selector);

	Array.prototype.forEach.call(elems, function(elem) {
		if (!elem) return;
		let timepickerInput = elem.querySelector(".timepicker .form-elem");
		timepickerInput.tabIndex = 0;

		timepickerInput.addEventListener("click", function() {
			if (!elem.classList.contains("active")) {
				elem.classList.add("active");
			} else {
				elem.classList.remove("active");
			}

			// if (timepickerInput.getAttribute("readonly")) {
			// 	elem.classList.remove("active");
			// }
		});

		document.addEventListener("mouseup", function(event) {
			if (elem.classList.contains("active") && !elem.contains(event.target)) {
				elem.classList.remove("active");
			}
		});
	});
};

timepicker(".timepicker.module-b");



const setDecimalNumber = Number.prototype.pad = function(size) {
	var s = String(this);
	while (s.length < (size || 2)) {s = "0" + s;}
	return s;
}



function setTimeUp(elem, target) {
	if (!target) return;
	target.stepUp();

	if (target.value == target.max) {
		target.value = target.min;
	}
}


function setTimeDown(elem, target) {
	if (!target) return;
	target.stepDown();

	if (target.value == target.min) {
		target.value = target.max;
	}
}

function setDecimalNumberTest(event) {
	console.log(event);
}


// oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"


/**
 * @module moreDataTooltip
 **/
const moreDataTooltip = new buiExpand('[data-bui-expand="moreDataTooltip"]', {
	clickout: true,
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">옵션 열기</span>',
	buttonActiveText: '<span class="btn-text">옵션 닫기</span>',
	activeAfterCallBack: function(self) {
		// let navNames = self.target.querySelectorAll('.navi-name');
		// Array.prototype.forEach.call(navNames, function(navName) {
		// 	navName.addEventListener('click', function() {
		// 		moreDataTooltip.inactive(self.index);
		// 	});
		// });
	},
});


const expandSubsection = new buiExpand('[data-bui-expand="subsection"]', {
	// accordion: true,
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">본문 열기</span>',
	buttonActiveText: '<span class="btn-text">본문 닫기</span>',
	buttonAppendArea: '.subsection-head',
});

/**
 * @layout localSide
 **/
const dataBuiExpandTest = new buiExpand('[data-bui-expand="test"]', {
	// clickout: true,
	accordion: true,
	buttonClass: 'btn expand',
	buttonText: '<span class="btn-text">펼치기</span>',
	buttonActiveText: '<span class="btn-text">접기</span>',
	buttonAppendArea: '.data-bui-expand-head',
	buttonAppendTo: 'afterBegin',
});

// sortable
const sortable = function (selector) {
	const sortables = document.querySelectorAll(selector);
	if (!sortables) return;

	sortables.forEach(function (sortables) {
		new Sortable(sortables, {
			animation: 150,
			ghostClass: "sortable-ghost", // Class name for the drop placeholder
			chosenClass: "sortable-chosen", // Class name for the chosen item
			dragClass: "sortable-drag", // Class name for the dragging item
			forceFallback: true, // ignore the HTML5 DnD behaviour and force the fallback to kick in
		});

	});
};
sortable(".attr-draggable > .data-list");

const formModuleB = new buiForm(".form.module-b .form-elem[type='text'], .form.module-b .form-elem[type='url'], .form.module-b .form-elem[type='search'], .form.module-b.textarea .form-elem");

const campaignPopupPreview = function(elem) {
	let campaignPopup = elem.closest('.campaign-preview').querySelector('.campaign-popup');
	Array.prototype.forEach.call(elem.closest('.tab-list').querySelectorAll(".tab-item"), function(tabItem) {
		tabItem.classList.remove("current");
		tabItem.removeAttribute("title");
		elem.classList.add("current");
		elem.setAttribute("title", "선택됨");
		campaignPopup.dataset.buiDevice = elem.dataset.buiTabItem;
	});
};

//
// const campaignDetailSwiper = new Swiper(".message-preview-slides", {
// 	slidesPerView: 'auto',
// 	spaceBetween: 20,
// 	autoHeight: true,
// 	simulateTouch: false,
// 	navigation: {
// 		nextEl: '.swiper-button-next',
// 		prevEl: '.swiper-button-prev',
// 	},
// });

let swiperOptions = {
	loop: false,
	slidesPerView: 1,
	spaceBetween: 0,
	touchRatio: 0, // 드래그 막기
	autoHeight: true,
	pagination: {
		el: ".scroll-slides .swiper-pagination",
		type: "fraction",
	},
	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},
}

if (!location.pathname.includes('/campaign/message/regist.view')) {
	swiperOptions.loop = true;
	swiperOptions.on = {
		slideChangeTransitionEnd: function () {
			let idx = this.realIndex + 1; // 1부터 시작하는 인덱스

			// '#multiSubject'를 제대로 찾기 위해 querySelector 사용
			const multiSubjectElement = document.querySelector('#multiSubject');
			if (multiSubjectElement) {
				multiSubjectElement.textContent = `멀티형 메시지 ${idx}`;
			}
		}
	}
}

const campaignDetailPopSwiper =
	new Swiper(".page-campaign .scroll-slides", swiperOptions);

const updateSlide = function () {
	setTimeout(function (){
		campaignDetailPopSwiper.forEach(function(item, key) {
			campaignDetailPopSwiper[key].update();
		});
	}, 100);
}

const removeSlide = function (index) {
	campaignDetailPopSwiper.forEach(function(item, key) {
		campaignDetailPopSwiper[key].removeSlide(index);
	});

	updateSlide();
}

const slideTo = function (index) {
	campaignDetailPopSwiper.forEach(function(item, key) {
		campaignDetailPopSwiper[key].slideTo(index);
	});
}

const addSlide = function (template) {
	if (template === '') return false;

	campaignDetailPopSwiper.forEach(function(item, key) {
		campaignDetailPopSwiper[key].appendSlide(`<div class="swiper-slide">${template}</div>`);
	});

	updateSlide();
}