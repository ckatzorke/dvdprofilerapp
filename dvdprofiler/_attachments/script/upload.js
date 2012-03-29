$("#uploadcollection").submit(function(e) { // invoke callback on submit
	e.preventDefault();
	var data = {};
	$.each($("form :input").serializeArray(), function(i, field) {
		data[field.name] = field.value;
	});
	$("form :file").each(function() {
		data[this.name] = this.value; // file inputs need special handling
	});

	if (!data._attachments || data._attachments.length == 0) {
		alert("Please select a file to upload.");
		return;
	}

	$(this).ajaxSubmit({
		url : "/dvdprofiler/c",
		success : function(resp) {
			$('#saved').fadeIn().animate({
				opacity : 1.0
			}, 3000).fadeOut();
		}
	});
});