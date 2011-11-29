package org.dvdprofilerapp.model;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class DVD {

	private String id;

	private String title;
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "DVD [id=" + id + ", title=" + title + ", description="
				+ description + "]";
	}

}
