package de.slag.finance3.logic.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public interface FinDataPointService {

	void store(FinDataPoint dataPoint);

	Collection<FinDataPoint> getAll(String isin);
	
	FinDataPoint store(String isin, LocalDate date, Kpi kpi, int[] parameters, BigDecimal value);
	

	default Optional<FinDataPoint> get(String isin, LocalDate date, Kpi kpi, int[] parameters) {
		return getAll(isin).stream().filter(dp -> dp.getDate().equals(date)).filter(dp -> dp.getKpi() == kpi)
				.filter(dp -> Objects.deepEquals(dp.getParameters(), parameters)).findAny();
	}

}
