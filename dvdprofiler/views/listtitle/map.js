/**
 * View is used for DVDProfiler List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	emit(doc.title, {"id": doc._id, "collectionNumber" : doc.collectionNumber, "title": doc.title, "genres": doc.genres, "media": doc.mediaType, "purchaseDate" : doc.purchaseDate});
 }
}