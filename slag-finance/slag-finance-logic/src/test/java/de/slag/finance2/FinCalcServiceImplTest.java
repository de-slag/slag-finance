package de.slag.finance2;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import de.slag.finance.model.Kpi;

@ExtendWith(MockitoExtension.class)
class FinCalcServiceImplTest {
	
	@Mock
	private FinAppContext FinAppContext;
	
	@Spy
	private FinCalcService finCalcService = new FinCalcServiceImpl();

	@Test
	void testCalc() {
		Assert.assertNotNull(finCalcService.calc("846900", LocalDate.of(2019, 5, 5), Kpi.SMA, 5));
	}

}
