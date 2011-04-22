package org.dvdprofilerapp.xml;

import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.springframework.core.io.Resource;

public class CollectionXmlStreamHandler {

	private Resource collectionXmlResource;

	private int dvdCount = 0;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}
	
	public int getDvdCount() {
		return dvdCount;
	}

	public void readCollection() throws XMLStreamException, IOException {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader staxReader = f
				.createXMLStreamReader(collectionXmlResource.getInputStream());
		String previousLocalName = null;
		while (staxReader.hasNext()) {
			int eventType = staxReader.next();
			//TODO anstaendiger check ob es das richtige DVD element ist
			if (eventType == XMLEvent.START_ELEMENT
					&& "DVD".equals(staxReader.getLocalName())
					&& ("Collection".equals(previousLocalName) || "DVD"
							.equals(previousLocalName))) {
				dvdCount++;
			}
			previousLocalName = staxReader.getLocalName();
		}
		staxReader.close();
	}
}
