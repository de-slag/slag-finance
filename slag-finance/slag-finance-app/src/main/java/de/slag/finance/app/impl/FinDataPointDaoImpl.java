package de.slag.finance.app.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.data.FinDataPointDao;
import de.slag.finance.model.FinDataPoint;

@Repository
public class FinDataPointDaoImpl extends AbstractDao<FinPersistDataPoint> implements FinDataPointDao {

	@Override
	public Collection<FinDataPoint> loadBy(String isin) {
		final Collection<Long> findAllIds = findAllIds();
		return findAllIds.stream()
				.map(id -> loadById(id))
				.filter(opt -> opt.isPresent())
				.map(opt -> opt.get())
				.filter(dp -> isin.equals(dp.getIsin()))
				.collect(Collectors.toList());
	}

	@Override
	public void save(FinDataPoint dataPoint) {
		super.save((FinPersistDataPoint) dataPoint);

	}

	@Override
	protected Class<FinPersistDataPoint> getPersistentType() {
		return FinPersistDataPoint.class;
	}

}
