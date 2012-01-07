package org.dvdprofilerapp.xml.stax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.dvdprofilerapp.model.DVD;
import org.dvdprofilerapp.model.MediaType;
import org.dvdprofilerapp.xml.AbstractCollectionProcessor;
import org.dvdprofilerapp.xml.CollectionProcessor;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class CollectionXmlStreamProcessor extends AbstractCollectionProcessor {
	private Logger logger = Logger.getLogger(CollectionXmlStreamProcessor.class
			.getName());
	private Resource collectionXmlResource;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}

	@Override
	public void process() {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader staxReader = null;
		try {
			staxReader = f.createXMLStreamReader(collectionXmlResource
					.getInputStream());

			ParserContext context = new ParserContext();
			while (staxReader.hasNext()) {
				int eventType = staxReader.next();
				context.setCurrentEventType(eventType);
				// process when DVD start element, and previous is DVD (end) or
				// Collection (start), because DVD element occurs more often
				if (eventType == XMLEvent.START_ELEMENT) {
					context.setCurrentElement(staxReader.getLocalName());
					if (CollectionProcessor.COLLECTION_ELEMENT_DVD
							.equals(context.getCurrentElement())
							&& (CollectionProcessor.COLLECTION_ELEMENT_DVD
									.equals(context.getPreviousElement()) || CollectionProcessor.COLLECTION_ELEMENT_COLLECTION
									.equals(context.getPreviousElement()))) {
						DVD dvd = new DVD();
						// process all child elements until end_element DVD is
						// reached
						while (staxReader.hasNext()) {
							eventType = staxReader.next();
							context.setCurrentEventType(eventType);
							if (eventType == XMLEvent.START_ELEMENT) {
								context.setCurrentElement(staxReader
										.getLocalName());
								if (CollectionProcessor.COLLECTION_ELEMENT_ID
										.equals(context.getCurrentElement())) {
									dvd.setId(getElementTextValue(staxReader));
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_PROFILE_TIMESTAMP
										.equals(context.getCurrentElement())) {
									dvd.setProfileTimeStamp(getElementTextValue(staxReader));
								}
								// UPC
								if (CollectionProcessor.COLLECTION_ELEMENT_UPC
										.equals(context.getCurrentElement())) {
									dvd.setUpc(getElementTextValue(staxReader));
								}
								// ParentUPC
								if (CollectionProcessor.COLLECTION_ELEMENT_PARENT
										.equals(context.getCurrentElement())) {
									String parentUpc = getElementTextValue(staxReader);
									if (StringUtils.hasText(parentUpc)) {
										dvd.setParentUpc(parentUpc);
									}
								}
								// PurchaseDate
								if (CollectionProcessor.COLLECTION_ELEMENT_PURCHASE_DATE
										.equals(context.getCurrentElement())) {
									String purchaseDate = getElementTextValue(staxReader);
									if (StringUtils.hasText(purchaseDate)) {
										dvd.setPurchaseDate(purchaseDate);
									}
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_TITLE
										.equals(context.getCurrentElement())) {
									dvd.setTitle(getElementTextValue(staxReader));
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_COLLECTIONNUMBER
										.equals(context.getCurrentElement())) {
									try {
										dvd.setCollectionNumber(Integer
												.parseInt(getElementTextValue(staxReader)));
									} catch (NumberFormatException e) {
//										logger.warning("Error parsing as int@"
//												+ staxReader.getLocation());
									}
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_COUNTRYOFORIGIN
										.equals(context.getCurrentElement())) {
									dvd.setCountryOfOrigin(getElementTextValue(staxReader));
								}
								// make additional check that previous element
								// is
								// not media type, overview exists twice....
								if (CollectionProcessor.COLLECTION_ELEMENT_OVERVIEW
										.equals(context.getCurrentElement())
										&& !CollectionProcessor.COLLECTION_ELEMENT_MEDIA_TYPE
												.equals(context
														.getPreviousElement())) {

									dvd.setOverview(getElementTextValue(staxReader));
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_MEDIA_TYPES
										.equals(staxReader.getLocalName())) {
									List<MediaType> mediatypes = new ArrayList<MediaType>();
									while (true) {
										eventType = staxReader.next();
										if (eventType == XMLEvent.START_ELEMENT) {
											if (getElementTextValue(staxReader)
													.equals("true")) {
												mediatypes
														.add(MediaType.get(staxReader
																.getLocalName()));
											}

										}
										if (eventType == XMLEvent.END_ELEMENT
												&& CollectionProcessor.COLLECTION_ELEMENT_MEDIA_TYPES
														.equals(staxReader
																.getLocalName())) {
											dvd.setMediaType(mediatypes
													.toArray(new MediaType[mediatypes
															.size()]));
											break;
										}
									}
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_GENRES
										.equals(staxReader.getLocalName())) {
									List<String> genres = new ArrayList<String>();
									while (true) {
										eventType = staxReader.next();
										if (eventType == XMLEvent.START_ELEMENT) {
											genres.add(getElementTextValue(staxReader));
										}
										if (eventType == XMLEvent.END_ELEMENT
												&& CollectionProcessor.COLLECTION_ELEMENT_GENRES
														.equals(staxReader
																.getLocalName())) {
											dvd.setGenres(genres
													.toArray(new String[genres
															.size()]));
											break;
										}
									}
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_LOCKS
										.equals(context.getCurrentElement())) {
									// skip all children
									while (true) {
										eventType = staxReader.next();
										if (eventType == XMLEvent.END_ELEMENT
												&& CollectionProcessor.COLLECTION_ELEMENT_LOCKS
														.equals(staxReader
																.getLocalName())) {
											break;
										}
									}
								}
							}
							if (eventType == XMLEvent.END_ELEMENT) {
								context.setCurrentElement(staxReader
										.getLocalName());
								if (context
										.getCurrentElement()
										.equals(CollectionProcessor.COLLECTION_ELEMENT_DVD)
										&& !context
												.getPreviousElement()
												.equals(CollectionProcessor.COLLECTION_ELEMENT_DVD)) {
									sendEvent(dvd);
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
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException("stream execption!", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Collection.xml must be present!", e);
		}
	}

	private String getElementTextValue(XMLStreamReader staxReader)
			throws XMLStreamException {
		@SuppressWarnings("unused")
		int eventType = 0;
		StringBuffer buff = new StringBuffer();
		while ((eventType = staxReader.next()) == XMLEvent.CHARACTERS) {
			if (staxReader.getText() != null) {
				buff.append(staxReader.getText());
			}
		}
		return buff.toString();
	}
}
