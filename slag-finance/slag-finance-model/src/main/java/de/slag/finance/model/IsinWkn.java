package de.slag.finance.model;

import javax.persistence.Entity;

import de.slag.common.model.EntityBean;

@Entity
public class IsinWkn extends EntityBean {

	private String isin;
	
	private String wkn;

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
	
}
