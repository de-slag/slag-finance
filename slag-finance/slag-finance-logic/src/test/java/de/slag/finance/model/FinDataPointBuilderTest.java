package de.slag.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slag.finance.deprecated.FinDataPoint;

public class FinDataPointBuilderTest {

	private static final int[] PARAMETERS = new int[0];
	private static final BigDecimal VALUE = BigDecimal.ONE;
	private static final Kpi KPI = Kpi.PRICE;
	private static final LocalDate DATE = LocalDate.of(2019, 6, 1);
	private static final String ISIN = "xxx";
	private FinDataPoint.Builder builder;

	@Before
	public void setUp() throws Exception {
		builder = new FinDataPoint.Builder();
	}

	@Test(expected = NullPointerException.class)
	public void testIsin() {
		builder.isin(null);
		builder.isin(ISIN);
	}

	@Test(expected = NullPointerException.class)
	public void testDate() {
		builder.date(null);
		builder.date(DATE);
	}

	@Test(expected = NullPointerException.class)
	public void testKpi() {
		builder.kpi(null);
		builder.kpi(KPI);
	}

	@Test(expected = NullPointerException.class)
	public void testValue() {
		builder.kpi(null);
		builder.value(VALUE);
	}

	@Test
	public void testParameters() {
		builder.parameters(null);
		builder.parameters(PARAMETERS);
	}

	@Test
	public void testBuild() {
		final FinDataPoint dp = builder.isin("x")
				.date(DATE)
				.kpi(KPI)
				.value(VALUE)
				.build();
		Assert.assertNotNull(dp);
	}

	@Test(expected = NullPointerException.class)
	public void testBuildFail() {
		builder.build();
	}

}
