package de.slag.fin.ti;

import java.time.LocalDate;
import java.util.Arrays;

import de.slag.fin.ti.api.TiKeyValue;

public class TiStoreHash {

	private final Class<?> type;
	private final String isin;
	private final LocalDate date;
	private final TiKeyValue[] keyValues;

	public static TiStoreHash of(Class<?> type, String isin, LocalDate date, TiKeyValue... keyValues) {
		return new TiStoreHash(type, isin, date, keyValues);
	}

	private TiStoreHash(Class<?> type, String isin, LocalDate date, TiKeyValue... keyValues) {
		super();
		this.type = type;
		this.isin = isin;
		this.date = date;
		this.keyValues = keyValues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((isin == null) ? 0 : isin.hashCode());
		result = prime * result + Arrays.hashCode(keyValues);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TiStoreHash other = (TiStoreHash) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (isin == null) {
			if (other.isin != null)
				return false;
		} else if (!isin.equals(other.isin))
			return false;
		if (!Arrays.equals(keyValues, other.keyValues))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
