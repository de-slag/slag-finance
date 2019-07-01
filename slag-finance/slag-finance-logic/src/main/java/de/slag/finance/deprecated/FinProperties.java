package de.slag.finance.deprecated;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.utils.SlagConfigSupportUtils;

public class FinProperties extends Properties {

	private static final Log LOG = LogFactory.getLog(FinProperties.class);

	private static final long serialVersionUID = 1L;

	private FinProperties(Properties p) {
		super(p);
		LOG.debug(p);
	}

	private static FinProperties instance() {
		return new FinProperties(SlagConfigSupportUtils.getConfigProperties());
	}
	
	public static String financeWorkdir() {
		return FinProperties.property(Constants.FINANCE_WORKDIR);
	}

	public static String rawDataFile() {
		return FinProperties.property(Constants.RAW_DATA_FILE);
	}
	
	public static String rawDataPostfix() {
		return property(Constants.RAW_DATA_POSTFIX);
	}
	
	public static String property(String key) {
		return instance().getProperty(key);
	}
}
