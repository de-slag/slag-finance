package de.slag.finance2;

import de.slag.finance.model.FinDataPointFactory;

public interface FinAppContext {
	
	FinDataPointFactory getDataPointFactory();
	
	FinDataPointManager getDataPointManager();

}
