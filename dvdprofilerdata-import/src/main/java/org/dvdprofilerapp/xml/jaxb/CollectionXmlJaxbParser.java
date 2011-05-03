package org.dvdprofilerapp.xml.jaxb;

import java.util.List;

import javax.xml.bind.JAXBContext;

import org.dvdprofilerapp.xml.CollectionParser;
import org.dvdprofilerapp.xml.jaxb.generated.Collection;
import org.dvdprofilerapp.xml.jaxb.generated.DVD;
import org.springframework.core.io.Resource;

public class CollectionXmlJaxbParser implements CollectionParser {
	private Resource collectionXmlResource;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}

	private List<DVD> dvds;

	@Override
	public int getDvdCount() {
		return dvds != null ? dvds.size() : 0;
	}

	@Override
	public void parseCollection() throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance("org.dvdprofilerapp.xml.jaxb.generated");
		Collection collection = (Collection) jaxbContext.createUnmarshaller()
				.unmarshal(collectionXmlResource.getInputStream());
		dvds = collection.getDVD();
	}
}
