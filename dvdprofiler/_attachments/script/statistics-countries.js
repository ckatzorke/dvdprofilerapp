$.fn.statistics('register', 'Countries', function (statisticcontainer) {
	statisticcontainer.append('<div id="countrieschart"></div>');
	var pieData;
	var name = "Countries (Origin)";
	var chart;
	db.view(design + "/countrystatistic", {
		group : "true",
		success : function(data) {
			// generate pie data
			pieData = new Array();
			var total = 0;
			// round 1 - calc total
			$.each(data.rows, function(index, value) { 
				 total+=value.value;
			});
			// round 2 - generate piedata, all smaller than 5% are "Other"
			var other = {name: "Other", y: 0, drilldown: {
				name: 'Other Countries - Details',
				data: []
			}};
			$.each(data.rows, function(index, value) {
				var rate = (value.value / total);
				if(rate < 0.05){
					other.y += value.value;
					other.drilldown.data.push({name: value.key + " (" + value.value + ")", y: value.value});
				} else {
					pieData.push({name: value.key + " (" + value.value + ")", y: value.value});
				}
			});
			//push other
			pieData.push(other);
			chart = new Highcharts.Chart({
				chart : {
					renderTo : 'countrieschart',
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : name
				},
				subtitle: {
					text: 'All countries smaller than 5% are combined in "Other" - click slice for details'
				},
				tooltip : {
					formatter : function() {
						if(this.point.name.substring(0, 3) == "N/A" || this.point.name.substring(0, 3) == "unk" || this.point.name.substring(0,3)=="Oth"){
							var image = ""; 
						}else{
							var image = '<img src="images/flags/'+this.point.name.substring(0, this.point.name.lastIndexOf(' (')).replace(" ", "_").toLowerCase() + '.png">';
						}
						var ret = '<span>' + image + ' ' + this.point.y + ' entries&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
						if(this.point.drilldown){
							ret+="<br><small>Click for details...</small>";
						}
						return ret;
					},
					useHTML: true
					
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						point: {
							events: {
								click: function() {
									var drilldown = this.drilldown;
									if (drilldown) { // drill down
										setChart(drilldown.name, drilldown.data);
									} else { // restore
										if(chart.series[0].name != name){
											setChart(name, pieData);
										}
									}
								}
							}
						},
						dataLabels : {
							enabled : true,
							color : '#000000',
							connectorColor : '#000000',
							
							formatter : function() {
								return '<span><b>' + this.point.name + '</b>: '
										+ (Math.round(this.percentage*100)/100) + ' %</span>';
							}
						}
					}
				},
				series : [ {
					type : 'pie',
					name : name,
					data :	pieData
				} ]
			});
		}
	});
	
	function setChart(name, data) {
		chart.series[0].remove();
		chart.addSeries({
			type: 'pie',
			name: name,
			data: data
		});
	}
});

