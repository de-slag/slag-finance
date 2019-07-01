package de.slag.finance.deprecated;

import java.time.LocalDate;

import de.slag.finance.model.Kpi;

public interface FinCalcInput {
	
	String getIsin();
	
	LocalDate getDate();
	
	Kpi getKpi();
	
	Integer[] getParameters();

}
