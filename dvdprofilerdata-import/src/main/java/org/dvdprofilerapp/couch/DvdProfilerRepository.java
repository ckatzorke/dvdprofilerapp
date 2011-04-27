package org.dvdprofilerapp.couch;

import org.springframework.web.client.RestTemplate;

public class DvdProfilerRepository {

	private RestTemplate restTemplate;

	private String dvdprofilerDbBaseUrl;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setDvdprofilerDbBaseUrl(String dvdprofilerBaseUrl) {
		this.dvdprofilerDbBaseUrl = dvdprofilerBaseUrl;
	}

	/**
	 * post the object to couch
	 * 
	 * @param s
	 * @return the id of the couchobj
	 */
	public String writeDvd(String s) {
		CouchBaseObj couchBaseObj = restTemplate.postForObject(
				dvdprofilerDbBaseUrl, s, CouchBaseObj.class);
		return couchBaseObj.getId();
	}

	/**
	 * reads a dvd
	 * 
	 * @param id
	 * @return
	 */
	public Object readDvd(String id) {
		Object o = restTemplate.getForObject(dvdprofilerDbBaseUrl + "/{id}",
				Object.class, id);
		return o;
	}
}
