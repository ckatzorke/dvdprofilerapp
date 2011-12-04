package org.dvdprofilerapp.couch;

import junit.framework.Assert;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
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
public class IntegrationTestDvdProfilerRepository {
	@Autowired
	private DvdProfilerRepository dvdProfilerRepository;

	private static RestTemplate restTemplate = new RestTemplate();


	@AfterClass
	public static void afterTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		restTemplate.delete("http://localhost:5984/test-dvdprofiler", entity);
	}

	@Test
	public void testSimpleWrite() throws Exception {
		DVD d = new DVD();
		d.setId("12345");
		d.setTitle("Harry");
		d.setOverview("So was halt");
		String id = dvdProfilerRepository.writeDvd(d);
		DVD d2 = dvdProfilerRepository.readDvd(id);
		Assert.assertNotNull(d2);

	}
}
