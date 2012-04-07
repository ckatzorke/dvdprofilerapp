function(doc) {
  if(doc.type == "dvdprofilerentry"){
	if(doc.countryOfOrigin == "")
		emit("N/A", 1);
	else
		  emit(doc.countryOfOrigin, 1);
  }
}