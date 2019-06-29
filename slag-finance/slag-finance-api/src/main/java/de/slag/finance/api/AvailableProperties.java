package de.slag.finance.api;

public interface AvailableProperties {

	String IMPORT_DIR = "finance.import.dir";
	
	@Deprecated
	String DATA_WKN = "finance.data.wkn";
	
	String DATA_WKN_ISINS = "finance.data.wknisins";
	
	String CALC_KPIS = "finance.calc.kpis";
	
	String ALLOVER_START_DATE = "finance.allover.start.date";
	
	String ALLOVER_MAX_PARAMETER = "finance.allover.max.parameter";


}
