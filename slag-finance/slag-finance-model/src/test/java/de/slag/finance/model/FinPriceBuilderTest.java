package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

class FinPriceBuilderTest {

	private static final BigDecimal VALUE = BigDecimal.ONE;
	private static final String ISIN = "abc";
	private static final Date DATE = new Date(0);

	@Test
	void testSuccess() {
		Assert.assertNotNull(new FinPrice.Builder().isin(ISIN).date(DATE).close(VALUE).build());

	}

	@Test(expected = NoSuchElementException.class)
	void testDate() {
		new FinPrice.Builder().isin(ISIN).close(VALUE).build();
	}

	@Test(expected = NoSuchElementException.class)
	void testValue() {
		new FinPrice.Builder().isin(ISIN).date(DATE).build();
	}

	@Test(expected = NoSuchElementException.class)
	void testIsin() {
		new FinPrice.Builder().close(VALUE).date(DATE).build();
	}

}
