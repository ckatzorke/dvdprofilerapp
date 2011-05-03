package org.dvdprofilerapp.xml;

public interface CollectionParser {

	public abstract int getDvdCount();

	public abstract void parseCollection() throws Exception;

}