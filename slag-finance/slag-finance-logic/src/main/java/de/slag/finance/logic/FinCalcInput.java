package de.slag.finance.logic;

import java.time.LocalDate;

import de.slag.finance.data.model.KPI;

public interface FinCalcInput {
	
	String getIsin();
	
	LocalDate getDate();
	
	KPI getKpi();
	
	Integer[] getParameters();

}
