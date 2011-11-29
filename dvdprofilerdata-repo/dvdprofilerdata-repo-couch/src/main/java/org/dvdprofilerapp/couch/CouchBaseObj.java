package org.dvdprofilerapp.couch;

public class CouchBaseObj {
	private boolean ok;
	private String rev;
	private String id;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

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
