package de.slag.finance3.logic;

import java.nio.file.Path;
import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.AbstractFinDataPoint;

public interface FinService {
	
	AbstractFinDataPoint calc(String isin, LocalDate date, Kpi kpi, Integer[] parameters);
	
	void stageData(Path path);
	
	void importData();

	void assertIsinWkn();
	
	void calcAllAdministered();

}
