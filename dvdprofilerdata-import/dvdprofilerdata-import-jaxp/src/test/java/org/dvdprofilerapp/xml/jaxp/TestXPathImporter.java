package org.dvdprofilerapp.xml.jaxp;

import junit.framework.Assert;

import org.dvdprofilerapp.xml.CollectionImporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class TestXPathImporter {

	@Autowired
	private CollectionImporter xpathCollectionImporter;

	
	@Test
	public void testImporter() throws Exception {
		xpathCollectionImporter.importCollection();
		Assert.assertEquals(3, xpathCollectionImporter.getDvdCount());
	}
}
