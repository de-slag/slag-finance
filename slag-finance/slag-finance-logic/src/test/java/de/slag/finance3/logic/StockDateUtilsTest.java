package de.slag.finance3.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.slag.finance.logic.FinStockDateUtils;

class StockDateUtilsTest {

	private static final Collection<LocalDate> EXPECTED = Arrays.asList(LocalDate.of(2019, 1, 15),
			LocalDate.of(2019, 1, 14), LocalDate.of(2019, 1, 11), LocalDate.of(2019, 1, 10), LocalDate.of(2019, 1, 9),
			LocalDate.of(2019, 1, 8), LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 3),
			LocalDate.of(2019, 1, 2), LocalDate.of(2018, 12, 28), LocalDate.of(2018, 12, 27),
			LocalDate.of(2018, 12, 21), LocalDate.of(2018, 12, 20), LocalDate.of(2018, 12, 19),
			LocalDate.of(2018, 12, 18), LocalDate.of(2018, 12, 17), LocalDate.of(2018, 12, 14),
			LocalDate.of(2018, 12, 13), LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 11),
			LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 7), LocalDate.of(2018, 12, 6), LocalDate.of(2018, 12, 5),
			LocalDate.of(2018, 12, 4), LocalDate.of(2018, 12, 3));

	@Test
	public void testdetermineStockDays() {
		final Set<LocalDate> set = new TreeSet<>();
		set.addAll(FinStockDateUtils.determineStockDays(LocalDate.of(2019, 1, 15), 27));
		Assert.assertTrue(27 == EXPECTED.size());

		Assert.assertTrue(set.size() == EXPECTED.size());
		set.forEach(date -> Assert.assertTrue("result not in expected: " + date, EXPECTED.contains(date)));
	}

	@Test
	public void testEasterWeeks() {
		final Collection<LocalDate> stockDays = FinStockDateUtils.determineStockDays(LocalDate.of(2019, 4, 26),
				10);
		Assert.assertTrue(Integer.valueOf(10)
				.equals(stockDays.size()));

		final LocalDate goodFriday = LocalDate.of(2019, 4, 19);
		Assert.assertTrue(!stockDays.contains(goodFriday));
		
		final LocalDate easterMonday = LocalDate.of(2019, 4, 22);
		Assert.assertTrue(!stockDays.contains(easterMonday));
	}
	
	@Test
	public void testPfingsten() {
		final LocalDate pfingstmontag = LocalDate.of(2019, 6, 10);
		final Collection<LocalDate> stockDays = FinStockDateUtils.determineStockDays(LocalDate.of(2019, 6, 14), 10);
		
		Assert.assertTrue(!stockDays.contains(pfingstmontag));
		
		
	}

}
