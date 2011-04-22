package org.dvdprofilerapp.xml;

import javax.xml.stream.XMLStreamConstants;

/**
 * ParserEvent bean for help when parsing the collection. Holds information
 * about the last start or end element
 * 
 */
public class ParserEvent {

	/**
	 * see {@link XMLStreamConstants#START_ELEMENT},
	 * {@link XMLStreamConstants#END_ELEMENT}
	 */
	private int eventType;
	private String localName;

	public int getEventType() {
		return eventType;
	}

	/**
	 * only {@link XMLStreamConstants#START_ELEMENT} and {@link XMLStreamConstants#END_ELEMENT} is used
	 * @param eventType
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

}
