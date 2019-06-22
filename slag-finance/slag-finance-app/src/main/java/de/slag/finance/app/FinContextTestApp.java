package de.slag.finance.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.context.SlagContext;
import de.slag.common.logging.LoggingUtils;
import de.slag.finance3.logic.FinService;

public class FinContextTestApp {
	
	private static final Log LOG = LogFactory.getLog(FinContextTestApp.class);
	
	public static void main(String[] args) {
		LoggingUtils.activateLogging();
		final FinService finService = SlagContext.getBean(FinService.class);
		LOG.info("success!");
		
	}

}
