var thumbnailgallerysettings = {
	/*current scroll index*/
	thumbnailindex : 1, /* we start with 1 for easier calculating*/
	/*number of thumbnails shown*/
	thumbnailitems : 9,
	/*number of entries loaded from view*/
	thumbnailinterval : 20,
	/*number of view queries*/
	thumbnailfetchcounter : 0
}
function createThumbnailGallery() {
	db.view(design + "/thumbnailgallery", {
		descending : "true",
		limit : thumbnailgallerysettings.thumbnailinterval,
		success : function(data) {
			var thumbnailgallery = Mark.up($("#template-thumbnailgallery")
					.html(), data);
			$("#carousel-thumbnailgallery").html(thumbnailgallery);
			$('#carousel-thumbnailgallery').carouFredSel(
					{
						items 		: thumbnailgallerysettings.thumbnailitems,
						width 		: 800,
						onCreate		: function( items ) {
							loadImages( items );
						},
						scroll 		: {
							delay 			: 3000,
							pauseOnHover 	: true ,
							onBefore		: function( oldItems, newItems){
								loadImages(newItems);
							}/*,
							onAfter : function(oldItems, newItems) {
								thumbnailgallerysettings.thumbnailindex++;
								if(thumbnailgallerysettings.thumbnailindex > (thumbnailgallerysettings.thumbnailinterval/thumbnailgallerysettings.thumbnailitems)){
									thumbnailgallerysettings.thumbnailindex = 1;
								}
								//if nothing left 2 scroll, then fetch next interval...
								if((thumbnailgallerysettings.thumbnailinterval - (thumbnailgallerysettings.thumbnailindex * thumbnailgallerysettings.thumbnailitems)) == 0){
									console.log("downloading new items...");
									db.view(design + "/thumbnailgallery", {
										descending 	: "true",
										limit 		: thumbnailgallerysettings.thumbnailinterval,
										skip		: ++thumbnailgallerysettings.thumbnailfetchcounter*thumbnailgallerysettings.thumbnailinterval,
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

function loadImages(items){
	items.each(function(){
//		console.log("loading " + $(this).attr('original'));
		$(this).attr('src', $(this).attr('original'));
	});
}

$(document).ready(function() {
	createThumbnailGallery();
	//bind help message
	 $('#helpthumbnailgallery').click(function() { 
	        doHelpMessage($("#helpthumbnailgallerymessage"));
	    }); 
});
