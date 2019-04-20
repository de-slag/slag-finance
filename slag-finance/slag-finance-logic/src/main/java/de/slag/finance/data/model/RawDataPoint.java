package de.slag.finance.data.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RawDataPoint {
	
	private String isin;
	
	private LocalDate date;
	
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RawDataPoint [isin=" + isin + ", date=" + date + ", value=" + value + "]";
	}
	
	

}
