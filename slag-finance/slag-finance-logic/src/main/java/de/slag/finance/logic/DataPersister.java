package de.slag.finance.logic;

import java.util.Collection;

public interface DataPersister<T> {

	T loadById(Long id);
	
	void save(T t);
	
	Collection<Long> findAllIds();

}
