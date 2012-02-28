/***
 * maps all dvds to genres, i.e. with the reduce function a sum of all dvds a genre can be created.
 */
function(doc) {
  if(doc.type == "dvdprofilerentry"){
	  for(var idx in doc.genres){
		  emit(doc.genres[idx] , 1);
	  }
//	  emit(doc.genres[0] , 1);
  }
}