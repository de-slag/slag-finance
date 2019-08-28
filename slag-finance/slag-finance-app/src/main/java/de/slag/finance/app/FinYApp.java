package de.slag.finance.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.logging.LoggingUtils;

public class FinYApp {

	private static final Log LOG = LogFactory.getLog(FinYApp.class);

	public static void main(String[] args) {
		LoggingUtils.activateLogging(Level.DEBUG);
		FinYApp finYApp = new FinYApp();
		finYApp.setUp();
		finYApp.run();
	}

	private void setUp() {
		LOG.info("set up...");
		final HibernateResource hibernateResource = SlagContext.getBean(HibernateResource.class);
		if (!hibernateResource.isValid()) {
			hibernateResource.update();
		}
		if (!hibernateResource.isValid()) {
			throw new BaseException("could not update database.");
		}
		LOG.info("database updated");
		LOG.info("set up...done.");
	}

	public void run() {
		LOG.debug("test");
	}

}
