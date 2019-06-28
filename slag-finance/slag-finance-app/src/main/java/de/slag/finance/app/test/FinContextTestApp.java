package de.slag.finance.app.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.base.event.Event;
import de.slag.common.base.event.EventAction;
import de.slag.common.base.event.EventBus;
import de.slag.common.context.SlagContext;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.DateUtils;
import de.slag.finance.FinPriceDao;
import de.slag.finance.FinSmaDao;
import de.slag.finance.model.FinPrice;
import de.slag.finance3.AvailableProperties;
import de.slag.finance3.events.DataImportedEvent;
import de.slag.finance3.logic.FinService;
import de.slag.finance3.logic.config.FinAdminSupport;

public class FinContextTestApp {

	private static final Log LOG = LogFactory.getLog(FinContextTestApp.class);

	private FinService finService;

	private HibernateResource hibernateResource;

	private FinSmaDao finSmaDao;

	private StringBuffer eventLogger;

	public static void main(String[] args) {
		LoggingUtils.activateLogging();
		final FinContextTestApp app = new FinContextTestApp();

		try {
			app.setUp();
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
		finSmaDao = SlagContext.getBean(FinSmaDao.class);

		LOG.info("set up...done.");

		eventLogger = new StringBuffer();

		EventBus.addAction(new EventAction() {

			@Override
			public void run(Event event) {
				if (!(event instanceof DataImportedEvent)) {
					return;
				}
				final FinPriceDao finPriceDao = SlagContext.getBean(FinPriceDao.class);
				final Collection<FinPrice> all = finPriceDao.findAll();

				final Date date = DateUtils.toDate(LocalDate.of(2019, 6, 10));

				Collection<FinPrice> c = all.stream().filter(p -> p.getDate().after(date)).collect(Collectors.toList());

				c.forEach(e -> LOG.info(e));

			}
		});

		EventBus.addAction(new EventAction() {

			@Override
			public void run(Event event) {
				eventLogger.append(LocalDateTime.now() + ": " + event.getInfo() + "\n");

			}
		});
	}

	public void tearDown() {
		LOG.info("\n" + eventLogger.toString());
	}

	public void run() {
		finService.assertIsinWkn();
		final Path path = Paths.get(FinAdminSupport.getSafe(AvailableProperties.IMPORT_DIR));
		finService.stageData(path);
		finService.importData();
		finService.calcAllAdministered();

	}

}
