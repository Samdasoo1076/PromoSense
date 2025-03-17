// Polyfill entries
if(!Object.entries) 
Object.entries = function(obj) {
	return Object.keys(obj).reduce(function(arr, key) {
		arr.push([key, obj[key]]);
		return arr;
	}, []);
}

// Polyfill getSiblings
var getSiblings = function(e) {
	// for collecting siblings
	let siblings = []; 
	// if no parent, return no sibling
	if(!e.parentNode) {
		return siblings;
	}
	// first child of the parent node
	let sibling  = e.parentNode.firstChild;
	// collecting siblings
	while (sibling) {
		if (sibling.nodeType === 1 && sibling !== e) {
			siblings.push(sibling);
		}
		sibling = sibling.nextSibling;
	}
	return siblings;
};

/**
 * campaignToggle
 * 
 * @ProjectDescription
 * @author codenamic@gmail.com
 * @version 1.1
 * 
 * Released on 2022-02-01
 * Copyright (c) 2018,
 *
 * Licensed under the MIT license.
 * http://opensource.org/licenses/MIT
 * 
**/
(function (root, factory) {
	if ( typeof define === 'function' && define.amd ) {
		define([], function () {
			return factory(root);
		});
	} else if ( typeof exports === 'object' ) {
		module.exports = factory(root);
	} else {
		root.campaignToggle = factory(root);
	}
})(typeof global !== 'undefined' ? global : typeof window !== 'undefined' ? window : this, function (window) {

	'use strict';

	//
	// Variables
	//
	var defaults = {
		// general
		mode: 'normal',
		event: 'click',

		activeClass: 'active',
		
		// active: true,
		// inactive: false,
		// inactiveClass: 'inactive',

		// initialTarget: null,

		// disabled: false,
		// disabledClass: null,

		focusin: false,
		focusout: false,

		clickout: false,
		clickoutTarget: null,
		
		// target
		targetClass: 'campaign-toggle-target',
		targetActiveClass: 'active',
		targetAttribute: 'data-toggle-target',

		// inactiveButton
		inactiveButton: false,
		inactiveButtonElement: 'button',
		inactiveButtonClass: 'close',
		inactiveButtonText: 'close',
		inactiveButtonArea: null,

		// dimmed target
		dimmedTarget: null,
		dimmedTargetClass: null,
		dimmedTargetActiveClass: 'active-dimmed',

		// react target
		reactTarget: null,
		reactTargetClass: null,
		reactTargetActiveClass: 'active',

		// react Parent
		reactParent: null,
		reactParentClass: null,
		reactParentActiveClass: 'active',

		// callback
		onloadCallBack: function() {return false;},
		
		eventBeforeCallBack: function() {return false;},
		eventAfterCallBack: function() {return false;},

		activeBeforeCallBack: function() {return false;},
		activeAfterCallBack: function() {return false;},

		inactiveBeforeCallBack: function() {return false;},
		inactiveAfterCallBack: function() {return false;}
	};

	// Merge two or more objects together.
	var extend = function () {
		var merged = {};
		Array.prototype.forEach.call(arguments, function (obj) {
			for (var key in obj) {
				if (!obj.hasOwnProperty(key)) return;
				merged[key] = obj[key];
			}
		});
		return merged;
	};

	// Create the Constructor object
	var Constructor = function(selector, options) {
		
		// Merge user options with defaults
		settings = extend(defaults, options || {});
		
		var publicAPIs = {};
		var settings;
		var lastEventTarget = null;

		publicAPIs.settings = settings;
		publicAPIs.myToggle = {};

		// active
		publicAPIs.active = function(name) {
			active(settings, publicAPIs.myToggle[name]);
		}

		// inactive
		publicAPIs.inactive = function(name) {
			inactive(settings, publicAPIs.myToggle[name]);
		}

		// toggle
		publicAPIs.toggle = function(name) {
			publicAPIs.myToggle[name].toggleTarget.classList.contains(settings.activeClass) ? publicAPIs.inactive(name) : publicAPIs.active(name);
		}

		// update
		publicAPIs.update = function() {			
			let toggleTargets = document.querySelectorAll(selector);
			if (!toggleTargets) return;
			Array.prototype.forEach.call(toggleTargets, function(value, index, array) {
				publicAPIs.myToggle[value.id] = {
					toggleName: value.id,
					toggleTarget: value,
					toggleButton: document.querySelector('[data-campaign-toggle-button="' + value.id + '"]'),
					dimmed: value.dataset.campaignDimmed,
					reactTarget: document.querySelector(settings.reactTarget),
				}

				settings.onloadCallBack.call(this, publicAPIs.myToggle[value.id]);
				publicAPIs.myToggle[value.id].toggleTarget.classList.add(settings.targetClass);

				if (settings.inactiveButton) inactiveButton(settings, publicAPIs.myToggle[value.id]);
				if (settings.focusin) focusin(settings, publicAPIs.myToggle[value.id]);

				if (publicAPIs.myToggle[value.id].toggleButton != null) {
					publicAPIs.myToggle[value.id].toggleButton.addEventListener('click', function(event) {
						publicAPIs.toggle(value.id);
						if (event.currentTarget.nodeName === 'A') event.preventDefault();
					}, false);
				}
			});
		};
		
		// Actions Active
		 function active(settings, toggleThis) {
			settings.eventBeforeCallBack.call(this, toggleThis);
			settings.activeBeforeCallBack.call(this, toggleThis);

			toggleThis.toggleTarget.classList.add(settings.activeClass);
			if (toggleThis.toggleButton != null) toggleThis.toggleButton.classList.add(settings.activeClass);
			if (settings.reactTarget != null) toggleThis.reactTarget.classList.add(settings.reactTargetActiveClass);
			if (settings.reactParent != null) toggleThis.toggleTarget.closest(settings.reactParent).classList.add(settings.reactParentActiveClass);	
			if (toggleThis.dimmed == "true") document.querySelector("html").classList.add(settings.dimmedTargetActiveClass);

			if (settings.focusin) {
				setTimeout(function() {
					toggleThis.toggleTarget.focus();
				}, 100);
			};

			// if (settings.focusin) console.log(toggleThis.toggleTarget);
			if (settings.clickout) clickout(settings, toggleThis);
			if (settings.focusout) focusout(settings, toggleThis);

			settings.eventAfterCallBack.call(this, toggleThis);
			settings.activeAfterCallBack.call(this, toggleThis);

			// console.log(toggleThis);
		};
		
		// Actions Inactive
		function inactive(settings, toggleThis) {		
			settings.eventBeforeCallBack.call(this, toggleThis);
			settings.inactiveBeforeCallBack.call(this, toggleThis);

			toggleThis.toggleTarget.classList.remove(settings.activeClass);
			if (toggleThis.toggleButton != null) toggleThis.toggleButton.classList.remove(settings.activeClass);
			if (settings.reactTarget != null) toggleThis.reactTarget.classList.remove(settings.reactTargetActiveClass);
			if (settings.reactParent != null) toggleThis.toggleTarget.closest(settings.reactParent).classList.remove(settings.reactParentActiveClass);
			if (toggleThis.dimmed == "true") document.querySelector("html").classList.remove(settings.dimmedTargetActiveClass);

			settings.eventAfterCallBack.call(this, toggleThis);
			settings.inactiveAfterCallBack.call(this, toggleThis);
		};	

		// Inactive Button
		 function inactiveButton(settings, toggleThis) {
			var inactiveButton = toggleThis.toggleTarget.querySelector('.' + settings.inactiveButtonClass);

			if (inactiveButton === null) {
				inactiveButton = document.createElement('button');
				inactiveButton.setAttribute('type', 'button');
				inactiveButton.className = settings.inactiveButtonClass;
				inactiveButton.innerHTML = settings.inactiveButtonText;
				
				// Append Inactive Button
				settings.inactiveButtonArea === null ? toggleThis.toggleTarget.appendChild(inactiveButton) : toggleThis.toggleTarget.querySelector(settings.inactiveButtonArea).appendChild(inactiveButton);
			}

			// Inactive event
			inactiveButton.addEventListener('click', function() {
				inactive(settings, toggleThis);
			}, false);
		};

		// reactTarget
		 function reactTarget(settings, toggleThis) {		
			toggleThis.reactTarget.classList.add(settings.activeClass);
		};

		// Inactive by Clickout
		function clickout(settings, toggleThis) {
			document.addEventListener('mouseup', function(event) {
				if (toggleThis.toggleTarget.classList.contains(settings.activeClass) && !toggleThis.toggleTarget.contains(event.target)) {
					inactive(settings, toggleThis);
				}
			});
		};

		// Inactive by focusout
		function focusout(settings, toggleThis) {
			console.log("key setting");
			// window.addEventListener('keyup', function(event) {
			// 	if (event.keyCode === 9) {
			// 		if (!toggleThis.toggleTarget.querySelector(':focus')) {
			// 			inactive(settings, toggleThis);
			// 		}
			// 	}
			// });
		};

		// focus to Active target
		function focusin(settings, toggleThis) {
			toggleThis.toggleTarget.setAttribute('tabindex', '0');
		};

		// Initialize the instance
		var init = function () {
			// Setup the DOM
			publicAPIs.update();
		};

		// Initialize and return the Public APIs
		init();
		return publicAPIs;
	};

	// Return the Constructor
	return Constructor;
});