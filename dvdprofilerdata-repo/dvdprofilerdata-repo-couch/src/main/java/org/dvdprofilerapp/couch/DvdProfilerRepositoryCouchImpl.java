package org.dvdprofilerapp.couch;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class DvdProfilerRepositoryCouchImpl implements DvdProfilerRepository {

	private RestTemplate restTemplate;

	private String dvdprofilerDbBaseUrl;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setDvdprofilerDbBaseUrl(String dvdprofilerBaseUrl) {
		this.dvdprofilerDbBaseUrl = dvdprofilerBaseUrl;
	}

	private void init() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		restTemplate.put(this.dvdprofilerDbBaseUrl, entity);
	}

	/**
	 * post the object to couch
	 * 
	 * @return the id
	 */
	public String writeDvd(DVD dvd) {
		CouchBaseObj postForObject = restTemplate.postForObject(
				dvdprofilerDbBaseUrl, dvd, CouchBaseObj.class);
		if (postForObject.isOk())
			return postForObject.getId();
		else
			return null;

	}

	/**
	 * reads a dvd
	 * 
	 * @param id
	 * @return
	 */
	public DVD readDvd(String id) {
		DVD o = restTemplate.getForObject(dvdprofilerDbBaseUrl + "/{id}",
				DVD.class, id);
		return o;
	}
}
