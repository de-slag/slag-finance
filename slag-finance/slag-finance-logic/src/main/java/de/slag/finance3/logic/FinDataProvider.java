package de.slag.finance3.logic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import de.slag.common.base.function.QuadFunction;
import de.slag.finance.data.model.Kpi;

public interface FinDataProvider extends QuadFunction<String, LocalDate, Kpi, int[], Optional<BigDecimal>> {
	
	Optional<BigDecimal> apply(String isin, LocalDate date, Kpi kpi, int[] params);

}
