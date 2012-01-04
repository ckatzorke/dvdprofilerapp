function createThumbnailGallery() {
	db.view(design + "/thumbnailgallery", {
		descending : "true",
		success : function(data) {
			var thumbnailgallery = $.mustache($("#mustache-thumbnailgallery").html(), data);
			$("#thumbnailgallery").html(thumbnailgallery);
			$('#carousel-thumbnailgallery').carouFredSel(
				{
					auto: {
						items 			: 10,
						duration		: 5000,
						easing			: "linear",
						pauseDuration	: 0,
						pauseOnHover	: "immediate"
					}
				}
			);
		}
	});
};


$(document).ready(function() {
	createThumbnailGallery();
});
