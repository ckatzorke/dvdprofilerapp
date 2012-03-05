function(doc) {
  if(doc.type == "dvdprofilerentry"){
	if(doc.countryOfOrigin == "")
		emit("unknown", 1);
	else
		  emit(doc.countryOfOrigin, 1);
  }
}