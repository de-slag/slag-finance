package de.slag.finance.api;

import java.time.LocalDate;

import de.slag.finance.model.AbstractFinDataPoint;
import de.slag.finance.model.Kpi;

public interface FinService {
	
	AbstractFinDataPoint calc(String isin, LocalDate date, Kpi kpi, Integer[] parameters);
	
	void stageData();
	
	void importData();

	void assertIsinWkn();
	
	void calcAllAdministered();


}
