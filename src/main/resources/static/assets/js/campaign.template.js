/**
 * @layout campaignPopupleadBoard
 * @module campaignToggle
 **/
const campaignLeadBoard = new campaignToggle('[data-campaign-toggle="campaignLeadBoard"]', {
	reactTarget: "html",
	reactTargetActiveClass: "active-campaign-lead-board",
	focusin: true,
	focusout: true,
});

/**
 * @layout campaignDefaultPopup
 * @module campaignToggle
 **/
const campaignDefaultPopup = new campaignToggle('[data-campaign-toggle="campaignDefaultPopup"]', {
	reactTarget: "html",
	reactTargetActiveClass: "active-campaign-default-popup",
	focusin: true,
	focusout: true,
	dimmedTargetActiveClass: "active-campaign-default-popup-dimmed",
});

/**
 * @layout campaignScrollPopup
 * @module campaignToggle
 **/
const campaignScrollPopup = new campaignToggle('[data-campaign-toggle="campaignScrollPopup"]', {
	reactTarget: "html",
	reactTargetActiveClass: "active-campaign-scroll-popup",
	focusin: true,
	focusout: true,
	dimmedTargetActiveClass: "active-campaign-scroll-popup-dimmed",
});

/**
 * @layout campaignFloatingPopup
 * @module campaignToggle
 **/
const campaignFloatingPopup = new campaignToggle('[data-campaign-toggle="campaignFloatingPopup"]', {
	reactTarget: "html",
	reactTargetActiveClass: "active-campaign-floating-popup",
	focusin: true,
	focusout: true,
	dimmedTargetActiveClass: "active-campaign-floating-popup-dimmed",
});

/**
 * @layout campaignActionFloatingPopup
 * @module campaignToggle
 **/
const campaignActionFloatingPopup = new campaignToggle('[data-campaign-toggle="campaignActionFloatingPopup"]', {
	reactTarget: "html",
	reactTargetActiveClass: "active-campaign-action-floating-popup",
	focusin: true,
	focusout: true,

	dimmedTargetActiveClass: "active-campaign-action-floating-popup-dimmed",
});

/*
let scrollSwiper = new Swiper("#campaignScrollPopupTypeC .scroll-slides", {
	// slidesPerView: 'auto',
	autoplay: {
		delay: 2500,
		disableOnInteraction: true,
	},
	loop: true,
	slidesPerView: 1,
	spaceBetween: 0,
	autoHeight: true,
	pagination: {
		el: ".scroll-slides .swiper-pagination",
		type: "fraction",
	},
});*/
