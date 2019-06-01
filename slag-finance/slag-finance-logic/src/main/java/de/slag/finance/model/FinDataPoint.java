package de.slag.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;

public interface FinDataPoint {

	String getIsin();

	LocalDate getDate();

	Kpi getKpi();

	BigDecimal getValue();

	int[] getParameters();

}
