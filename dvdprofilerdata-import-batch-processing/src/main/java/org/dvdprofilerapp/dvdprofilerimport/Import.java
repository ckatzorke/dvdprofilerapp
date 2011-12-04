package org.dvdprofilerapp.dvdprofilerimport;

import org.dvdprofilerapp.xml.CollectionProcessor;
import org.dvdprofilerapp.xml.DVDEventListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Import {

	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		CollectionProcessor collectionProcessor = appCtx
				.getBean(CollectionProcessor.class);
		DVDEventListener persister = appCtx.getBean(DVDEventListener.class);
		collectionProcessor.addDVDEventListener(persister);
		long now = System.currentTimeMillis();
		try {
			System.out.println("**************************************");
			System.out.println("* Starting import of collection.xml....");
			collectionProcessor.process();
			long then = System.currentTimeMillis();
			System.out.println("* Done. [" + (then - now) + "ms].");
			System.out.println("**************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
