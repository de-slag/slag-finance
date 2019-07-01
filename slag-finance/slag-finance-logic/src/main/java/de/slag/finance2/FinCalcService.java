package de.slag.finance2;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.model.Kpi;

public interface FinCalcService {
	
	BigDecimal calc (String isin, LocalDate date, Kpi kpi, int... params);

}
