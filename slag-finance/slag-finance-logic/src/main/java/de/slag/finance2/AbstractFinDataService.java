package de.slag.finance2;

import java.time.LocalDate;

import de.slag.finance.data.FinDataPointDao;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public abstract class AbstractFinDataService implements FinDataService {

	protected abstract FinDataPointsHolder getDataPointsHolder();

	protected abstract FinDataPointDao getDao();

	@Override
	public FinDataPoint getPoint(String isin, LocalDate date, Kpi kpi) {
		final FinDataPointsHolder dataPointsHolder = getDataPointsHolder();
		synchronized (dataPointsHolder) {
			if (!dataPointsHolder.contains(isin, date, kpi)) {
				calc(dataPointsHolder, isin, date, kpi);
			}
		}
		return dataPointsHolder.get(isin, date, kpi);

	}

	private void calc(FinDataPointsHolder dataPointsHolder, String isin, LocalDate date, Kpi kpi) {
		// TODO Auto-generated method stub
		
	}

}
