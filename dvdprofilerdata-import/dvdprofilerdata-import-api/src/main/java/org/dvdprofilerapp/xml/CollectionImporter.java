package org.dvdprofilerapp.xml;

/**
 * Importer Interface for processing collection.xml files.
 * 
 * @author Chris
 * 
 */
public interface CollectionImporter {

	public static final String COLLECTION_ELEMENT_TITLE = "Title";
	public static final String COLLECTION_ELEMENT_ID = "ID";
	public static final String COLLECTION_ELEMENT_COLLECTION = "Collection";
	public static final String COLLECTION_ELEMENT_DVD = "DVD";
	public static final String COLLECTION_ELEMENT_OVERVIEW = "Overview";
	public static final String COLLECTION_ELEMENT_MEDIA_TYPE = "MediaType";

	public abstract int getDvdCount();

	public abstract void importCollection() throws Exception;

}