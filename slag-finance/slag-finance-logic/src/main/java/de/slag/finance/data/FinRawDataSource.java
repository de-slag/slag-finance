package de.slag.finance.data;

import java.util.Collection;

import de.slag.finance.data.model.FinRawDataHolder;

public interface FinRawDataSource<T> {
	
	Collection<FinRawDataHolder<T>> findAll();

}
