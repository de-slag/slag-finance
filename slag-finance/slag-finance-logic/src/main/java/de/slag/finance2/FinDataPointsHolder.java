package de.slag.finance2;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import de.slag.common.base.BaseException;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.Kpi;

public class FinDataPointsHolder {

	private Map<Key, FinDataPoint> map = new HashMap<>();

	public FinDataPoint get(String isin, LocalDate date, Kpi kpi) {
		final Key key = new Key(isin, date, kpi);
		if (!map.containsKey(key)) {
			throw new BaseException("not contains");
		}
		return map.get(key);
	}

	public boolean contains(String isin, LocalDate date, Kpi kpi, int... parameters) {
		return map.containsKey(new Key(isin, date, kpi, parameters));
	}

	public boolean contains(FinDataPoint finDataPoint) {
		return contains(finDataPoint.getIsin(), finDataPoint.getDate(), finDataPoint.getKpi(),
				finDataPoint.getParameters());
	}

	public void put(FinDataPoint finDataPoint) {
		if (contains(finDataPoint)) {
			throw new BaseException("already contains: " + finDataPoint);
		}
		map.put(key(finDataPoint), finDataPoint);
	}

	private Key key(FinDataPoint finDataPoint) {
		return new Key(finDataPoint.getIsin(), finDataPoint.getDate(), finDataPoint.getKpi());
	}

	private class Key implements Comparable<Key> {

		private String isin;

		private LocalDate date;

		private Kpi kpi;

		private int[] parameters;

		public Key(String isin, LocalDate date, Kpi kpi, int... parameters) {
			this.isin = isin;
			this.date = date;
			this.kpi = kpi;
			this.parameters = parameters;
		}

		@Override
		public int compareTo(Key o) {
			final int compareToIsin = this.isin.compareTo(o.isin);
			if (compareToIsin != 0) {
				return compareToIsin;
			}

			final int compareToDate = this.date.compareTo(o.date);
			if (compareToDate != 0) {
				return compareToDate;
			}

			final int compareToKpi = this.kpi.compareTo(o.kpi);
			if (compareToKpi != 0) {
				return compareToKpi;
			}

			int compareAttributesLengh = this.parameters.length - o.parameters.length;
			if (compareAttributesLengh != 0) {
				return compareAttributesLengh;
			}
			for (int i = 0; i < this.parameters.length; i++) {
				final int compareTo = Integer.valueOf(this.parameters[i]).compareTo(o.parameters[i]);
				if (compareTo != 0) {
					return compareTo;
				}
			}
			return 0;

		}

	}

}
