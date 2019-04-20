package de.slag.finance.logic;

import java.util.Properties;

import de.slag.finance.Constants;

public class FinProperties extends Properties {
	
	public FinProperties(Properties p) {
		super(p);
	}

	private static final long serialVersionUID = 1L;
	
	public String getFinanceWorkdir() {
		return getProperty("finance-workdir");
	}
	
	public String getRawDataFile() {
		return getProperty(Constants.RAW_DATA_FILE);
	}

}
