package org.dvdprofiler.repo.ektorp;

import org.codehaus.jackson.annotate.JsonProperty;
import org.dvdprofilerapp.model.DVD;

public class CouchDVD extends DVD {
	@JsonProperty(value = "_rev")
	private String revision;
	@JsonProperty(value = "_id")
	private String id;

	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public void setId(String id) {
		super.setId(id);
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRevision() {
		return revision;
	}
}
