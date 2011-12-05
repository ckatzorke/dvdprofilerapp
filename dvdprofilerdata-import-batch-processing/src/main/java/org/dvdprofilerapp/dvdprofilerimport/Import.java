package org.dvdprofilerapp.dvdprofilerimport;

import org.codehaus.jackson.map.ObjectMapper;
import org.dvdprofilerapp.model.DVD;
import org.dvdprofilerapp.model.mixin.DVDMixin;
import org.dvdprofilerapp.xml.CollectionProcessor;
import org.dvdprofilerapp.xml.DVDEventListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Import implements InitializingBean {

	private ObjectMapper objectMapper;

	public void setObjectMapper(ObjectMapper mapper) {
		this.objectMapper = mapper;
	}

	public void doImport() {
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

	@Override
	public void afterPropertiesSet() throws Exception {
		this.objectMapper.getSerializationConfig().addMixInAnnotations(
				DVD.class, DVDMixin.class);
	}

	public static void main(String[] args) {
		new Import().doImport();
	}

}
