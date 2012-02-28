function(doc) {
  if(doc.type == "dvdprofilerentry"){
		  emit(doc._id , {"collectionNumber": doc.collectionNumber, "title" : doc.title, "genres": doc.genres, "media": doc.media, "purchaseDate": doc.purchaseDate});
  }
}