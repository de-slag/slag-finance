package de.slag.finance.logic;

import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.logic.service.FinDataPointService;

@Service
public class FinCalcServiceImpl implements FinCalcService {
	
	private FinDataPointService finDataPointService;
	
	@Override
	public FinCalcOutput calc(final FinCalcInput input) {
		final Kpi kpi = input.getKpi();
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
