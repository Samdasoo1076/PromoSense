var frontUI = function() {
	var me = this;
	me.desk = matchMedia("(min-width: 1280px)");
	me.tablet = matchMedia("(max-width: 1279px)");
	me.mobile = matchMedia("(max-width: 1023px)");
	me.body = $("body");
	me.scrollbarWidth = window.innerWidth - $("body").width(); //modal Background ScrollBar
	me.scrollTop;

	$(document).ready(function(){
		me.firstLoad();
		me.headerFixed();
	});

	$(window).resize(function(){		
		if(me.mobile.matches){
			isMobile = true;
		}else if(me.tablet.matches){
			isMobile = true;
		}else{
			isMobile = false;
		}
	});

	$(window).scroll(function() {
		me.headerFixed();
	});
	
	if(me.mobile.matches){
		isMobile = true;
	}else if(me.tablet.matches){
		isMobile = true;
	}else{
		isMobile = false;
	}
}

frontUI.prototype = {
	firstLoad: function(){
		setTimeout(function(){
			$("#wrap").animate({opacity:1});
		}, 200)
	},

	headerFixed: function(){
		var me = this;
		var scrollTop = $(window).scrollTop();
		var headerHeight = $(".header").innerHeight();
		if(scrollTop > headerHeight){ //스크롤이 header 높이값보다 내려가면
			$(".header").addClass("toFixed");
		}else {
			$(".header").removeClass("toFixed");
		}
	},}

var front = new frontUI ();

