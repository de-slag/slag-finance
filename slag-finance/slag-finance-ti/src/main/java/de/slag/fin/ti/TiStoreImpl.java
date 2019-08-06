package de.slag.fin.ti;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.slag.fin.ti.api.TiDataPoint;
import de.slag.fin.ti.api.TiKeyValue;
import de.slag.fin.ti.api.TiStore;

@Component
public class TiStoreImpl implements TiStore {

	private Map<TiStoreHash, TiDataPoint> cache = new HashMap<>();

	@Override
	public <T extends TiDataPoint> T get(Class<T> type, String isin, LocalDate date, TiKeyValue... keyValues) {
		return type.cast(get0(type, isin, date, keyValues));
	}

	private Object get0(Class<?> type, String isin, LocalDate date, TiKeyValue... keyValues) {
		final TiStoreHash hash = TiStoreHash.of(type, isin, date, keyValues);
		if (cache.containsKey(hash)) {
			return cache.get(hash);
		}

		throw new UnsupportedOperationException(String.format("%s, %s, %s, %s", type, isin, date, keyValues));
	}

	public void put(TiDataPoint dataPoint) {
		cache.putIfAbsent(TiStoreHash.of(dataPoint.getClass(), dataPoint.getIsin(), dataPoint.getDate(),
				dataPoint.getKeyValues()), dataPoint);
	}
}
