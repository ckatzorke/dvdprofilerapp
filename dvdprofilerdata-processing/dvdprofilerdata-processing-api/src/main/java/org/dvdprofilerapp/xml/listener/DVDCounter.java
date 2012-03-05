package org.dvdprofilerapp.xml.listener;

import org.dvdprofilerapp.xml.DVDEvent;
import org.dvdprofilerapp.xml.DVDEventListener;

public class DVDCounter implements DVDEventListener {

	int counter = 0;

	@Override
	public void onEvent(DVDEvent event) {
		counter++;
		System.out.println("#" + counter);
	}

	public int getCounter() {
		return counter;
	}

}
