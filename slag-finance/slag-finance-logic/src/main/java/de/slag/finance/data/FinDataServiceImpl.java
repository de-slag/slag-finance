package de.slag.finance.data;

import org.springframework.stereotype.Service;

@Service
public class FinDataServiceImpl implements FinDataService {

	public FinDataServiceImpl() {
		init();
	}

	public void init() {
		
	}

	public <T> FinDataSource<T> getDataSource(Class<T> c) {
		return null;
	}

}
