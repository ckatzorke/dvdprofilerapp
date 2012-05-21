/**
 * View is used for DVDProfiler List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	emit(doc.title, {"id": doc._id, "collectionNumber" : doc.collectionNumber, "title": doc.title, "originalTitle": doc.originalTitle, "genres": doc.genres, "media": doc.mediaType, "purchaseDate" : doc.purchaseDate, "description": doc.overview, "country": doc.countryOfOrigin,"releaseDate": doc.releaseDate, "productionYear": doc.productionYear, "studios": doc.studios, "actors": doc.actors, "review": doc.review});
 }
}