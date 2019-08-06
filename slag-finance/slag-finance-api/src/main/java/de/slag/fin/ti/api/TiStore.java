package de.slag.fin.ti.api;

import java.time.LocalDate;

public interface TiStore {

	<T extends TiDataPoint> T get(Class<T> type, String isin, LocalDate date, TiKeyValue... keyValues);

}
