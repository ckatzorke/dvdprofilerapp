package org.dvdprofiler.repo.ektorp;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
import org.ektorp.AttachmentInputStream;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.FileSystemResource;

public class DvdProfilerRepositoryEktorpImpl implements DvdProfilerRepository {

	private Logger logger = Logger
			.getLogger(DvdProfilerRepositoryEktorpImpl.class.getName());

	private static final String IMAGES_THUMNBNAILS_DIR = "/Images/Thumbnails/";
	private static final String IMAGES_DIR = "/Images/";

	private DVDRepo repo;
	private String dvdprofilerDBDirectory;
	private Boolean attach;

	private enum ThumbnailType {
		f, b
	}

	private enum CoverType {
		f, b
	}

	public void setDvdprofilerDBDirectory(String dvdprofilerDBDirectory) {
		this.dvdprofilerDBDirectory = dvdprofilerDBDirectory;
	}

	public void setRepo(DVDRepo repo) {
		this.repo = repo;
	}

	@Override
	public String writeDvd(DVD dvd) {
		CouchDVD couchDVD = null;
		boolean isnew = true;
		try {
			couchDVD = repo.get(dvd.getId());
			isnew = false;
		} catch (DocumentNotFoundException e) {
			couchDVD = new CouchDVD();
		}
		boolean update = true;
		if (couchDVD.getProfileTimeStamp() != null) {
			// compare profilertimestamps
			ProfilerTimeStampCompare compare = new ProfilerTimeStampCompare(
					couchDVD.getProfileTimeStamp(), dvd.getProfileTimeStamp());
			if (compare.isUpdated()) {
				logger.info("DVD " + dvd.getTitle() + " (" + dvd.getId()
						+ ") updated.");
				update = true;
			} else {
				logger.info("DVD " + dvd.getTitle() + " (" + dvd.getId()
						+ ") not updated. Skipping...");
				update = false;
			}
		} else {
			logger.fine("Creating dvd entity " + dvd.getTitle() + " ("
					+ dvd.getId() + ")");
		}
		if (update) {
			BeanUtils.copyProperties(dvd, couchDVD);
			if (isnew)
				repo.add(couchDVD);
			else
				repo.update(couchDVD);
			if (attachEnabled()) {
				attachThumbnail(couchDVD, ThumbnailType.f);
				attachThumbnail(couchDVD, ThumbnailType.b);
//				attachCover(couchDVD, CoverType.f);
//				attachCover(couchDVD, CoverType.b);
			}
		}
		return null;
	}

	public boolean attachEnabled() {
		if (attach == null) {
			if (dvdprofilerDBDirectory == null) {
				logger.warning("DB Directory is NOT set. Attachment of images and thumbnails are disabled.");
				attach = Boolean.FALSE;
			} else {
				File dbDir = new File(dvdprofilerDBDirectory);
				File tnDir = new File(dvdprofilerDBDirectory
						+ IMAGES_THUMNBNAILS_DIR);
				if (dbDir.exists()) {
					logger.info(dvdprofilerDBDirectory
							+ " is correct. Checking thumbnails...");
					if (tnDir.exists()) {
						logger.info(dvdprofilerDBDirectory
								+ IMAGES_THUMNBNAILS_DIR
								+ " is correct. Trying to attach images and thumbnails.");
						attach = Boolean.TRUE;
					} else {
						logger.warning(dvdprofilerDBDirectory
								+ IMAGES_THUMNBNAILS_DIR
								+ " does NOT exist. Attachment of images and thumbnails are disabled.");
						attach = Boolean.FALSE;
					}
				} else {
					logger.warning(dvdprofilerDBDirectory
							+ " does NOT exist. Attachment of images and thumbnails are disabled.");
					attach = Boolean.FALSE;
				}
			}
		}
		return attach.booleanValue();
	}

	private void attachThumbnail(CouchDVD couchDVD, ThumbnailType type) {
		String filename = dvdprofilerDBDirectory + IMAGES_THUMNBNAILS_DIR
				+ couchDVD.getId() + type + ".jpg";
		try {
			FileSystemResource thumbnail = new FileSystemResource(filename);
			InputStream inputStream = thumbnail.getInputStream();
			AttachmentInputStream a = new AttachmentInputStream("thumbnail_"
					+ type, inputStream, "image/jpeg");
			couchDVD = repo.get(couchDVD.getId());
			repo.getDB().createAttachment(couchDVD.getId(),
					couchDVD.getRevision(), a);
		} catch (Exception e) {
			logger.severe("Cannot attach thumbnail_" + type + " [" + filename
					+ "]: " + e.getMessage());
		}
	}

	private void attachCover(CouchDVD couchDVD, CoverType type) {
		String filename = dvdprofilerDBDirectory + IMAGES_DIR
				+ couchDVD.getId() + type + ".jpg";
		try {
			FileSystemResource thumbnail = new FileSystemResource(filename);
			InputStream inputStream = thumbnail.getInputStream();
			AttachmentInputStream a = new AttachmentInputStream(
					"cover_" + type, inputStream, "image/jpeg");
			couchDVD = repo.get(couchDVD.getId());
			repo.getDB().createAttachment(couchDVD.getId(),
					couchDVD.getRevision(), a);
		} catch (Exception e) {
			logger.severe("Cannot attach cover_" + type + " [" + filename
					+ "]: " + e.getMessage());
		}
	}

	@Override
	public DVD readDvd(String id) {
		CouchDVD dvd = repo.get(id);
		return dvd;
	}

}
