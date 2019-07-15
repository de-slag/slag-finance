package de.slag.finance.api;

import de.slag.finance.api.FinAdminSupport.MandatoryProperty;

public interface AvailableProperties {
	
	@Deprecated
	String IMPORT_DIR = "finance.import.dir";
	
	@Deprecated
	String DATA_WKN = "finance.data.wkn";
	
	String DATA_WKN_ISINS = "finance.data.wknisins";
	
	String CALC_KPIS = "finance.calc.kpis";
	
	String ALLOVER_START_DATE = "finance.allover.start.date";
	
	@Deprecated
	String ALLOVER_MAX_PARAMETER = "finance.allover.max.parameter";
	
	@MandatoryProperty
	String WORKDIR = "finance.workdir";
	
	String FINANCE_INTERFACE_AV_KEY = "finance.interface.av.key";
	
	String FINANCE_INTERFACE_AV_URL = "finance.interface.av.url";
	
	String FINANCE_INTERFACE_OV_URL = "finance.interface.ov.url";
	
	String FINANCE_DATA_ISIN_NOTATIONS = "finance.data.isin.notations";


}
