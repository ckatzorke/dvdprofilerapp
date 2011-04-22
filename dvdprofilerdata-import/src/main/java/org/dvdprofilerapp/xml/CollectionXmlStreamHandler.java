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
		ParserEvent parserEvent = new ParserEvent();
		while (staxReader.hasNext()) {
			int eventType = staxReader.next();
			// TODO anstaendiger check ob es das richtige DVD element ist
			if (eventType == XMLEvent.START_ELEMENT
					|| eventType == XMLEvent.END_ELEMENT) {
				String localName = staxReader.getLocalName();
				if (eventType == XMLEvent.START_ELEMENT
						&& "DVD".equals(localName)
						&& (("Collection".equals(parserEvent.getLocalName()) || "DVD"
								.equals(parserEvent.getLocalName())))) {
					dvdCount++;
				}
				parserEvent.setLocalName(localName);
				parserEvent.setEventType(eventType);
			}
		}
		staxReader.close();
	}
}
