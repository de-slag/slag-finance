package de.slag.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.model.FinDataPoint.Builder;

public interface FinDataPointBuilder {

	Builder isin(String isin);

	Builder date(LocalDate date);

	Builder kpi(Kpi kpi);

	Builder value(BigDecimal value);

	Builder parameters(int[] parameters);

	FinDataPoint build();

}
