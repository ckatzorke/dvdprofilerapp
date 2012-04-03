function createOverview() {
	db.view(design + "/media", {
		group : "true",
		success : function(data) {
			$.get("templates/overview.html", function(txt) {
				var overview = Mark.up(txt, data);
				$("#overviewcontainer").html(overview);
			}, "html");

		}
	});
};

$(document).ready(function() {
	createOverview();
});
