function(doc) {
  if(doc.type == "dvdprofilerentry"){
	  for(var idx in doc.genres){
		  emit(doc.genres[idx] , doc);
	  }
  }
}