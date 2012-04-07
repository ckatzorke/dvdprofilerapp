/**
 * View is used for DVDProfiler List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	 var title = doc.title.replace(/[^a-zA-Z0-9\s]/g, "");
	 if(title.indexOf(" ")>-1)
	 var titleTags = title.split(" ");
	 else var titleTags = new Array(title);
	 for (var t in titleTags){
		 emit(titleTags[t], doc._id);
	 }
	if(doc.genres){
		for ( var idx in doc.genres) {
			emit(doc.genres[idx], doc._id);
		}
	}
 }
}