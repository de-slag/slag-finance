package de.slag.finance.app;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.context.SlagContext;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.SlagConfigSupportUtils;
import de.slag.finance.data.RawDataSource;
import de.slag.finance.logic.FinCentralService;
import de.slag.finance.logic.FinProperties;

public class FinApp {
	
	private static final Log LOG = LogFactory.getLog(FinApp.class);

	public static void main(String[] args) {
		LoggingUtils.activateLogging(Level.DEBUG);
		final FinProperties finProperties = new FinProperties(SlagConfigSupportUtils.getConfigProperties());
		final String financeWorkdir = finProperties.getFinanceWorkdir();
		
		
		
		
		
		
		SlagContext.init();
		final FinCentralService finCentralService = SlagContext.getBean(FinCentralService.class);
		finCentralService.doNothing();
		
		
		
		
		RawDataSource rds = new RawDataSource();
		
		Collection<Long> allIds = rds.findAllIds();
		
		LOG.info(allIds);
	}

}
