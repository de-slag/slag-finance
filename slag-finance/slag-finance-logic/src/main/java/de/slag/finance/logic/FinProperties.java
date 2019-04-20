package de.slag.finance.logic;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.finance.Constants;

public class FinProperties extends Properties {
	
	private static final Log LOG = LogFactory.getLog(FinProperties.class);
	
	public FinProperties(Properties p) {
		super(p);
		LOG.debug(p);
	}

	private static final long serialVersionUID = 1L;
	
	public String getFinanceWorkdir() {
		return getProperty(Constants.FINANCE_WORKDIR);
	}
	
	public String getRawDataFile() {
		return getProperty(Constants.RAW_DATA_FILE);
	}

}
