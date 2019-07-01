package de.slag.finance.app.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.FinDataPointFactory;
import de.slag.finance.model.Kpi;

public class FinDataPointFactoryImpl implements FinDataPointFactory {

	private String isin;

	private LocalDate localDate;

	private Kpi kpi;

	private int[] params;

	private boolean created = false;

	private BigDecimal value;

	@Override
	public FinDataPoint create() {
		final FinPersistDataPoint finPersistDataPoint;
		synchronized (this) {
			if (created) {
				throw new UnsupportedOperationException("already created");
			}
			finPersistDataPoint = new FinPersistDataPoint();
			created = true;
		}

		finPersistDataPoint.setDate(Objects.requireNonNull(localDate));
		finPersistDataPoint.setIsin(Objects.requireNonNull(isin));
		finPersistDataPoint.setKpi(Objects.requireNonNull(kpi));
		finPersistDataPoint.setValue(Objects.requireNonNull(value));

		finPersistDataPoint.setParameters(params);

		return finPersistDataPoint;
	}

	@Override
	public void setIsin(String isin) {
		this.isin = isin;

	}

	@Override
	public void setDate(LocalDate date) {
		this.localDate = date;

	}

	@Override
	public void setKpi(Kpi kpi) {
		this.kpi = kpi;

	}

	@Override
	public void setParameters(int[] params) {
		this.params = params;

	}

	@Override
	public void setValue(BigDecimal value) {
		this.value = value;

	}

}
