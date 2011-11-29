package org.dvdprofilerapp.dvdprofilerimport;

import org.dvdprofilerapp.xml.CollectionImporter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Import {

	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		CollectionImporter collectionImporter = appCtx
				.getBean(CollectionImporter.class);
		long now = System.currentTimeMillis();
		try {
			System.out.println("**************************************");
			System.out.println("* Starting import of collection.xml....");
			collectionImporter.importCollection();
			long then = System.currentTimeMillis();
			System.out.println("* Done. " + collectionImporter.getDvdCount()
					+ " elements handled and uploaded to couch [" + (then - now) + "ms].");
			System.out.println("**************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
