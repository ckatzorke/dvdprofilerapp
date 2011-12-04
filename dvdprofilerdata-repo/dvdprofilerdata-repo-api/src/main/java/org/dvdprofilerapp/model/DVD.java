package org.dvdprofilerapp.model;

import java.util.Arrays;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class DVD {

	private String id;

	private String title;
	private String overview;
	private MediaType[] mediaType;
	private int collectionNumber;
	private String countryOfOrigin;
	private String[] genres;

	public String getId() {
		return id;
	}

	public void setId(String upn) {
		this.id = upn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String description) {
		this.overview = description;
	}

	public MediaType[] getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType[] mediaType) {
		this.mediaType = mediaType;
	}

	public int getCollectionNumber() {
		return collectionNumber;
	}

	public void setCollectionNumber(int collectionNumber) {
		this.collectionNumber = collectionNumber;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "DVD [id=" + id + ", title=" + title + ", overview=" + overview
				+ ", mediaType=" + Arrays.toString(mediaType)
				+ ", collectionNumber=" + collectionNumber
				+ ", countryOfOrigin=" + countryOfOrigin + ", genres="
				+ Arrays.toString(genres) + "]";
	}


}
