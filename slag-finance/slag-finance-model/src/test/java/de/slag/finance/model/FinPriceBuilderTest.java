package de.slag.finance.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FinPriceBuilderTest {

	private static final BigDecimal VALUE = BigDecimal.ONE;
	private static final String ISIN = "abc";
	private static final Date DATE = new Date(0);

	@Test
	void testSuccess() {
		assertNotNull(new FinPrice.Builder()
				.isin(ISIN)
				.date(DATE)
				.value(VALUE)
				.build());

	}

	@Test
	void testDate() {
		Assertions.assertThrows(NoSuchElementException.class, () -> new FinPrice.Builder()
				.isin(ISIN)
				.value(VALUE)
				.build());
	}

	@Test
	void testValue() {
		Assertions.assertThrows(NoSuchElementException.class, () -> new FinPrice.Builder()
				.isin(ISIN)
				.date(DATE)
				.build());
	}

	@Test
	void testIsin() {
		Assertions.assertThrows(NoSuchElementException.class, () -> new FinPrice.Builder()
				.value(VALUE)
				.date(DATE)
				.build());
	}

}
