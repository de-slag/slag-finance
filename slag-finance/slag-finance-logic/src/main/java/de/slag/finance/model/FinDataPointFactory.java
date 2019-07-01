package de.slag.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FinDataPointFactory {
	
	FinDataPoint create();
	
	void setIsin(String isin);
	
	void setDate(LocalDate date);
	
	void setKpi(Kpi kpi);
	
	void setValue(BigDecimal value);
	
	void setParameters(int[] params);

}
