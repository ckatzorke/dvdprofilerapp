package org.dvdprofilerapp.xml.jaxp;

import junit.framework.Assert;

import org.dvdprofilerapp.xml.CollectionProcessor;
import org.dvdprofilerapp.xml.listener.DVDCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestXPathProcessor {

	@Autowired
	private CollectionProcessor xpathCollectionProcessor;

	@Autowired
	private DVDCounter dvdCounter;

	@Before
	public void before() {
		xpathCollectionProcessor.addDVDEventListener(dvdCounter);
	}

	@Test
	public void testProcessor() throws Exception {
		xpathCollectionProcessor.process();
		Assert.assertEquals(3, dvdCounter.getCounter());
	}
}
