/**
 * Globals
 */
var INFINITELISTLOADINGMUTEX = {
	"locked" : false
};
var infinitelistloadingsettings = {
	"items" : 100,
	"loadCounter" : 0
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
		console.log("Ende: " + postLoad);
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
		INFINITELISTLOADINGMUTEX.locked = true;
		db.view(design + "/infinitelist", {
			descending : "true",
			limit : infinitelistloadingsettings.items,
			skip : infinitelistloadingsettings.items
					* infinitelistloadingsettings.loadCounter,
			success : function(data) {
				var list = $.mustache($("#mustache-infinitecollectionlist")
						.html(), data);
				if (infinitelistloadingsettings.loadCounter == 0) {
					$("#infinitecollectionlist").html(list);
				} else {
					$("#infinitecollectionlist").append(list);
				}
				infinitelistloadingsettings.loadCounter++;
				INFINITELISTLOADINGMUTEX.locked = false;
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
	var scrollBuffer = 150;

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

$(document).ready(function() {
	createInfiniteList();
	// bind help message
	$('#helpinfinitecollectionlist').click(function() {
		doHelpMessage($("#helpinfinitecollectionlistmessage"));
	});
});
