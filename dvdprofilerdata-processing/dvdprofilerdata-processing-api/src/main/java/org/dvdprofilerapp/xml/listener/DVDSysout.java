package org.dvdprofilerapp.xml.listener;

import org.dvdprofilerapp.xml.DVDEvent;
import org.dvdprofilerapp.xml.DVDEventListener;

public class DVDSysout implements DVDEventListener {

	@Override
	public void onEvent(DVDEvent event) {
		System.out.println(event.getDvd());
	}

}
