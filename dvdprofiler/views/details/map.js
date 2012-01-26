function(doc) {
  if(doc.type == "dvdprofilerentry"){
		  emit(doc._id , doc);
  }
}