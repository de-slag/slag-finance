package de.slag.finance.app.test;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.base.event.Event;
import de.slag.common.base.event.EventAction;
import de.slag.common.base.event.EventBus;
import de.slag.common.context.SlagContext;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.model.beans.SystemLogDao;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance3.logic.FinService;

public class FinContextTestApp {

	private static final Log LOG = LogFactory.getLog(FinContextTestApp.class);

	private FinService finService;

	private HibernateResource hibernateResource;

	private StringBuffer eventLogger;

	private SystemLogDao systemLogDao;

	public static void main(String[] args) {
		LoggingUtils.activateLogging();
		final FinContextTestApp app = new FinContextTestApp();

		try {
			app.setUp();

			// app.test();
			app.run();

			app.tearDown();
		} catch (Throwable t) {
			LOG.error("error execution", t);
			System.exit(1);
		}

		System.exit(0);

	}

	public void setUp() {
		LOG.info("set up...");
		hibernateResource = SlagContext.getBean(HibernateResource.class);
		if (!hibernateResource.isValid()) {
			hibernateResource.update();
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("Admin: \n");
		FinAdminSupport.getAll().forEach((key, value) -> sb.append(String.format("'%s': '%s'\n", key, value)));
		LOG.info(sb);

		finService = SlagContext.getBean(FinService.class);

		// FIXME hacky
		// ((FinServiceImpl)finService).init();

		systemLogDao = SlagContext.getBean(SystemLogDao.class);

		LOG.info("set up...done.");

		eventLogger = new StringBuffer();

		EventBus.addAction(new EventAction() {

			@Override
			public void run(Event event) {
				eventLogger.append(LocalDateTime.now() + ": " + event.getInfo() + "\n");

			}
		});
	}

	public void test() {

	}

	public void tearDown() {
		LOG.info("\n" + eventLogger.toString());
		systemLogDao.findAll().forEach(entry -> LOG.info(entry));
	}

	public void run() {
		finService.assertIsinWkn();
		finService.stageData();
		finService.importData();
		finService.calcAllAdministered();

	}

}
