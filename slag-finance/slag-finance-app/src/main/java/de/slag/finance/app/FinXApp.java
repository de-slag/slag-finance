package de.slag.finance.app;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.base.BaseException;
import de.slag.common.base.event.Event;
import de.slag.common.base.event.EventAction;
import de.slag.common.base.event.EventBus;
import de.slag.common.context.SlagContext;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.model.beans.SystemLogDao;
import de.slag.common.utils.SleepUtils;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance.api.FinService;

public class FinXApp {

	private static final Log LOG = LogFactory.getLog(FinXApp.class);

	private FinService finService;

	private HibernateResource hibernateResource;

	private StringBuffer eventLogger;

	private SystemLogDao systemLogDao;

	private File controlDir;

	public static void main(String[] args) {
		LoggingUtils.activateLogging();
		final FinXApp app = new FinXApp();

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

		String workdir = FinAdminSupport.getSafe(AvailableProperties.WORKDIR);
		String controlDirName = workdir + "/control";
		File file = new File(controlDirName);
		if (!file.exists()) {
			throw new BaseException("control dir does not exists: " + controlDirName);
		}
		if (!file.isDirectory()) {
			throw new BaseException("control dir is not a directory: " + controlDirName);
		}
		this.controlDir = file;

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

		while (true) {
			if (isControl("STOP")) {
				LOG.info("stop program");
				break;
			} else if (isControl("STAGE")) {
				finService.stageData();
			} else if (isControl("IMPORT")) {
				finService.importData();				
			} else if (isControl("CALC")) {
				finService.calcAllAdministered();				
			} else {
				LOG.info("do nothing");
			}

			SleepUtils.sleepFor(500);
		}


	}

	private boolean isControl(String controlString) {
		File[] listFiles = controlDir.listFiles();
		
		Stream<File> stream = Arrays.stream(listFiles);
		Predicate<? super File> predicate = file -> controlString.equals(file.getName());
		
		Optional<File> findAny = stream.filter(predicate).findAny();
		if(!findAny.isPresent()) {
			return false;
		}
		final File file = findAny.get();
		
		file.delete();
		
		return true;
	}

}
