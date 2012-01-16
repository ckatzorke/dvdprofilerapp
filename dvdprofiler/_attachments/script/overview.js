function createOverview() {
	db.view(design + "/media", {
		group : "true",
		success : function(data) {
			var overview = Mark.up($("#template-overview").html(), data);
			$("#overviewcontainer").html(overview);
		}
	});
};


$(document).ready(function() {
	createOverview();
});
