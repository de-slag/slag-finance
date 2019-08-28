package de.slag.finance3.logic.calc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.slag.common.base.BaseException;
import de.slag.common.utils.DateUtils;
import de.slag.common.utils.MathUtils;
import de.slag.finance.model.FinPrice;
import de.slag.finance.model.FinSma;
import de.slag.finance.model.Kpi;
import de.slag.finance3.logic.FinDataStore;
import de.slag.finance3.logic.utils.StockDateUtils;

public class SmaCalcUtils {

	public static FinSma calc(String isin, LocalDate date, Kpi kpi, int days, FinDataStore dataStore) {
		final Collection<LocalDate> stockDays = StockDateUtils.determineStockDays(date, days);

		
		final List<FinPrice> values = stockDays.stream()
				.map(day -> dataStore.get(isin, day, Kpi.PRICE))
				.filter(v -> v.isPresent())
				.map(v -> v.get())
				.map(v -> (FinPrice)v)
				.collect(Collectors.toList());

		if (values.size() != stockDays.size()) {
			final List<LocalDate> daysMissing = stockDays.stream()
					.filter(day -> !dataStore.get(isin, day, Kpi.PRICE).isPresent()).collect(Collectors.toList());

			if (!daysMissing.isEmpty()) {
				throw new BaseException(String.format("data is missing for dates: %s", daysMissing));
			}
		}

		final BigDecimal average = MathUtils
				.average(values.stream()
						.map(v -> v.getClose())
						.collect(Collectors.toList()));
		
		final FinSma sma = new FinSma.Builder().date(DateUtils.toDate(date)).isin(isin).parameter(days).value(average)
				.build();
		dataStore.put(sma);
		return sma;
	}

}
