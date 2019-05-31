package de.slag.finance.data;

import java.util.Collection;

public interface FinRawDataSource<T> {
	
	Collection<T> findAll();

}
