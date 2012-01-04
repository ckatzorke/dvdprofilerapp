function createOverview() {
	db.view(design + "/media", {
		group : "true",
		success : function(data) {
			console.log(data);
			var overview = $.mustache($("#mustache-overview").html(), data);
			$("#overview").html(overview);
			$('#carousel-overview').jcarousel({
				auto : 2,
				wrap : 'last',
				visible : 1,
				scroll : 1,
				animation : "slow",
				buttonNextHTML : null,
				buttonPrevHTML : null
			});
		}
	});
};
$(document).ready(function(){
	createOverview();
});