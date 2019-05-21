package de.slag.finance.data;

import java.util.Collection;

import de.slag.finance.model.FinDataPoint;

public interface FinDataPointDao {
	
	Collection<FinDataPoint> loadBy(String isin);
	
	void save(FinDataPoint dataPoint);
	
<<<<<<< HEAD
	Collection<Long> findAllIds();
=======
	public Collection<Long> findAllIds();
>>>>>>> branch 'master' of https://github.com/de-slag/slag-finance.git

}
