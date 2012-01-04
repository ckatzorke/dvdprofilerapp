package org.dvdprofilerapp.xml;

public interface CollectionProcessor {
	/** Handled elements */
	public static final String COLLECTION_ELEMENT_TITLE = "Title";
	public static final String COLLECTION_ELEMENT_ID = "ID";
	public static final String COLLECTION_ELEMENT_UPC = "UPC";
	public static final String COLLECTION_ELEMENT_PROFILE_TIMESTAMP = "ProfileTimestamp";
	public static final String COLLECTION_ELEMENT_COLLECTION = "Collection";
	public static final String COLLECTION_ELEMENT_DVD = "DVD";
	public static final String COLLECTION_ELEMENT_OVERVIEW = "Overview";
	public static final String COLLECTION_ELEMENT_MEDIA_TYPES = "MediaTypes";
	public static final String COLLECTION_ELEMENT_COLLECTIONNUMBER = "CollectionNumber";
	public static final String COLLECTION_ELEMENT_COUNTRYOFORIGIN = "CountryOfOrigin";
	public static final String COLLECTION_ELEMENT_GENRES = "Genres";
	public static final String COLLECTION_ELEMENT_BOXSET = "BoxSer";
	public static final String COLLECTION_ELEMENT_PARENT = "Parent";

	/** helper */
	public static final String COLLECTION_ELEMENT_MEDIA_TYPE = "MediaType";
	public static final String COLLECTION_ELEMENT_LOCKS = "Locks";

	public void process();

	public void addDVDEventListener(DVDEventListener listener);

}
