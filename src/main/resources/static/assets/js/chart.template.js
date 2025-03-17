// Load Charts and the corechart package.
google.charts.load('current', {'packages':['corechart']});

function drawBarChart(selector, data, accentColor) {
	var accent = ['#000000', '#e6223d', '#ee952c', '#3c9ba1', '#2c7aee', '#9b48ce'];
	var options = {
		height: 320,
		fontName: 'Open Sans, Noto Sans KR',
		legend: {
			position: 'top',
			alignment: 'end',
			maxLines: 1,
			width: 540,
			textStyle: {
				fontSize: 14,
				color: '#656565',
			},
		},
		series: {
			0: {
				color: '#e6223d',
			},
		},
		hAxis: {
			textStyle: {
				fontSize: 14,
				color: '#909090',
			},
		},
		vAxis: {
			textStyle: {
				fontSize: 14,
				color: '#909090',
			},
			viewWindowMode: 'explicit',
		},
		chartArea: {
			top: 48,
			right: 0,
			bottom: 32,
			left: 60,
		},
		seriesType: 'bars',
		series: {
			0: {color: accent[accentColor]},
			1: {color: '#909090'}
		},
		bar: {
			width: 32,
		},
		tooltip: {
			isHtml: true,
		},
	};
	
	var chart = new google.visualization.ComboChart(document.getElementById(selector));
	chart.draw(data, options);

	var timer;
	window.addEventListener('resize', function() {
		clearTimeout(timer);
		timer = setTimeout(function() {
			chart.draw(data, options);
		}, 400);
	});
};

function drawLineChart(selector, data, accentColor) {
	var accent = ['#000000', '#e6223d', '#ee952c', '#3c9ba1', '#2c7aee', '#9b48ce'];
	var options = {
		height: 320,
		fontName: 'Open Sans, Noto Sans KR',
		legend: {
			position: 'top',
			alignment: 'end',
			maxLines: 5,
			width: 240,
			textStyle: {
				fontSize: 14,
				color: '#656565',
			},
		},
		series: {
			0: {
				color: accent[accentColor],
			},
		},
		hAxis: {
			textStyle: {
				fontSize: 14,
				color: '#909090',
			},
		},
		vAxis: {
			textStyle: {
				fontSize: 14,
				color: '#909090',
			},
			viewWindowMode: 'explicit',
			viewWindow: {
				max: 30,
				min: 10,
			}
		},
		chartArea: {
			top: 48,
			right: 0,
			bottom: 32,
			left: 60,
		},
		pointSize: 11,
		tooltip: {
			isHtml: true,
		},
	};

	var chart = new google.visualization.LineChart(document.getElementById(selector));
	chart.draw(data, options);
	
	var timer;
	window.addEventListener('resize', function() {
		clearTimeout(timer);
		timer = setTimeout(function() {
			chart.draw(data, options);
		}, 400);
	});
};