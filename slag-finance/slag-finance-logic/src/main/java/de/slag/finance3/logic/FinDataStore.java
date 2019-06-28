package de.slag.finance3.logic;

import java.time.LocalDate;
import java.util.Optional;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.AbstractFinDataPoint;

public interface FinDataStore {

	Optional<AbstractFinDataPoint> get(String isin, LocalDate date, Kpi kpi, Integer... params);

	void put(AbstractFinDataPoint dp);
	
	

}
