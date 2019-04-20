package de.slag.finance.data.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RawDataPoint {
	
	private String isin;
	
	private LocalDate date;
	
	private KPI kpi;
	
	private BigDecimal value;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public KPI getKpi() {
		return kpi;
	}

	public void setKpi(KPI kpi) {
		this.kpi = kpi;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
