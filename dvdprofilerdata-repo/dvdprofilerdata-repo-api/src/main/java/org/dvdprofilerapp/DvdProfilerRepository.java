package org.dvdprofilerapp;

import org.dvdprofilerapp.model.DVD;

public interface DvdProfilerRepository {

	public String writeDvd(DVD dvd);

	public DVD readDvd(String id);
}
