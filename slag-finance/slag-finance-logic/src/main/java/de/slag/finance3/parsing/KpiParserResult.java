package de.slag.finance3.parsing;

import java.util.Map;

import de.slag.finance.model.Kpi;

public interface KpiParserResult {
	
	Kpi getKpi();
	
	Map<String,Integer> getParameter();	

}
