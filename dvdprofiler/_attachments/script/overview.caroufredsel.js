function createOverview() {
	db.view(design + "/media", {
		group : "true",
		success : function(data) {
			var overview = $.mustache($("#mustache-overview").html(), data);
			$("#overview").html(overview);
			$('#carousel-overview').carouFredSel();
		}
	});
};


$(document).ready(function() {
	createOverview();
});
