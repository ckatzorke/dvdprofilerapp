var genres = new Array();
function createGenres() {
	showLoading();
	db.view(design + "/genres",
			{
				success : function(data) {
					for ( var idx in data.rows) {
						var row = data.rows[idx];
						if (genres[row.key] == null) {
							genres[row.key] = new Array();
						}
						genres[row.key].push(row.value);
					}
					var genresgallery = "";
					for ( var key in genres) {
						var preview = new Array();
						for ( var i = 0; i <= 2; i++) {
							preview.push(genres[key][i]);
						}
						var context = {
							key : key,
							preview : preview
						};
						genresgallery += Mark.up($("#template-genres").html(),
								context);
					}
					$("#genresgallery").html(genresgallery);
					$('.notes').mobilynotes({
						init : 'rotate',
						autoplay : false,
						showList : false
					});
					// bind autoplay when mouseover, off when leave
					$('.notes').bind('mouseleave', function() {
						$(this).mobilynotes({
							init : 'rotate',
							autoplay : false,
							showList : false
						});
					});
					$('.notes').bind('mouseenter', function() {
						$(this).mobilynotes({
							init : 'rotate',
							autoplay : true,
							showList : false
						});
					});
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
