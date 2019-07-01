package de.slag.finance.app.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.logging.log4j.util.Strings;

import de.slag.common.model.EntityBean;
import de.slag.common.utils.DateUtils;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.Kpi;

//@Entity
@Table(name = "FIN_DATA_POINT")
public class FinPersistDataPoint extends EntityBean implements FinDataPoint {

	private final static String PARAMETERS_SEPARATOR = ";";

	@Basic
	private String isin;

	@Basic
	private Date date;

	@Enumerated(EnumType.STRING)
	private Kpi kpi;

	@Basic
	private BigDecimal value;

	@Basic
	private String parameters;

	public String getIsin() {
		return isin;
	}

	public LocalDate getDate() {
		return DateUtils.toLocalDate(date);
	}

	public Kpi getKpi() {
		return kpi;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public void setDate(LocalDate date) {
		this.date = DateUtils.toDate(date);
	}

	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int[] getParameters() {
		final String[] split = parameters.split(PARAMETERS_SEPARATOR);
		int[] a = new int[split.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = Integer.valueOf(split[i]);
		}
		return a;
	}

	public void setParameters(int[] parameters) {
		final List<String> collect = Arrays.asList(parameters).stream().map(i -> String.valueOf(i))
				.collect(Collectors.toList());
		this.parameters = Strings.join(collect, PARAMETERS_SEPARATOR.charAt(0));
	}
}
