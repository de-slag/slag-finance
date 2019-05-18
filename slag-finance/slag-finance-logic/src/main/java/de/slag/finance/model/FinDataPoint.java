package de.slag.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.data.model.Kpi;

public interface FinDataPoint {
	
	String getIsin();
	
	void setIsin(String date);
	
	LocalDate getDate();
	
	void setDate(LocalDate date);
	
	Kpi getKpi();
	
	void setKpi(Kpi kpi);
	
	BigDecimal getValue();
	
	void setValue(BigDecimal value);
	
	int[] getParameters();
	
	void setParameters(int[] parameters);

}
