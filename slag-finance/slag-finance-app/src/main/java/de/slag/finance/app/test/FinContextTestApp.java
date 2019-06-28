package de.slag.finance.app.test;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.XiDataDao;
import de.slag.common.context.SlagContext;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.logging.LoggingUtils;
import de.slag.finance.FinPriceDao;
import de.slag.finance3.AvailableProperties;
import de.slag.finance3.logic.FinService;
import de.slag.finance3.logic.config.FinAdminSupport;

public class FinContextTestApp {

	private static final Log LOG = LogFactory.getLog(FinContextTestApp.class);

	private FinService finService;

	private HibernateResource hibernateResource;

	private XiDataDao xiDataDao;

	public static void main(String[] args) {
		LoggingUtils.activateLogging();
		final FinContextTestApp app = new FinContextTestApp();

		// TODO tear down if error
		try {
			app.setUp();
			app.test();
			app.run();
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
		xiDataDao = SlagContext.getBean(XiDataDao.class);

		LOG.info("set up...done.");
	}

	public void test() {

	}

	public void run() {
		finService.assertIsinWkn();
		final Path path = Paths.get(FinAdminSupport.getSafe(AvailableProperties.IMPORT_DIR));
		finService.stageData(path);

		xiDataDao.findAllIds().stream().map(id -> xiDataDao.loadById(id)).filter(xi -> xi.isPresent())
				.map(xi -> xi.get()).forEach(xi -> LOG.info("import: " + xi));

		finService.importData();
		
		finService.stageData(path);
		finService.importData();
		
		
		
		
	}

}
