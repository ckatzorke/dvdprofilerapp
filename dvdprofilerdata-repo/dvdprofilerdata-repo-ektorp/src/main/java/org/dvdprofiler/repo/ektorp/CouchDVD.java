package org.dvdprofiler.repo.ektorp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.dvdprofilerapp.model.DVD;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouchDVD extends DVD {
	@JsonProperty(value = "_rev")
	private String revision;

	@Override
	@JsonProperty(value = "_id")
	public String getId() {
		return super.getId();
	}

	@Override
	@JsonProperty(value = "_id")
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
