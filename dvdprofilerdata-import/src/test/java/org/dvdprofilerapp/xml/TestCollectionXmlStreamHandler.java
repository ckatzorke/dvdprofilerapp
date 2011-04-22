package org.dvdprofilerapp.xml;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestCollectionXmlStreamHandler {
	@Autowired
	private CollectionXmlStreamHandler collectionXmlStreamHandler;

	@Test
	public void testIrgendwas() throws Exception {
		collectionXmlStreamHandler.readCollection();
		Assert.assertEquals(3, collectionXmlStreamHandler.getDvdCount());
	}

}
