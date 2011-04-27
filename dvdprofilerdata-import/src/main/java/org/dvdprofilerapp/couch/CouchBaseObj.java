package org.dvdprofilerapp.couch;

public class CouchBaseObj {
	
	private String rev;
	private String id;

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CouchBaseObj [rev=" + rev + ", id=" + id + "]";
	}
	
	

}
