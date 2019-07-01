package de.slag.finance.deprecated;

import java.util.Collection;

public interface FinDataPointDao {
	
	Collection<FinDataPoint> findBy(String isin);
	
	void save(FinDataPoint dataPoint);
	
	Collection<Long> findAllIds();


}
