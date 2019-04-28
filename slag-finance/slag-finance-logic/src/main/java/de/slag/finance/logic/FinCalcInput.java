package de.slag.finance.logic;

import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;

public interface FinCalcInput {
	
	String getIsin();
	
	LocalDate getDate();
	
	Kpi getKpi();
	
	Integer[] getParameters();

}
