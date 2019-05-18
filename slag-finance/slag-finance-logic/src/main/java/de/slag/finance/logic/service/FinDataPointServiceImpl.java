package de.slag.finance.logic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import de.slag.common.context.SlagContext;
import de.slag.finance.data.FinDataPointDao;
import de.slag.finance.model.FinDataPoint;

@Service
public class FinDataPointServiceImpl implements FinDataPointService {

	private FinDataPointDao finDataPointDao = SlagContext.getBean(FinDataPointDao.class);
	
	private FinDataPointCreator finDataPointCreator = SlagContext.getBean(FinDataPointCreator.class);
	
	@Override
	public boolean exists(String isin, LocalDate date) {
		final Collection<FinDataPoint> loadBy = finDataPointDao.loadBy(isin);
		return loadBy.stream()
				.filter(dataPoint -> dataPoint.getDate() != null)
				.anyMatch(dataPoint -> dataPoint.getDate().equals(date));
	}

	@Override
	public FinDataPoint create() {
		return finDataPointCreator.get();
	}

	@Override
	public void save(FinDataPoint dataPoint) {
		finDataPointDao.save(dataPoint);
	}
	
	@Override
	public Collection<Long> findAllIds() {
		return finDataPointDao.findAllIds();
	}

}
