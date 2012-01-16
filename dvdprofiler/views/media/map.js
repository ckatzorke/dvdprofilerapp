/***
 * maps all dvds to specific type, i.e. with the reduce function a sum of all dvds of specific type can be created.
 * Use
 * http://127.0.0.1:5984/dvdprofiler/_design/dvdprofiler/_view/media
 * to get a total count {"rows":[{"key":null,"value":1573}]}
 * but be careful: this does not reflect the number of dvds, since some of them may have more than one media type!
 * Use
 * http://127.0.0.1:5984/dvdprofiler/_design/dvdprofiler/_view/media?group=true
 * to generate a sum for all types {"rows":[{"key":"BluRay","value":361},{"key":"DVD","value":1152},{"key":"HDDVD","value":60}]}
 */
function(doc) {
  if(doc.type == "dvdprofilerentry"){
	  for(var idx in doc.mediaType){
		  emit(doc.mediaType[idx] , 1);
	  }
  }
}