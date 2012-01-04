/**
 * View is used for thumbnail carousel
 */
function(doc) {
 if(doc.type == "dvdprofilerentry" && doc._attachments){
	emit(doc.purchaseDate, {"title": doc.title, "id": doc._id, "no": doc.collectionNumber});
 }
}