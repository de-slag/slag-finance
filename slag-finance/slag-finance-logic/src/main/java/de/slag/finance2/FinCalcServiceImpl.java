package de.slag.finance2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import de.slag.common.context.SlagContext;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.Kpi;
import de.slag.finance2.calc.sma.SmaCalcUtils;

public class FinCalcServiceImpl implements FinCalcService {

	private FinAppContext finAppContext;// = SlagContext.getBean(FinAppContext.class);

	private Collection<FinDataPoint> findBy(String isin) {
		return finAppContext.getDataPointManager()
				.findBy(isin);
	}

	@Override
	public BigDecimal calc(String isin, LocalDate date, Kpi kpi, int... params) {
		if (Kpi.SMA == kpi) {
			return SmaCalcUtils.calcSma(findBy(isin), date, params[0]);
		}
		throw new IllegalStateException(String.format("not supported: %s", kpi));
	}

}
