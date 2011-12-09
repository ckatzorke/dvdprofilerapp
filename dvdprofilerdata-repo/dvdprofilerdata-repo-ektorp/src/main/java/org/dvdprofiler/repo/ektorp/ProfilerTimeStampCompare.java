package org.dvdprofiler.repo.ektorp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ProfilerTimeStampCompare {

	private final String tsStored;
	private final String tsToUpdate;
	private final DateTime dtStored;
	private final DateTime dtToUpdate;
	private DateTimeFormatter dateParser = ISODateTimeFormat.dateTimeParser();

	public ProfilerTimeStampCompare(String tsStored, String tsToUpdate) {
		this.tsStored = tsStored;
		this.tsToUpdate = tsToUpdate;
		this.dtStored = dateParser.parseDateTime(tsStored);
		this.dtToUpdate = dateParser.parseDateTime(tsToUpdate);
	}

	public boolean isUpdated() {
		return dtToUpdate.isAfter(dtStored);
	}

}
