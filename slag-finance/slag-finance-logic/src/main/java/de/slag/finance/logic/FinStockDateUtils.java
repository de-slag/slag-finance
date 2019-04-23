package de.slag.finance.logic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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

	private static boolean isStockDay(LocalDate date) {
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

		return true;
	}
}
