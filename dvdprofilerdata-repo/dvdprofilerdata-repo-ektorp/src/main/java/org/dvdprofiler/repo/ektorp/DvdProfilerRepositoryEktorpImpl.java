package org.dvdprofiler.repo.ektorp;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
import org.springframework.beans.BeanUtils;

public class DvdProfilerRepositoryEktorpImpl implements DvdProfilerRepository {

	private DVDRepo repo;

	public void setRepo(DVDRepo repo) {
		this.repo = repo;
	}

	@Override
	public String writeDvd(DVD dvd) {
		CouchDVD couchDVD = new CouchDVD();
		BeanUtils.copyProperties(dvd, couchDVD);
		repo.add(couchDVD);
		return null;
	}

	@Override
	public DVD readDvd(String id) {
		CouchDVD dvd = repo.get(id);
		return dvd;
	}

}
