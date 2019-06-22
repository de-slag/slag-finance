package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;

@Entity
public class FinSma extends AbstractFinDataPoint {

	private Integer parameter;

	public Integer getParameter() {
		return parameter;
	}

	public void setParameter(Integer parameter) {
		this.parameter = parameter;
	}

	public static class Builder {
		private Optional<String> isin = Optional.empty();
		private Optional<Date> date = Optional.empty();
		private Optional<BigDecimal> value = Optional.empty();
		private Optional<Integer> parameter = Optional.empty();

		public Builder isin(String isin) {
			this.isin = Optional.of(isin);
			return this;
		}

		public Builder date(Date date) {
			this.date = Optional.of(date);
			return this;
		}

		public Builder value(BigDecimal value) {
			this.value = Optional.of(value);
			return this;
		}
		
		public Builder parameter(Integer parameter) {
			this.parameter = Optional.of(parameter);
			return this;
		}

		public FinSma build() {
			final FinSma finSma = new FinSma();
			finSma.setIsin(isin.orElseThrow());
			finSma.setDate(date.orElseThrow());
			finSma.setValue(value.orElseThrow());
			finSma.setParameter(parameter.orElseThrow());
			return finSma;
		}

	}

}
