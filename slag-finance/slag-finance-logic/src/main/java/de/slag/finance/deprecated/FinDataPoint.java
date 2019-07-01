package de.slag.finance.deprecated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import de.slag.finance.model.Kpi;

public interface FinDataPoint {

	String getIsin();

	LocalDate getDate();

	Kpi getKpi();

	BigDecimal getValue();

	int[] getParameters();

	/**
	 * 
	 * creates a no persistable FinDataPoint
	 *
	 */
	class Builder implements FinDataPointBuilder {
		private String buildIsin;
		private LocalDate buildDate;
		private Kpi buildKpi;
		private BigDecimal buildValue;
		private int[] buildParameters;

		@Override
		public Builder isin(String isin) {
			this.buildIsin = Objects.requireNonNull(isin, "isin null");
			return this;
		}

		@Override
		public Builder date(LocalDate date) {
			this.buildDate = Objects.requireNonNull(date, "date null");
			return this;
		}

		@Override
		public Builder kpi(Kpi kpi) {
			this.buildKpi = Objects.requireNonNull(kpi, "kpi null");
			return this;
		}

		@Override
		public Builder value(BigDecimal value) {
			this.buildValue = Objects.requireNonNull(value, "value null");
			return this;
		}

		@Override
		public Builder parameters(int[] parameters) {
			this.buildParameters = parameters;
			return this;
		}

		@Override
		public FinDataPoint build() {

			return new FinDataPoint() {
				private String isin = Objects.requireNonNull(buildIsin, "isin null");
				private LocalDate date = Objects.requireNonNull(buildDate, "date null");
				private Kpi kpi = Objects.requireNonNull(buildKpi, "kpi null");
				private BigDecimal value = Objects.requireNonNull(buildValue, "value null");
				private int[] parameters = buildParameters != null ? buildParameters : new int[0];

				@Override
				public BigDecimal getValue() {
					return value;
				}

				@Override
				public int[] getParameters() {
					return parameters;
				}

				@Override
				public Kpi getKpi() {
					return kpi;
				}

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

}
