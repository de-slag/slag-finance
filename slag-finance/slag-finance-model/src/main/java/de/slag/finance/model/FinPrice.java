package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;

@Entity
public class FinPrice extends AbstractFinDataPoint {

	public static class Builder {
		private Optional<String> isin = Optional.empty();
		private Optional<Date> date = Optional.empty();
		private Optional<BigDecimal> value = Optional.empty();

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

		public FinPrice build() {
			final FinPrice finPrice = new FinPrice();
			finPrice.setIsin(isin.orElseThrow());
			finPrice.setDate(date.orElseThrow());
			finPrice.setValue(value.orElseThrow());
			return finPrice;
		}

	}
}