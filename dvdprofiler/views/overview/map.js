function(doc) {
  if(doc.type == "dvdprofilerentry"){
		  emit("count" , 1);
  }
}