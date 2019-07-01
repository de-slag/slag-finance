package de.slag.finance3.logic;

import java.time.LocalDate;
import java.util.Optional;

import de.slag.finance.model.AbstractFinDataPoint;
import de.slag.finance.model.Kpi;

public interface FinDataStore {

	@Deprecated
	Optional<AbstractFinDataPoint> get(String isin, LocalDate date, Kpi kpi, Integer... params);
	
	<T extends AbstractFinDataPoint> Optional<T> get(Class<T> type, String isin, LocalDate date, Integer... params);

	void put(AbstractFinDataPoint dp);
	
	

}
