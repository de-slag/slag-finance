package de.slag.finance.data;

import java.util.Collection;

public interface FinDataSource<T> extends FinRawDataSource<T> {

	void save(T t);
	
	T loadById(Long id);
	
	Collection<Long> findAllIds();

}
