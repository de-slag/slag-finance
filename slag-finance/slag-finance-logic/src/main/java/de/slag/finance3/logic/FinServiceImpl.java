package de.slag.finance3.logic;

import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;

@Service
public class FinServiceImpl implements FinService {
	
	private static final Log LOG = LogFactory.getLog(FinServiceImpl.class);

	@Override
	public FinDataPoint calc(String isin, LocalDate date, Kpi kpi, int[] parameters) {
		switch (kpi) {
		case PRICE:
			final Optional<FinDataPoint> optional = Optional.empty();// = finDataPointService.get(isin, date, kpi, parameters);
			if (!optional.isPresent()) {
				throw new BaseException(String.format("Price is not available for: %s %s", isin, date));
			}

		default:
			throw new BaseException(String.format("Kpi '%s' is not supported yet", kpi));
		}
	}

}
