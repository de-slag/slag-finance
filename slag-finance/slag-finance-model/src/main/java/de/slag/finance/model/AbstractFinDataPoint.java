package de.slag.finance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.slag.common.model.EntityBean;

@MappedSuperclass
public abstract class AbstractFinDataPoint extends EntityBean {

	@Column
	private String isin;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Enumerated(EnumType.STRING)
	private DataOrigin origin;
	
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
}
