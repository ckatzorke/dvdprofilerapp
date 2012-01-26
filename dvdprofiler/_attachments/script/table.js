var genres = new Array();
function createTable() {
	showLoading();
	db.view(design + "/listtitle", {
		success : function(data) {
			console.log("möüp");
			var markup = Mark.up($('#template-table').html(), data);
			$('#table').html(markup);
			$('#table_all').dataTable({
				"bSort"				:	true,
				"iDisplayLength" 	: 25
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
