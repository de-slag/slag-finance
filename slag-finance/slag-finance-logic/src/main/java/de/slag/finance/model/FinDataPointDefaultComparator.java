package de.slag.finance.model;

import java.util.Comparator;

public class FinDataPointDefaultComparator implements Comparator<FinDataPoint> {

	@Override
	// TODO nullsafe
	public int compare(FinDataPoint o1, FinDataPoint o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 != null && o2 == null) {
			return 1;
		}
		if (o1 == null && o2 != null) {
			return -1;
		}
		final int dateCompare = o1.getDate().compareTo(o2.getDate());
		if (dateCompare != 0) {
			return dateCompare;
		}
		final int isinCompare = o1.getIsin().compareTo(o2.getIsin());
		if (isinCompare != 0) {
			return isinCompare;
		}
		return o1.getKpi().compareTo(o2.getKpi());

	}

}
