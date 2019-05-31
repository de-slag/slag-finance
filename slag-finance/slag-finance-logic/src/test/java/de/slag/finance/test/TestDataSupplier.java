package de.slag.finance.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import org.apache.commons.csv.CSVRecord;

import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.ResourceUtils;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

public class TestDataSupplier implements Supplier<Collection<FinDataPoint>> {

	private final Collection<FinDataPoint> dataPoints = new ArrayList<>();

	public TestDataSupplier() throws IOException {
		final String filename = "test-data.raw.csv";
		final String absolutePathFromResources = ResourceUtils.getFileFromResources(filename).getAbsolutePath();

		final Collection<CSVRecord> records = CsvUtils.getRecords(absolutePathFromResources);

		records.forEach(rec -> new FinDataPoint() {

			private final BigDecimal value = BigDecimal.valueOf(Double.valueOf(rec.get("VALUE")));

			@Override
			public void setValue(BigDecimal value) {
				throw new UnsupportedOperationException();

			}

			@Override
			public void setParameters(int[] parameters) {
				throw new UnsupportedOperationException();

			}

			@Override
			public void setKpi(Kpi kpi) {
				throw new UnsupportedOperationException();

			}

			@Override
			public void setIsin(String date) {
				throw new UnsupportedOperationException();

			}

			@Override
			public void setDate(LocalDate date) {
				// TODO Auto-generated method stub

			}

			@Override
			public BigDecimal getValue() {

				return value;
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
		});

	}

	@Override
	public Collection<FinDataPoint> get() {
		// TODO Auto-generated method stub
		return null;
	}

}
