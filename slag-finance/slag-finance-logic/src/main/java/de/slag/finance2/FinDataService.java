package de.slag.finance2;

import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public interface FinDataService {
	
	FinDataPoint getPoint(String isin, LocalDate date, Kpi kpi);

}
