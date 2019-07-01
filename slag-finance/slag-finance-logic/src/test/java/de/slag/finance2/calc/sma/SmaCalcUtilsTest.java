package de.slag.finance2.calc.sma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.slag.common.base.BaseException;
import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance2.deprecated.SmaCalcUtils;

public class SmaCalcUtilsTest {

	private final Collection<FinDataPoint> dataPoints = new ArrayList<>();

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testCalcSma() {
		final BigDecimal calcSma = SmaCalcUtils.calcSma(dataPoints,
				LocalDate.of(2018, 4, 4), 25);
	}

	@Test(expected = BaseException.class)
	public void testCalcSmaSizeNotMatching() {
		final BigDecimal calcSma = SmaCalcUtils.calcSma(new ArrayList<>(),
				LocalDate.of(2018, 4, 4), 25);
	}

}
