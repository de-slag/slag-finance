package de.slag.finance3.logic;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance3.logic.interfaces.FinDataPointService;

@Service
public class FinServiceImpl implements FinService {

	private FinDataPointService finDataPointService = SlagContext.getBean(FinDataPointService.class);

	@Override
	public FinDataPoint calc(String isin, LocalDate date, Kpi kpi, int[] parameters) {
		switch (kpi) {
		case PRICE:
			final Optional<FinDataPoint> optional = finDataPointService.get(isin, date, kpi, parameters);
			if (!optional.isPresent()) {
				throw new BaseException(String.format("Price is not available for: %s %s", isin, date));
			}

		default:
			throw new BaseException(String.format("Kpi '%s' is not supported yet", kpi));
		}
	}

}
