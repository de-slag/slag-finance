package de.slag.finance2.deprecated;

import java.time.LocalDate;
import java.util.Collection;

import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.model.Kpi;

public interface FinDataPointManager {
	
	void persist(FinDataPoint finDataPoint);
	
	FinDataPoint get(String isin, LocalDate date, Kpi kpi, int[] params);
	
	Collection<FinDataPoint> findBy(String isin);

	
	
	
}
