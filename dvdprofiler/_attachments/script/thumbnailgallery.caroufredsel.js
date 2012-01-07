//current scroll index
var thumbnailindex = 1; // we start with 1 for easier calculating
//number of thumbnails shown
var thumbnailitems = 5;
//number of entries loaded from view
var thumbnailinterval = 2000;
//number of view queries
var thumbnailfetchcounter = 0;
function createThumbnailGallery() {
	db.view(design + "/thumbnailgallery", {
		descending : "true",
		limit : thumbnailinterval,
		success : function(data) {
			var thumbnailgallery = $.mustache($("#mustache-thumbnailgallery")
					.html(), data);
			$("#carousel-thumbnailgallery").html(thumbnailgallery);
			$('#carousel-thumbnailgallery').carouFredSel(
					{
						items 		: thumbnailitems,
						width 		: 610,
						scroll 		: {
							delay : 1000,
							pauseOnHover : true /*,
							onAfter : function(oldItems, newItems) {
								thumbnailindex++;
								if(thumbnailindex > (thumbnailinterval/thumbnailitems)){
									thumbnailindex = 1;
								}
								//if nothing left 2 scroll, then fetch next interval...
								if((thumbnailinterval - (thumbnailindex * thumbnailitems)) == 0){
									console.log("downloading new items...");
									db.view(design + "/thumbnailgallery", {
										descending 	: "true",
										limit 		: thumbnailinterval,
										skip		: ++thumbnailfetchcounter*thumbnailinterval,
										success : function(data) {
											var thumbnailgallery = $.mustache($("#mustache-thumbnailgallery")
													.html(), data);
											var domTG = $(thumbnailgallery);
											$("#carousel-thumbnailgallery").trigger( 'insertItem', domTG);
										}
									});
								}
							}*/

						}
					});
		}
	});
};

$(document).ready(function() {
	createThumbnailGallery();
});
