package org.dvdprofilerapp.xml.stax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private Map<String, DVD> dvds = new HashMap<String, DVD>();
	private Map<String, List<DVD>> missingParents = new HashMap<String, List<DVD>>();

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
							.equals(context.getCurrentElement())) {
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
									String title = getElementTextValue(staxReader);
									dvd.setTitle(title);
								}
								if (CollectionProcessor.COLLECTION_ELEMENT_COLLECTIONNUMBER
										.equals(context.getCurrentElement())) {
									try {
										dvd.setCollectionNumber(Integer
												.parseInt(getElementTextValue(staxReader)));
									} catch (NumberFormatException e) {
										// logger.warning("Error parsing as int@"
										// + staxReader.getLocation());
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
									String overview = getElementTextValue(
											staxReader).trim();
									overview = overview.replaceAll("\\n",
											"<br>");
									dvd.setOverview(overview);
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
										.equals(CollectionProcessor.COLLECTION_ELEMENT_DVD)) {
									if (dvd.getParentUpc() != null) {
										// get parent DVD entry
										DVD parent = dvds.get(dvd
												.getParentUpc());
										if (parent == null) {
											// no parent yet, store in missing
											// parents
											List<DVD> childs = missingParents
													.get(parent);
											if (childs == null) {
												childs = new ArrayList<DVD>();
											}
											childs.add(dvd);
											missingParents.put(
													dvd.getParentUpc(), childs);
										} else {
											// otherwise, add to childprofiles
											DVD[] childProfiles = parent
													.getChildProfiles();
											if (childProfiles == null) {
												childProfiles = new DVD[] { dvd };
											} else {
												childProfiles = Arrays
														.copyOf(childProfiles,
																childProfiles.length + 1);
												childProfiles[childProfiles.length - 1] = dvd;
											}
											parent.setChildProfiles(childProfiles);
										}
									} else {
										// chick if it is a boxset and there are
										// childs in missingchild map
										List<DVD> childs = missingParents
												.remove(dvd.getId());
										if (childs != null) {
											dvd.setChildProfiles(childs
													.toArray(new DVD[] {}));
										}
										dvds.put(dvd.getId(), dvd);
									}
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
			// now cleanup missing parents
			if (!missingParents.isEmpty()) {
				for (String parentId : missingParents.keySet()) {
					List<DVD> childs = missingParents.get(parentId);
					if (childs != null) {
						DVD dvd = dvds.get(parentId);
						if (dvd != null) {
							dvd.setChildProfiles(childs.toArray(new DVD[] {}));
						}
					}
				}
			}
			missingParents = null;
			for (DVD dvd : dvds.values()) {
				sendEvent(dvd);
			}
			dvds = null;
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
