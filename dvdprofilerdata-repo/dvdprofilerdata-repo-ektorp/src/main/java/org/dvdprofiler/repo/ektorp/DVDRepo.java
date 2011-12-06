package org.dvdprofiler.repo.ektorp;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class DVDRepo extends CouchDbRepositorySupport<CouchDVD> {

	protected DVDRepo(CouchDbConnector db) {
		super(CouchDVD.class, db);
	}

}
