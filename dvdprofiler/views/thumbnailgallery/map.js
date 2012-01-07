/**
 * View is used for thumbnail carousel
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	emit(doc.purchaseDate, doc.title);
 }
}