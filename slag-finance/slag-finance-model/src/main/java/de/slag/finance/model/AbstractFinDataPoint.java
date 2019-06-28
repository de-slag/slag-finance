package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import de.slag.common.model.EntityBean;

@MappedSuperclass

public abstract class AbstractFinDataPoint extends EntityBean {

	private String isin;
	private Date date;
	private BigDecimal value;
	
	public abstract Integer[] getParameter();

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
