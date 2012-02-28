var genres = new Array();
function createGenres() {
	showLoading();
	db.view(design + "/genrestatistic",
			{
				group	: "true",
				success : function(data) {
					$("#genresgallery").html(Mark.up($("#template-genres").html(),
							data));
					hideLoading();
				}
			});
};

$(document).ready(function() {
	createGenres();
	// bind help message
	$('#helpgenresgallery').click(function() {
		doHelpMessage($("#helpgenresgallerymessage"));
	});
});
