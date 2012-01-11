/**
 * View is used for DVDProfiler Infinite List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	emit(doc.collectionNumber, {"id": doc._id, "collectionNumber" : doc.collectionNumber, "title": doc.title, "genres": doc.genres});
 }
}