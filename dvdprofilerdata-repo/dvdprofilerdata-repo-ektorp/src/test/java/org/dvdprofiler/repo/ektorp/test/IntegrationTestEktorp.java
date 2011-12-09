package org.dvdprofiler.repo.ektorp.test;

import junit.framework.Assert;

import org.dvdprofiler.repo.ektorp.CouchDVD;
import org.dvdprofiler.repo.ektorp.DvdProfilerRepositoryEktorpImpl;
import org.dvdprofilerapp.model.DVD;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-context.xml")
public class IntegrationTestEktorp {
	@Autowired
	private DvdProfilerRepositoryEktorpImpl dvdRepo;

	public void setDvdRepo(DvdProfilerRepositoryEktorpImpl dvdRepo) {
		this.dvdRepo = dvdRepo;
	}

	@Test
	public void testUp() throws Exception {
		Assert.assertNotNull(dvdRepo);
	}

	@Test
	public void testWrite() throws Exception {
		DVD dvd = new DVD();
		dvd.setId("011891980449");
		dvd.setTitle("test");
		dvd.setProfileTimeStamp("2011-02-02");
		dvdRepo.writeDvd(dvd);
		DVD readDvd = dvdRepo.readDvd("011891980449");
		Assert.assertEquals("test", readDvd.getTitle());
	}
}
