package de.slag.finance.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.base.SlagConfig;
import de.slag.common.context.SlagContext;
import de.slag.common.db.h2.InMemoryProperties;
import de.slag.common.db.hibernate.HibernateDbUpdateUtils;
import de.slag.common.logging.LoggingUtils;
import de.slag.finance.imp.RawDataImportService;

public class FinApp {

	private static final Log LOG = LogFactory.getLog(FinApp.class);

	public static void main(String[] args) {
		LoggingUtils.activateLogging(Level.DEBUG);
		try {
			new FinApp().run();
		} catch (Throwable t) {
			LOG.error("fehler",t);
		} finally {
			LOG.info("exit");
			System.exit(0);
		}
	}

	public void run() {

		SlagContext.init();

		if (!HibernateDbUpdateUtils.isValid(new InMemoryProperties())) {
			LOG.warn("db not valid");
			HibernateDbUpdateUtils.update(new InMemoryProperties());
		}

		LOG.info("db update done");


		final RawDataImportService rawDataImportService = SlagContext.getBean(RawDataImportService.class);

		rawDataImportService.importFrom(SlagConfig.getConfigProperties().getProperty("slag.finance.importfolder"));
	}

}
