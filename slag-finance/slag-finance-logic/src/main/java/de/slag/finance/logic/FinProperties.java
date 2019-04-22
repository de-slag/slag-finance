package de.slag.finance.logic;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.utils.SlagConfigSupportUtils;
import de.slag.finance.Constants;

public class FinProperties extends Properties {

	private static final Log LOG = LogFactory.getLog(FinProperties.class);

	private static final long serialVersionUID = 1L;

	public FinProperties(Properties p) {
		super(p);
		LOG.debug(p);
	}

	public String getFinanceWorkdir() {
		return getProperty(Constants.FINANCE_WORKDIR);
	}

	public String getRawDataFile() {
		return getProperty(Constants.RAW_DATA_FILE);
	}

	private static FinProperties instance() {
		return new FinProperties(SlagConfigSupportUtils.getConfigProperties());
	}

	public static String financeWorkdir() {
		return instance().getFinanceWorkdir();
	}

	public static String rawDataFile() {
		return instance().getRawDataFile();
	}

}
