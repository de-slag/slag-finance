package de.slag.finance3.logic.calc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import de.slag.common.base.BaseException;
import de.slag.finance3.logic.calc.WmaCalculationRunner;

public class WmaTest {

	private Function<List<Integer>, List<BigDecimal>> INTEGER_LIST_TO_BIG_DECIMAL_LIST = l -> l.stream()
			.map(i -> BigDecimal.valueOf(Double.valueOf(i))).collect(Collectors.toList());

	@Test
	public void calc1() throws Exception {
		final WmaCalculationRunner calculator = new WmaCalculationRunner(
				INTEGER_LIST_TO_BIG_DECIMAL_LIST.apply(Arrays.asList(1000, 1011, 1020, 1025, 995, 975, 950)));
		final BigDecimal result = calculator.call();
		Assert.assertTrue(BigDecimal.valueOf(1005.393).equals(result));
	}

	@Test
	public void calc2() throws Exception {
		final WmaCalculationRunner calculator = new WmaCalculationRunner(
				INTEGER_LIST_TO_BIG_DECIMAL_LIST.apply(Arrays.asList(1000, 990, 980, 970, 960, 950, 940)));
		final BigDecimal result = calculator.call();
		Assert.assertTrue(BigDecimal.valueOf(980.0).equals(result));
	}

	@Test(expected = NullPointerException.class)
	public void calcRejectNull() {
		new WmaCalculationRunner(null);
	}

	@Test(expected = BaseException.class)
	public void calcRejectEmpoty() throws Exception {
		final WmaCalculationRunner calculator = new WmaCalculationRunner(Collections.emptyList());
		calculator.call();
	}
}
