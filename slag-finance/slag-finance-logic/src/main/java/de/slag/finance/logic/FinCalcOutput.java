package de.slag.finance.logic;

import java.math.BigDecimal;

public interface FinCalcOutput {
	
	BigDecimal getValue();
	
	FinCalcInput getInput();

}
