package org.dvdprofilerapp.xml.stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
import org.dvdprofilerapp.xml.CollectionImporter;
import org.springframework.core.io.Resource;

public class CollectionXmlStreamHandler implements CollectionImporter {

	private Resource collectionXmlResource;
	private DvdProfilerRepository dvdProfilerRepository;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}

	public void setDvdProfilerRepository(
			DvdProfilerRepository dvdProfilerRepository) {
		this.dvdProfilerRepository = dvdProfilerRepository;
	}

	private int dvdCount = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dvdprofilerapp.xml.CollectionParser#getDvdCount()
	 */
	@Override
	public int getDvdCount() {
		return dvdCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dvdprofilerapp.xml.CollectionParser#parseCollection()
	 */
	@Override
	public void importCollection() throws Exception {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader staxReader = f
				.createXMLStreamReader(collectionXmlResource.getInputStream());
		ParserContext context = new ParserContext();
		while (staxReader.hasNext()) {
			int eventType = staxReader.next();
			context.setCurrentEventType(eventType);
			// process when DVD start element, and previous is DVD (end) or
			// Collection (start), because DVD element occurs more often
			if (eventType == XMLEvent.START_ELEMENT) {
				context.setCurrentElement(staxReader.getLocalName());
				if (CollectionImporter.COLLECTION_ELEMENT_DVD.equals(context
						.getCurrentElement())
						&& (CollectionImporter.COLLECTION_ELEMENT_DVD
								.equals(context.getPreviousElement()) || CollectionImporter.COLLECTION_ELEMENT_COLLECTION
								.equals(context.getPreviousElement()))) {
					dvdCount++;
					DVD dvd = new DVD();
					// process all child elements until end_element DVD is
					// reached
					while (staxReader.hasNext()) {
						eventType = staxReader.next();
						context.setCurrentEventType(eventType);
						if (eventType == XMLEvent.START_ELEMENT) {
							context.setCurrentElement(staxReader.getLocalName());
							if (CollectionImporter.COLLECTION_ELEMENT_ID
									.equals(context.getCurrentElement())) {
								eventType = staxReader.next();
								if (eventType == XMLEvent.CHARACTERS) {
									if (staxReader.getText() != null
											&& !"false".equals(staxReader
													.getText())) {
										dvd.setId(staxReader.getText());
									}
								}
							}
							if (CollectionImporter.COLLECTION_ELEMENT_TITLE
									.equals(context.getCurrentElement())) {
								eventType = staxReader.next();
								if (eventType == XMLEvent.CHARACTERS) {
									if (staxReader.getText() != null
											&& !"false".equals(staxReader
													.getText())) {
										dvd.setTitle(staxReader.getText());
									}
								}
							}
							//kake additional check that previous element is not media type, overview exists twice....
							if (CollectionImporter.COLLECTION_ELEMENT_OVERVIEW
									.equals(context.getCurrentElement())
									&& !CollectionImporter.COLLECTION_ELEMENT_MEDIA_TYPE
											.equals(context
													.getPreviousElement())) {
								StringBuffer overview = new StringBuffer();
								while ((eventType = staxReader.next()) == XMLEvent.CHARACTERS) {
									if (staxReader.getText() != null
											&& !"false".equals(staxReader
													.getText())) {
										overview.append(staxReader.getText());
									}
								}
								dvd.setDescription(overview.toString());
							}
						}
						if (eventType == XMLEvent.END_ELEMENT) {
							context.setCurrentElement(staxReader.getLocalName());
							if (context.getCurrentElement().equals(
									CollectionImporter.COLLECTION_ELEMENT_DVD)
									&& !context
											.getPreviousElement()
											.equals(CollectionImporter.COLLECTION_ELEMENT_DVD)) {
//								System.out
//										.println("persisting " + dvd + " ...");
								dvdProfilerRepository.writeDvd(dvd);
								dvd = null;
								break;
							}
						}
					}
				}
			} else if (eventType == XMLEvent.END_ELEMENT) {
				context.setCurrentElement(staxReader.getLocalName());
			}
		}
		staxReader.close();
	}
}
