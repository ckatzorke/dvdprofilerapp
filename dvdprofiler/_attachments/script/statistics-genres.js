function createGenresStatistic() {
	var genres = new Array();
	showLoading();
	db.view(design + "/genrestatistic", {
		group : "true",
		success : function(data) {
			//generate pie data
			var pieData = new Array();
			var total = 0;
			$.each(data.rows, function(index, value) { 
				 pieData.push(new Array(value.key + " (" + value.value + ")", value.value));
				 total+=value.value;
			});
			console.log(total);
			var chart = new Highcharts.Chart({
				chart : {
					renderTo : 'statistics',
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : 'Genres'
				},
				tooltip : {
					formatter : function() {
						return '<b>' + this.point.name + '</b>: '
								+ (Math.round(this.percentage*100)/100)  + ' %';
					}
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							formatter : function() {
								return '<b>' + this.point.name + '</b>: '
										+ (Math.round(this.percentage*100)/100) + ' %';
							}
						}
					}
				},
				series : [ {
					type : 'pie',
					name : 'Browser share',
					data :	pieData,
					/*data : [ [ 'Firefox', 45.0 ], [ 'IE', 26.8 ], {
						name : 'Chrome',
						y : 12.8,
						sliced : true,
						selected : true
					}, [ 'Safari', 8.5 ], [ 'Opera', 6.2 ], [ 'Others', 0.7 ] ]*/
				} ]
			});
			hideLoading();
		}
	});
};

