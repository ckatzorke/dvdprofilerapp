/**
 * Globals
 */
var views = new Array("listcollectionnumber", "listtitle", "listpurchasedate");
var INFINITELISTLOADINGMUTEX = {
	"locked" : false
};
var infinitelistloadingsettings = {
	"items" : 50,
	"loadCounter" : 0,
	"descending" : true,
	"view" : ""
}

function handleListChange(view){
	infinitelistloadingsettings.loadCounter=0;
	for ( var v in views) {
		rotateButton($('#button-' + views[v]), 0);
		$('#button-' + views[v]).removeClass('button-selected');
	}
	$('#button-'+view).addClass('button-selected');
	if(infinitelistloadingsettings.view==view){
		//invert sorting
		infinitelistloadingsettings.descending=!infinitelistloadingsettings.descending;
		if(infinitelistloadingsettings.descending){
			rotateButton($('#button-'+view), 15);
		} else {
			rotateButton($('#button-'+view), -15);
		}
		console.log("Changing sorting to descending=" +infinitelistloadingsettings.descending );		
	} else {
		infinitelistloadingsettings.view=view;
		infinitelistloadingsettings.descending=false;
		rotateButton($('#button-'+view), -15);
		console.log("Changing view to " + view);
	}
	createInfiniteList();
}

function rotateButton(button, deg){
	var scale ="";
	if(deg!=0){
		scale = "scale(1.25, 1.25)";
	}
	button.css('-moz-transform', scale + ' rotate('+deg+'deg)');
	button.css('-webkit-transform', scale +' rotate('+deg+'deg)');
	button.css('-o-transform', scale + ' rotate('+deg+'deg)');;
	button.css('transform', scale + ' rotate('+deg+'deg)');
	button.css('transform', scale + ' rotate('+deg+'deg)');
}

/**
 * creates the infinite list container. Create first 100 entries and register
 * for post loading
 */
function createInfiniteList() {
	loadInfiniteListItems();
	// add the scroll handler
	$(window).bind("scroll resize", function(event) {
		var postLoad = checkPostLoadInfiniteList();
//		console.log("Ende: " + postLoad);
		if (postLoad) {
			loadInfiniteListItems();
		}
	});

}
/**
 * load items
 */
function loadInfiniteListItems() {
	if (!INFINITELISTLOADINGMUTEX.locked) {
		console.log("postloading items for infinite list");
		showLoading();
		INFINITELISTLOADINGMUTEX.locked = true;
		db.view(design + "/" + infinitelistloadingsettings.view, {
			descending : infinitelistloadingsettings.descending,
			limit : infinitelistloadingsettings.items,
			skip : infinitelistloadingsettings.items
					* infinitelistloadingsettings.loadCounter,
			success : function(data) {
				var list = Mark.up($("#template-infinitecollectionlist")
						.html(), data);
				if (infinitelistloadingsettings.loadCounter == 0) {
					$("#infinitecollectionlist").html(list);
				} else {
					$("#infinitecollectionlist").append(list);
				}
				infinitelistloadingsettings.loadCounter++;
				//$(".fancybox").fancybox();
				INFINITELISTLOADINGMUTEX.locked = false;
				hideLoading();
			},
		});
	}
}

/**
 * Checks if the end is reached, and new items must be loaded. Inspired from
 * http://www.bennadel.com/blog/1801-Creating-An-Infinite-Scroll-Effect-With-jQuery-And-ColdFusion.htm
 */
function checkPostLoadInfiniteList() {
	// Hand the control flow off to the method that
	// worries about the list content.
	var viewTop = $(window).scrollTop();
	var viewBottom = (viewTop + $(window).height());

	// Get the offset of the bottom of the list container.
	//
	// NOTE: I am using the container rather than the list
	// itself since the list has FLOATING elements, which
	// might cause the UL to report an inacturate height.
	var container = $("#infinitecollectionlistcontainer");
	var containerBottom = Math.floor(container.offset().top
			+ container.height());

	// I am the scroll buffer; this is the amount of
	// pre-bottom space we want to take into account
	// before we start loading the next items.
	var scrollBuffer = 100;

	// Check to see if the container bottom is close
	// enought (with buffer) to the scroll of the
	// window to trigger the loading of new items.
	if ((containerBottom - scrollBuffer) <= viewBottom) {

		// The bottom of the container is close enough
		// to the bottom of thew view frame window to
		// imply more item loading.
		return (true);

	} else {

		// The container bottom is too far below the view
		// frame bottom - no new items needed yet.
		return (false);
	}
}

Mark.pipes.replace = function (str, n, m) {
    return str.replace(n, m);
};

$(document).ready(function() {
	handleListChange(views[0]);
});
