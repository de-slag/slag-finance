package de.slag.fin.ti.resolve;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.slag.fin.ti.api.TiStore;
import de.slag.fin.ti.resolve.TiResolverServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TiResolverServiceImplTest {

	@InjectMocks
	TiResolverServiceImpl tiResolverServiceImpl = new TiResolverServiceImpl();

	@Mock
	TiStore tiStore;

	@Before
	public void init() {
		
	}

	@Test
	public void testResolve() {

	}

}
