package de.slag.finance2;

import java.time.LocalDate;
import java.util.Collection;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public interface FinDataPointManager {
	
	void persist(FinDataPoint finDataPoint);
	
	FinDataPoint get(String isin, LocalDate date, Kpi kpi, int[] params);
	
	Collection<FinDataPoint> findBy(String isin);

	
	
	
}
