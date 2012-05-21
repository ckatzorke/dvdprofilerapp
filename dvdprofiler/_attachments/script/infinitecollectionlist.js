/**
 * Globals
 */
var views = new Array("listpurchasedate", "listcollectionnumber", "listtitle");
var INFINITELISTLOADINGMUTEX = {
	"locked" : false
};
var infinitelistloadingsettings = {
	"items" : 50,
	"loadCounter" : 0,
	"descending" : true,
	"view" : ""
}

function initInfiniteList() {
	highlight = "";
	$('#listcontainer').block({
		message : null
	}).html($('#template-infinitelist').html()).unblock();
	db.view(design + "/genrestatistic", {
		group : "true",
		success : function(data) {
			var key, value;
			for ( var i = 0; i < data.rows.length; i++) {
				key = data.rows[i].key;
				value = key + " (" + data.rows[i].value + ")";
				$('#genrefilterchosen').append($('<option>', {
					value : key
				}).text(value));
			}
			$('.chzn-select').chosen().change(function() {
				console.log($('#genrefilterchosen').val());
			});
			$('#genrefilter').show();
		}
	});
}

function handleListChange(view) {
	// $('#listcontainer').block({message: null});
	infinitelistloadingsettings.loadCounter = 0;
	for ( var v in views) {
		rotateButton($('#button-' + views[v]), 0);
		$('#button-' + views[v]).removeClass('button-selected');
	}
	$('#button-' + view).addClass('button-selected');
	if (infinitelistloadingsettings.view == view) {
		// invert sorting
		infinitelistloadingsettings.descending = !infinitelistloadingsettings.descending;
		if (infinitelistloadingsettings.descending) {
			rotateButton($('#button-' + view), 15);
		} else {
			rotateButton($('#button-' + view), -15);
		}
		console.log("Changing sorting to descending="
				+ infinitelistloadingsettings.descending);
	} else {
		infinitelistloadingsettings.view = view;
		infinitelistloadingsettings.descending = false;
		rotateButton($('#button-' + view), -15);
		console.log("Changing view to " + view);
	}
	createInfiniteList();
}

function rotateButton(button, deg) {
	var scale = "";
	if (deg != 0) {
		scale = "scale(1.25, 1.25)";
	}
	button.css('-moz-transform', scale + ' rotate(' + deg + 'deg)');
	button.css('-webkit-transform', scale + ' rotate(' + deg + 'deg)');
	button.css('-o-transform', scale + ' rotate(' + deg + 'deg)');
	;
	button.css('transform', scale + ' rotate(' + deg + 'deg)');
	button.css('transform', scale + ' rotate(' + deg + 'deg)');
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
		// console.log("Ende: " + postLoad);
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
		// showLoading();
		$('#infinitecollectionlistscrollinfo').show();
		INFINITELISTLOADINGMUTEX.locked = true;
		db.view(design + "/" + infinitelistloadingsettings.view, {
			descending : infinitelistloadingsettings.descending,
			limit : infinitelistloadingsettings.items,
			skip : infinitelistloadingsettings.items
					* infinitelistloadingsettings.loadCounter,
			success : function(data) {
				var list = Mark.up($("#template-collectionlist").html(), data);
				if (infinitelistloadingsettings.loadCounter == 0) {
					$("#collectionlist").html(list);
				} else {
					$("#collectionlist").append(list);
				}
				infinitelistloadingsettings.loadCounter++;
				// $(".fancybox").fancybox();
				INFINITELISTLOADINGMUTEX.locked = false;
				// hideLoading();
				$('#infinitecollectionlistscrollinfo').hide();
				// $('#listcontainer').unblock();
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
	var container = $("#listcontainer");
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

$(document).ready(function() {
	initInfiniteList();
	handleListChange(views[0]);
});
