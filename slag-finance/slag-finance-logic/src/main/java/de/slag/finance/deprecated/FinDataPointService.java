package de.slag.finance.deprecated;

import java.time.LocalDate;
import java.util.Collection;

public interface FinDataPointService {
	
	FinDataPointDao getDao();

	FinDataPoint getPersistable(FinDataPoint dataPoint);
	
	FinDataPointFactory getFactory();
	
	default boolean exists(String isin, LocalDate date) {
		final Collection<FinDataPoint> loadBy = getDao().findBy(isin);
		return loadBy.stream()
				.filter(dataPoint -> dataPoint.getDate() != null)
				.anyMatch(dataPoint -> dataPoint.getDate().equals(date));
	}
	
	/**
	 * ..use getFactory() instead
	 */
	
	@Deprecated
	FinDataPoint create();
	

	default void save(FinDataPoint dataPoint) {
		getDao().save(getPersistable(dataPoint));
	}

	default int count() {
		return findAllIds().size();
	}

	default Collection<Long> findAllIds() {
		return getDao().findAllIds();
	}
	

}
