package de.slag.finance3.logic.calc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.slag.common.base.BaseException;
import de.slag.common.utils.MathUtils;
import de.slag.finance.data.model.Kpi;
import de.slag.finance3.logic.FinDataProvider;
import de.slag.finance3.logic.utils.StockDateUtils;

public class SmaCalcUtils {

	private static final int[] EMPTY_PARAMS = new int[0];

	public static BigDecimal calc(String isin, LocalDate date, Kpi kpi, int days, FinDataProvider dataProvider) {
		final Collection<LocalDate> stockDays = StockDateUtils.determineStockDays(date, days);

		final List<BigDecimal> values = stockDays.stream()
				.map(day -> dataProvider.apply(isin, day, Kpi.PRICE, EMPTY_PARAMS))
				.filter(v -> v.isPresent())
				.map(v -> v.get())
				.collect(Collectors.toList());

		if (values.size() != stockDays.size()) {
			final List<LocalDate> daysMissing = stockDays.stream()
					.filter(day -> !dataProvider.apply(isin, day, Kpi.PRICE, EMPTY_PARAMS)
							.isPresent())
					.collect(Collectors.toList());

			if (!daysMissing.isEmpty()) {
				throw new BaseException(String.format("data is missing for dates: %$", daysMissing));
			}
		}

		return MathUtils.average(values);
	}

}
