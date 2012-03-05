var genres = new Array();
function createTable() {
	showLoading();
	db.view(design + "/table", {
		success : function(data) {
			var markup = Mark.up($('#template-table').html(), data);
			$('#table').html(markup);
			$('#table_all').dataTable({
				"bSort" : true,
				"aaSorting": [[ 0, "desc" ]],
				"iDisplayLength" : 50,
			     "sPaginationType": "full_numbers"
			});
			/* Add events */
		    $('#table_all tbody tr').live('mouseenter', function (e) {
		        var sTitle;
		        var nTds = $('td', this);
		        var sId = $('div', nTds[1]).attr('id');
		        console.log("ID: " + sId);
		        xOffset = 10;
				yOffset = 30;
				
				// these 2 variable determine popup's distance from the cursor
				// you might want to adjust to get the right result
				
				/* END CONFIG */
				$("body").append("<p id='cover'><img src='/dvdprofiler/"+ sId +"/thumbnail_f' alt='url preview' onerror='this.src=\"images/noimage.jpg\";this.height=\"81\"; this.width=\"67\"'/></p>");								 
				$("#cover")
					.css("top",(e.pageY - xOffset) + "px")
					.css("left",(e.pageX + yOffset) + "px")
					.fadeIn("fast");	
		    } );
		    $('#table_all tbody tr').live('mousemove', function (e) {
		    	$("#cover")
				.css("top",(e.pageY - xOffset) + "px")
				.css("left",(e.pageX + yOffset) + "px");
		    });
		    $('#table_all tbody tr').live('mouseleave', function (e) {
		    	$("#cover").remove();
		    });
			hideLoading();
		}
	});
};

$(document).ready(function() {
	createTable();
	// bind help message
	$('#helptablegallery').click(function() {
		doHelpMessage($("#helptablegallerymessage"));
	});

	
});
