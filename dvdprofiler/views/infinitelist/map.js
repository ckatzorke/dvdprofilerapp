/**
 * View is used for DVDProfiler Infinite List
 */
function(doc) {
 if(doc.type == "dvdprofilerentry"){
	emit(doc.collectionNumber, doc.title);
 }
}