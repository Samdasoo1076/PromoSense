/**
 * @layout checkDevice
 */
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


/**
 * @layout pageNavigation
 * @module buiToggle
 */
 const pageNavigation = new buiToggle('[data-bui-toggle="pageNavigation"]', {
	inactiveButton: true,
	inactiveButtonClass: 'btn close',
	inactiveButtonText: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24px" height="24px"><title>닫기</title><path d="M12.99,12l6.16,6.16-.99,.99-6.16-6.16-6.16,6.16-.99-.99,6.16-6.16L4.85,5.84l.99-.99,6.16,6.16,6.16-6.16,.99,.99-6.16,6.16Z"/></svg>',
	inactiveButtonArea: '.section-func .button-area',
	reactTarget: 'html',
	reactTargetActiveClass: 'active-page-navi',	
	focusin: true,
	focusout: true,
	//clickout: true,	
	onloadCallBack: function(myToggle) {
		const toggleFuncContainer = myToggle.toggleTarget.querySelector('.section-wrap');
		const toggleFunc = document.createElement('div');
		toggleFunc.className = 'section-func'
		toggleFunc.innerHTML = '<span class="button-area"></span>';
		toggleFuncContainer.appendChild(toggleFunc);
	}
});

const datepickerLanguage = {
	days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
	daysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
	daysMin: ['일', '월', '화', '수', '목', '금', '토'],
	months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	monthsShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	today: 'Today',
	clear: 'Clear',
	dateFormat: 'yyyy-MM-dd',
	timeFormat: 'hh:ii aa',
	firstDay: 0,
};

const datepicker = function(selector) {
	const datepickers = document.querySelectorAll(selector);
	if (!datepickers) return;

	datepickers.forEach(function(datepickers) {
		new AirDatepicker(datepickers, {
			locale: datepickerLanguage,
			// isMobile: true,
			// autoClose: true,
		});
	});
}

datepicker('.form.datepicker .form-elem:not(:read-only)');