package org.dvdprofilerapp.xml.listener;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.xml.DVDEvent;
import org.dvdprofilerapp.xml.DVDEventListener;

public class DVDPersister implements DVDEventListener {

	private DvdProfilerRepository dvdProfilerRepository;

	public void setDvdProfilerRepository(
			DvdProfilerRepository dvdProfilerRepository) {
		this.dvdProfilerRepository = dvdProfilerRepository;
	}

	@Override
	public void onEvent(DVDEvent event) {
		this.dvdProfilerRepository.writeDvd(event.getDvd());
	}
}
