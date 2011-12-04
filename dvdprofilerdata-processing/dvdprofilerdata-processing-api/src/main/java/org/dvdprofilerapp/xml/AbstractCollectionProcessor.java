package org.dvdprofilerapp.xml;

import java.util.ArrayList;
import java.util.List;

import org.dvdprofilerapp.model.DVD;

public abstract class AbstractCollectionProcessor implements
		CollectionProcessor {
	private List<DVDEventListener> listeners = new ArrayList<DVDEventListener>();

	@Override
	public void addDVDEventListener(DVDEventListener listener) {
		listeners.add(listener);
	}

	protected void sendEvent(DVD dvd) {
		DVDEvent event = new DVDEvent(dvd);
		for (DVDEventListener listener : listeners) {
			listener.onEvent(event);
		}
	}

}
