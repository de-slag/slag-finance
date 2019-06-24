package de.slag.finance3.logic;

import java.nio.file.Path;
import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public interface FinService {
	
	FinDataPoint calc(String isin, LocalDate date, Kpi kpi, int[] parameters);
	
	void importData(Path fromPath);

	void assertIsinWkn();

}
