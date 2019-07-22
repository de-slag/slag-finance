package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FinWma extends AbstractFinDataPoint {

	@Basic
	private Integer parameter;
	
	@Column(precision=7, scale=2)
	private BigDecimal value;

	public Integer[] getParameter() {
		return new Integer[] { parameter };
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

		public FinWma build() {
			final FinWma finSma = new FinWma();
			finSma.setIsin(isin.get());
			finSma.setDate(date.get());
			finSma.setValue(value.get());
			finSma.setParameter(parameter.get());
			return finSma;
		}
	}

	@Override
	public String toString() {
		return "FinWma [parameter=" + parameter + ", getIsin()=" + getIsin() + ", getDate()=" + getDate()
				+ ", getValue()=" + getValue() + ", getId()=" + getId() + "]";
	}

	public void setValue(BigDecimal value) {
		this.value = value;		
	}

	public BigDecimal getValue() {
		return value;
	}

}
