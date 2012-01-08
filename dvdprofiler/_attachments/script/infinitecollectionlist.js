function createInfiniteList(){
	db.view(design + "/infinitelist?", {
		descending 	: "true",
		limit		: 100,
		success : function(data) {
			var list = $.mustache($("#mustache-infinitecollectionlist").html(), data);
			$("#infinitecollectionlist").html(list);
		}
	});
}

$(document).ready(function() {
	createInfiniteList();
	//bind help message
	 $('#helpinfinitecollectionlist').click(function() { 
	        doHelpMessage($("#helpinfinitecollectionlistmessage"));
	    }); 
});
