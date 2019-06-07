package de.slag.finance.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.slag.finance.data.model.Kpi;

class FinDataPointBuilderTest {

	private static final int[] PARAMETERS = new int[0];
	private static final BigDecimal VALUE = BigDecimal.ONE;
	private static final Kpi KPI = Kpi.PRICE;
	private static final LocalDate DATE = LocalDate.of(2019, 6, 1);
	private static final String ISIN = "xxx";
	private FinDataPoint.Builder builder;

	@BeforeEach
	void setUp() throws Exception {
		builder = new FinDataPoint.Builder();
	}

	@Test
	void testIsin() {
		Assertions.assertThrows(NullPointerException.class, () -> builder.isin(null));
		builder.isin(ISIN);
	}

	@Test
	void testDate() {
		Assertions.assertThrows(NullPointerException.class, () -> builder.date(null));
		builder.date(DATE);
	}

	@Test
	void testKpi() {
		Assertions.assertThrows(NullPointerException.class, () -> builder.kpi(null));
		builder.kpi(KPI);
	}

	@Test
	void testValue() {
		Assertions.assertThrows(NullPointerException.class, () -> builder.kpi(null));
		builder.value(VALUE);
	}

	@Test
	void testParameters() {
		builder.parameters(null);
		builder.parameters(PARAMETERS);
	}

	@Test
	void testBuild() {
		final FinDataPoint dp = builder
			.isin("x")
			.date(DATE)
			.kpi(KPI)
			.value(VALUE)
			.build();
		Assert.assertNotNull(dp);
	}

	@Test
	void testBuildFail() {
		Assertions.assertThrows(NullPointerException.class, () -> builder.build());
	}

}
