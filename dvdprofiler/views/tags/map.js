/**
 * View is used for DVDProfiler List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	 var title = doc.title.replace(/[^a-zA-Z0-9\s]/g, "");
	 var titleTags = new Array();
	 if(title.indexOf(" ")>-1){
		 var tmp = title.split(" ");
		 for(var i = 0;i<tmp.length;i++){
			 titleTags[tmp[i]] = tmp[i];
		 }
	 }
	 else 
		 titleTags[title]=title;
	 if(doc.originalTitle){
		 title = doc.originalTitle.replace(/[^a-zA-Z0-9\s]/g, "");
		 if(title.indexOf(" ")>-1){
			 var tmp = title.split(" ");
			 for(var i = 0;i<tmp.length;i++){
				 titleTags[tmp[i]] = tmp[i];
			 }
		 }
		 else 
			 titleTags[title]=title;
	 }
	 for (var t in titleTags){
		 if(titleTags[t]!="")
			 emit(titleTags[t].toLowerCase(), {"id": doc._id, "collectionNumber" : doc.collectionNumber, "title": doc.title, "originalTitle": doc.originalTitle, "genres": doc.genres, "media": doc.mediaType, "purchaseDate" : doc.purchaseDate, "description": doc.overview, "country": doc.countryOfOrigin, "releaseDate": doc.releaseDate, "productionYear": doc.productionYear, "studios": doc.studios, "actors": doc.actors});
	 }
//	if(doc.genres){
//		for ( var idx in doc.genres) {
//			emit(doc.genres[idx], doc._id);
//		}
//	}
 }
}