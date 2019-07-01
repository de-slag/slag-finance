package de.slag.finance.deprecated;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.slag.finance.model.Kpi;

public interface FinDataPointFactory2 {
	
	FinDataPoint create();
	
	void setIsin(String isin);
	
	void setDate(LocalDate date);
	
	void setKpi(Kpi kpi);
	
	void setValue(BigDecimal value);
	
	void setParameters(int[] params);

}
