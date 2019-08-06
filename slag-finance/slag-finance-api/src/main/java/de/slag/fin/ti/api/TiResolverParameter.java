package de.slag.fin.ti.api;

import java.time.LocalDate;

public interface TiResolverParameter {

	String getIsin();
	
	LocalDate getDate();

	default TiResolverParameter of(String isin, LocalDate date) {
		return new TiResolverParameter() {
			
			@Override
			public String getIsin() {
				return isin;
			}
			
			@Override
			public LocalDate getDate() {
				return date;
			}
		};
	}
}
