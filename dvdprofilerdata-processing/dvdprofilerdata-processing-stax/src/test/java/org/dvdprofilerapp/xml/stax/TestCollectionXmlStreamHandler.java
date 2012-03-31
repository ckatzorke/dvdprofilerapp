package org.dvdprofilerapp.xml.stax;

import junit.framework.Assert;

import org.dvdprofilerapp.xml.CollectionProcessor;
import org.dvdprofilerapp.xml.listener.DVDCounter;
import org.dvdprofilerapp.xml.listener.DVDSysout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestCollectionXmlStreamHandler {
	@Autowired
	private CollectionProcessor collectionXmlStreamHandler;

	@Test
	public void testDvDCount() throws Exception {
		DVDCounter dvdCounter = new DVDCounter();
		DVDSysout dvdSysout = new DVDSysout();
		collectionXmlStreamHandler.addDVDEventListener(dvdCounter);
		collectionXmlStreamHandler.addDVDEventListener(dvdSysout);
		collectionXmlStreamHandler.process();
		Assert.assertEquals(2, dvdCounter.getCounter());
	}

}
