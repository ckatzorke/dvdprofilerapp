package org.dvdprofilerapp.xml.jaxb;

import junit.framework.Assert;

import org.dvdprofilerapp.xml.CollectionParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestCollectionXmlJaxbHandler {
	@Autowired
	private CollectionParser collectionXmlJaxbHandler;

	@Test
	public void testDvDCount() throws Exception {
		collectionXmlJaxbHandler.parseCollection();
		Assert.assertEquals(3, collectionXmlJaxbHandler.getDvdCount());
	}

}
