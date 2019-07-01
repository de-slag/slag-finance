package de.slag.finance.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

import org.apache.commons.csv.CSVRecord;

import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.model.Kpi;

public class CsvToDataPoint implements Function<CSVRecord, FinDataPoint> {

	@Override
	public FinDataPoint apply(CSVRecord t) {

		// TODO Auto-generated method stub
		return new FinDataPoint() {
			
			@Override
			public BigDecimal getValue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int[] getParameters() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Kpi getKpi() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getIsin() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public LocalDate getDate() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
