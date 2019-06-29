package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;

@Entity
public class FinPrice extends AbstractFinDataPoint {
	
	private BigDecimal open;
	
	private BigDecimal close;
	
	private BigDecimal low;
	
	private BigDecimal high;

	public static class Builder {
		private Optional<String> isin = Optional.empty();
		private Optional<Date> date = Optional.empty();
		private Optional<BigDecimal> close = Optional.empty();

		public Builder isin(String isin) {
			this.isin = Optional.of(isin);
			return this;
		}

		public Builder date(Date date) {
			this.date = Optional.of(date);
			return this;
		}

		public Builder close(BigDecimal close) {
			this.close = Optional.of(close);
			return this;
		}

		public FinPrice build() {
			final FinPrice finPrice = new FinPrice();
			finPrice.setIsin(isin.get());
			finPrice.setDate(date.get());
			finPrice.setClose(close.get());
			return finPrice;
		}

	}

	@Override
	public Integer[] getParameter() {
		return new Integer[0];
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	@Override
	public String toString() {
		return "FinPrice [open=" + open + ", close=" + close + ", low=" + low + ", high=" + high + ", getIsin()="
				+ getIsin() + ", getDate()=" + getDate() + ", getId()=" + getId() + "]";
	}
	
	
}
