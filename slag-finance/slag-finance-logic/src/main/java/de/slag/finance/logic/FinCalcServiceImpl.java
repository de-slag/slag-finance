package de.slag.finance.logic;

import de.slag.common.base.BaseException;
import de.slag.finance.data.model.KPI;

public class FinCalcServiceImpl implements FinCalcService {
	
	private FinDataProvider finDataProvider;

	@Override
	public FinCalcOutput calc(final FinCalcInput input) {
		final KPI kpi = input.getKpi();
		switch (kpi) {
		case SMA:
			return calcSma(input);
		default:
			throw new BaseException("not supported:" + kpi);
		}
	}

	private FinCalcOutput calcSma(final FinCalcInput input) {
		
		// TODO Auto-generated method stub
		return null;
	}
}
