package de.slag.finance.logic.service;

import java.time.LocalDate;
import java.util.Collection;

import de.slag.finance.model.FinDataPoint;

public interface FinDataPointService {
	
	boolean exists(String isin, LocalDate date);
	
	FinDataPoint create();
	
	void save(FinDataPoint dataPoint);

	int count();

	Collection<Long> findAllIds();

}
