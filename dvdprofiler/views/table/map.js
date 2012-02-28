/**
 * View is used for DVDProfiler List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	 var blu = false;
	 var dvd = false;
	 var hddvd = false;
	 for(var idx in doc.mediaType){
		 if(doc.mediaType[idx]=="BluRay"){
			 blu = true;
		 } else if (doc.mediaType =="DVD"){
			 dvd = true;
		 } else {
			 hddvd = true;
		 }
	 }
	emit(doc.title, {"id": doc._id, "collectionNumber" : doc.collectionNumber, "title": doc.title, "genres": doc.genres, "isBluray": blu, "isDvd": dvd, "isHddvd": hddvd, "purchaseDate" : doc.purchaseDate});
 }
}