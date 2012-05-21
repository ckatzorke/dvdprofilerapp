// Apache 2.0 J Chris Anderson 2011
var path;
var design;
var db;
$(function() {
	// friendly helper http://tinyurl.com/6aow6yn
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	path = unescape(document.location.pathname).split('/'), design = path[3],
			db = $.couch.db(path[1]);

	// ?
	var changesRunning = false;
	function setupChanges(since) {
		if (!changesRunning) {
			var changeHandler = db.changes(since);
			changesRunning = true;
			changeHandler.onChange(drawItems);
		}
	}
});

function doHelpMessage(helpmessageNode) {
	$.blockUI({
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			opacity : .9,
			color : '#fff',
			'text-align' : 'left'
		},
		message : helpmessageNode
	});
}

function showLoading() {
	$.blockUI({
		message : "Loading items...",
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		}
	});
}

function hideLoading() {
	$.unblockUI();
}

var jsonDetails;
function openDetails(id) {
	console.log("opening " + path[1] + "/" + id);
	$.getJSON("/" + path[1] + "/" + id, function(json) {
		jsonDetails = json;
		$.get("templates/details.html", function(txt) {
			var content = Mark.up(txt, jsonDetails);
			$.fancybox.open(content, {
				scrolling : "no"
			});
		}, "html");
	});
}


$(document).ready(
		function() {
			db.view(design + "/overview", {
				reduce : true,
				success : function(data) {
					var total = data.rows[0].value;
					var randomBG = Math.round(Math.random() * total);
					db.view(design + "/details", {
						limit : 1,
						skip : randomBG,
						success : function(data) {
							var randomBGId = data.rows[0].id;
							$("body").css(
									"background-image",
									"url(/dvdprofiler/" + randomBGId
											+ "/thumbnail_f)")
						},
					});
				},
			});
		});