package de.slag.finance2.deprecated;

import de.slag.finance.deprecated.FinDataPointFactory;

public interface FinAppContext {
	
	FinDataPointFactory getDataPointFactory();
	
	FinDataPointManager getDataPointManager();

}
