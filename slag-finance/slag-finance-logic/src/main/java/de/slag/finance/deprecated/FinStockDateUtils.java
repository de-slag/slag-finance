package de.slag.finance.deprecated;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import de.slag.common.utils.DateUtils;

public class FinStockDateUtils {

	private static final Collection<DayOfWeek> WEEKEND = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	private static Collection<Integer> CHRISTMAS_AND_YEARSEND = Arrays.asList(24, 25, 26, 31);

	public static Collection<LocalDate> determineStockDays(final LocalDate startDate, int amountOfDates) {
		if (amountOfDates == 0) {
			return Collections.emptyList();
		}
		final LocalDate currentDate = startDate.minusDays(1);
		if (!isStockDay(startDate)) {
			return determineStockDays(currentDate, amountOfDates);
		}
		final ArrayList<LocalDate> arrayList = new ArrayList<>(determineStockDays(currentDate, amountOfDates - 1));
		arrayList.add(startDate);
		return arrayList;
	}

	public static boolean isStockDay(LocalDate date) {
		final DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (WEEKEND.contains(dayOfWeek)) {
			return false;
		}

		final Month month = date.getMonth();
		final int dayOfMonth = date.getDayOfMonth();
		if (month == Month.JANUARY) {
			// NEW YEAR
			if (dayOfMonth == 1) {
				return false;
			}
		}
		if (month == Month.DECEMBER) {
			if (CHRISTMAS_AND_YEARSEND.contains(dayOfMonth)) {
				return false;
			}
		}
		final LocalDate easterSunday = DateUtils.getEasterSunday(date.getYear());
		final LocalDate goodFriday = easterSunday.minusDays(2);
		if (goodFriday.equals(date)) {
			return false;
		}
		final LocalDate easterMonday = easterSunday.plusDays(1);
		if (easterMonday.equals(date)) {
			return false;
		}
		
		final LocalDate pfingsten = easterSunday.plusDays(50);

		return true;
	}
}
