package de.slag.fin.ti.api;

import java.time.LocalDate;

public interface TiDataPoint {

	String getIsin();

	LocalDate getDate();

	TiKeyValue[] getKeyValues();
}
