package de.slag.finance.app.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import de.slag.common.context.SlagContext;
import de.slag.finance.data.FinDataPointDao;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.FinDataPointDefaultComparator;
import de.slag.finance2.FinDataPointManager;

//@Service
public class FinDataPointManagerImpl implements FinDataPointManager {
	
	private FinDataPointDao finDataPointDao = SlagContext.getBean(FinDataPointDao.class); 

	@Override
	public void persist(FinDataPoint finDataPoint) {
		finDataPointDao.save(finDataPoint);
		
	}

	@Override
	public FinDataPoint get(String isin, LocalDate date, Kpi kpi, int[] params) {
		final Collection<FinDataPoint> loadBy = finDataPointDao.findBy(isin);
		FinDataPoint dp = new FinDataPoint() {
			
			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}
			
			@Override
			public int[] getParameters() {
				return params;
			}
			
			@Override
			public Kpi getKpi() {
				return kpi;
			}
			
			@Override
			public String getIsin() {
				return isin;
			}
			
			@Override
			public LocalDate getDate() {
				return date;
			}
		};
		Comparator<FinDataPoint> c = new FinDataPointDefaultComparator();
		return loadBy.stream().filter(e -> c.compare(e, dp) == 0).findAny().get();
		
		
	}

	@Override
	public Collection<FinDataPoint> findBy(String isin) {
		return finDataPointDao.findBy(isin);
	}

}
