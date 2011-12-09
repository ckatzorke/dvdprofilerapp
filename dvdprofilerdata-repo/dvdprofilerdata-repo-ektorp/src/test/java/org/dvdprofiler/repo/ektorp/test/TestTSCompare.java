package org.dvdprofiler.repo.ektorp.test;

import junit.framework.Assert;

import org.dvdprofiler.repo.ektorp.ProfilerTimeStampCompare;
import org.junit.Test;

public class TestTSCompare {

	@Test
	public void testCompareNoUpdate() throws Exception {
		String stored = "2008-02-29T15:47:00.000Z";
		String updated = "2008-02-29T15:47:00.000Z";
		ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(stored,
				updated);
		Assert.assertTrue(!compare.isUpdated());
	}
	
	@Test
	public void testCompareUpdate() throws Exception {
		String stored = "2008-02-29T15:47:00.000Z";
		String updated = "2008-03-29T15:47:00.000Z";
		ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(stored,
				updated);
		Assert.assertTrue(compare.isUpdated());
	}
	@Test
	public void testCompareUpdateWithDiffString() throws Exception {
		String stored = "2008-02-29T15:47:00.000Z";
		String updated = "2008-03-20";
		ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(stored,
				updated);
		Assert.assertTrue(compare.isUpdated());
	}
	
	@Test
	public void testCompareNoUpdateWithDiffString() throws Exception {
		String stored = "2008-02-29T15:47:00.000Z";
		String updated = "2008-02-29";
		ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(stored,
				updated);
		Assert.assertTrue(!compare.isUpdated());
	}
	
	@Test
	public void testCompareNoUpdateWithSameString() throws Exception {
		String stored = "2008-02-29";
		String updated = "2008-02-29";
		ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(stored,
				updated);
		Assert.assertTrue(!compare.isUpdated());
	}

}
