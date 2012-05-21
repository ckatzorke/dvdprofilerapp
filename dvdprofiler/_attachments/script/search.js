// search
var timeout;
var delay = 1000;
var isLoading = false;
var highlight = "";

function reloadSearch() {
	if (!isLoading) {
		var searchTerm = $("#searchfield").val();
		highlight = searchTerm;
		timeout = setTimeout(function() {
			$('#listcontainer').block({
				message : null
			});
			isLoading = true;
			console.log("searching: " + searchTerm);

			// Simulate a real ajax call
			// setTimeout(function() { isLoading = false; }, 1000);
			db.view(design + "/tags", {
				keys : searchTerm.toLowerCase().split(" "),
				success : function(data) {
					isLoading = false;
					// $.get("templates/searchresult.html", function(txt) {
					// var content = Mark.up(txt, data);
					// $('#searchresult').html(content).show();
					// }, "html");
					var result = {
						count : data.rows.length,
						searchterm : searchTerm
					};
					var content = Mark.up($('#template-searchresult').html(),
							result);
					$('#listcontainer').html(content);
					content = Mark.up($('#template-collectionlist').html(),
							data);
					$('#searchcollectionlist').html(content);
					$('#listcontainer').unblock();
				}
			});
		}, delay);
	}
	// else {
	// log("still loading last search");
	// }
}

$(function() {
	$("#searchfield").keyup(function(e) {
		if(e.keyCode == 13)
			reloadSearch();
		if (e.keyCode > 40) {
			if (timeout) {
				clearTimeout(timeout);
			}
			reloadSearch();
		}
	}).bind('focus', function() {
		$(this).animate({
			width : '+=100px'
		}, 300)
	}).bind('blur', function() {
		$(this).animate({
			width : '-=100px'
		}, 300);
	});
});