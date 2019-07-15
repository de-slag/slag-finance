package de.slag.finance.model;

import java.util.Objects;

import javax.persistence.Entity;

import de.slag.common.model.EntityBean;

@Entity
public class StockTitle extends EntityBean {

	private String isin;
	
	private String wkn;
	
	private String notation;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getWkn() {
		return wkn;
	}

	public void setWkn(String wkn) {
		this.wkn = wkn;
	}
	
	@Override
	public String toString() {
		return "IsinWkn [isin=" + isin + ", wkn=" + wkn + "]";
	}
	
	public static class Builder {
		String isin;
		String wkn;
	
		public Builder isin(String isin) {
			this.isin = isin;
			return this;
		}
		
		public Builder wkn(String wkn) {
			this.wkn = wkn;
			return this;
		}
		
		public StockTitle build() {
			final StockTitle builded = new StockTitle();
			builded.setIsin(Objects.requireNonNull(isin));
			builded.setWkn(Objects.requireNonNull(wkn));
			return builded;
		}
		
	}

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	
}
