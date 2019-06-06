package de.slag.finance3.logic;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import de.slag.finance.model.FinDataPoint;
import de.slag.finance3.logic.interfaces.FinDataPointService;

@ExtendWith(MockitoExtension.class)
class FinServiceImplTest {
	
	@Mock
	FinDataPointService finDataPointService;

	@InjectMocks
	FinService finService = new FinServiceImpl();

	@BeforeEach
	void setUp() throws Exception {
		Mockito.when(finDataPointService.getAll("x")).then(new Answer<Collection<FinDataPoint>>() {

			@Override
			public Collection<FinDataPoint> answer(InvocationOnMock invocation) throws Throwable {
				return Collections.emptyList();
			}
		});
	}

	@Test
	void testCalc() {
		Assert.assertNotNull(finDataPointService.getAll("x"));
		Assert.assertNull(finDataPointService.getAll("y"));
	}

}
