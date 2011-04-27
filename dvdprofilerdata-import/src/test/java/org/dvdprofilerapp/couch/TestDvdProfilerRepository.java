package org.dvdprofilerapp.couch;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestDvdProfilerRepository {
	@Autowired
	private DvdProfilerRepository dvdProfilerRepository;

	private static RestTemplate restTemplate = new RestTemplate();

	@BeforeClass
	public static void prepareTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		restTemplate.put("http://localhost:5984/test-dvdprofiler", entity);
	}

	@AfterClass
	public static void afterTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		restTemplate.delete("http://localhost:5984/test-dvdprofiler", entity);
	}

	@Test
	public void testSimpleWrite() throws Exception {
		String dvd = dvdProfilerRepository.writeDvd("test");
		Assert.assertNotNull(dvd);
		Object object = dvdProfilerRepository.readDvd(dvd);
		Assert.assertNotNull(object);

	}
}
