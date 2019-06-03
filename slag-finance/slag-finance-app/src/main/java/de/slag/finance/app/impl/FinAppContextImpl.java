package de.slag.finance.app.impl;

import org.springframework.stereotype.Service;

import de.slag.common.context.SlagContext;
import de.slag.finance.model.FinDataPointFactory;
import de.slag.finance2.FinAppContext;
import de.slag.finance2.FinDataPointManager;

@Service
public class FinAppContextImpl implements FinAppContext {

	private FinDataPointManager finDataPointManager = SlagContext.getBean(FinDataPointManager.class);

	@Override
	public FinDataPointFactory getDataPointFactory() {
		return new FinDataPointFactoryImpl();
	}

	@Override
	public FinDataPointManager getDataPointManager() {
		return finDataPointManager;
	}

}
