package de.slag.finance.deprecated;

import java.util.Collection;

public interface FinRawDataSource<T> {
	
	Collection<T> findAll();

}
