package org.dvdprofiler.repo.ektorp.test;

import junit.framework.Assert;

import org.dvdprofiler.repo.ektorp.DvdProfilerRepositoryEktorpImpl;
import org.junit.Before;
import org.junit.Test;

public class TestCheckAttachmentEnabled {

	DvdProfilerRepositoryEktorpImpl repo;

	@Before
	public void setUp() {
		repo = new DvdProfilerRepositoryEktorpImpl();
	}

	@Test
	public void testAttachDbDirNotSet() throws Exception {
		Assert.assertTrue(!repo.attachEnabled());
	}

	@Test
	public void testAttachBaseDirExist() throws Exception {
		repo.setDvdprofilerDBDirectory(System.getProperty("java.io.tmpdir"));
		Assert.assertTrue(!repo.attachEnabled());
	}

}
