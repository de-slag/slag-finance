package de.slag.finance.deprecated;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.deprecated.FinDataPoint.Builder;
import de.slag.finance.model.Kpi;

public interface FinDataPointBuilder {

	Builder isin(String isin);

	Builder date(LocalDate date);

	Builder kpi(Kpi kpi);

	Builder value(BigDecimal value);

	Builder parameters(int[] parameters);

	FinDataPoint build();

}
