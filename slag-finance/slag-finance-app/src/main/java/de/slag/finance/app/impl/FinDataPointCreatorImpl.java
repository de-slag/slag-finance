package de.slag.finance.app.impl;

import org.springframework.stereotype.Service;

import de.slag.finance.logic.service.FinDataPointCreator;
import de.slag.finance.model.FinDataPoint;

@Service
public class FinDataPointCreatorImpl implements FinDataPointCreator {

	@Override
	public FinDataPoint get() {
		return new FinPersistDataPoint();
	}

}
