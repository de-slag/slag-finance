package de.slag.finance2.deprecated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.slag.common.base.BaseException;
import de.slag.common.utils.MathUtils;
import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.deprecated.FinDataPointDefaultComparator;
import de.slag.finance.deprecated.FinStockDateUtils;

public class SmaCalcUtils {

	public static BigDecimal calcSma(Collection<FinDataPoint> dataPoints,
			LocalDate forDate, int stockDaysBack) {

		final Collection<LocalDate> stockDaysToCalc = FinStockDateUtils
				.determineStockDays(forDate, stockDaysBack);

		final List<FinDataPoint> dataPointsToCalc = dataPoints.stream()
				.filter(dp -> stockDaysToCalc.contains(dp.getDate()))
				.collect(Collectors.toList());

		if (dataPointsToCalc.size() != stockDaysToCalc.size()) {
			throw new BaseException("sizes not matching");
		}

		final Set<FinDataPoint> set = new TreeSet<>(
				new FinDataPointDefaultComparator());
		set.addAll(dataPointsToCalc);

		final List<BigDecimal> values = set.stream().map(dp1 -> dp1.getValue())
				.collect(Collectors.toList());
		final BigDecimal sum = MathUtils.addAll(values);

		return MathUtils.divide(sum, set.size());

	}

}
