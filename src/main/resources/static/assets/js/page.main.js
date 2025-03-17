
/**
 * @layout buiFullPage
 * 현재 사용하지 않음
 */
function buiFullPage() {
	if (document.addEventListener) {
		document.addEventListener("mousewheel", MouseWheelHandler, false); //IE9, Chrome, Safari, Oper
		document.addEventListener("DOMMouseScroll", MouseWheelHandler, false); //Firefox
	} else {
		document.attachEvent("onmousewheel", MouseWheelHandler); //IE 6/7/8
	}
	
	var i = 0;
	var mouseWheel = true;
	var scrollArea = document.querySelector('#wrap');
	
	function MouseWheelHandler(e) {
		if (!mouseWheel) {
			return false;
		}
	
		mouseWheel = false;
		setTimeout(function() {
			mouseWheel = true;
		}, 800); // Stop mouse wheel event for 3 seconds

		e = scrollArea.event || e;
		var delta = Math.max(-1, Math.min(1, e.wheelDelta || -e.detail));
		var h = scrollArea.offsetHeight;
		// var h = window.innerHeight;
		var section = document.querySelectorAll(".content-body .section");
	
		// console.log(delta + ', ' + i + ',' + h * i);
		// console.log(section.length);
	
		if (i <= section.length && i >= 0) {
			if (delta < 0) {
				//scrolling down?
				if (i < section.length) i++;
				i > 0 ? document.querySelector('#header').classList.add('scroll-start') : document.querySelector('#header').classList.remove('scroll-start');			

				if (i == section.length ) {
					// console.log('last')
					scrollArea.scrollTo({
						top: document.querySelector('#page').scrollHeight,
						behavior: "smooth"
					});
				} else {
					scrollArea.scrollTo({
						top: h * i,
						behavior: "smooth"
					});
				}
			} else {
				//scrolling up?
				if (i > 0) i--;
				scrollArea.scrollTo({
					top: h * i,
					behavior: "smooth"
				});
				i > 0 ? document.querySelector('#header').classList.add('scroll-start') : document.querySelector('#header').classList.remove('scroll-start');
			}
		} else {
			// i = 0;
			// console.log(i);
			// scrollArea.scrollTo({
			// 	top: 0,
			// 	behavior: "smooth"
			// });
		}
	}
}
// if (Detectizr.device.type == 'desktop') buiFullPage();

/**
 * @layout gotoMainContent
 */
function gotoMainContent() {
	var elem = document.querySelector('.goto-main-content');
	elem.addEventListener('click', function(e) {
		e.preventDefault();
		var headerHeight = document.querySelector('#header').offsetHeight;
		window.scrollTo({
			top: innerHeight - headerHeight,
			behavior: "smooth"
		});

	});
}
gotoMainContent();

/**
 * @layout deviceAspectRatio
 */
function deviceAspectRatio(viewWidth, viewHeight) {
	if (checkAspectRatio(viewWidth, viewHeight) == 'landscape') {
		document.querySelector('body').style.setProperty('--background-size', '100vw auto');
	} else {
		document.querySelector('body').style.setProperty('--background-size', 'cover');
	}

	var timer;
	window.addEventListener('resize', function() {
		clearTimeout(timer);
		timer = setTimeout(function() {
			if (checkAspectRatio(viewWidth, viewHeight) == 'landscape') {
				document.querySelector('body').style.setProperty('--background-size', '100vw auto');
			} else {
				document.querySelector('body').style.setProperty('--background-size', 'cover');
			}
		}, 400);
	});
}
deviceAspectRatio(16, 9);