package de.slag.finance.interfaces.av;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import de.slag.common.base.SlagProperties;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.interfaces.av.call.AvCall;
import de.slag.finance.interfaces.av.call.AvCallBuilder;

public class AvPeriodicCallerTest {

	Collection<Callable> callables = new ArrayList<>();

	@Before
	public void setUp() {
		String key = SlagProperties.get(AvailableProperties.FINANCE_INTERFACE_AV_KEY);

		for (int i = 0; i < 10; i++) {
			AvCall build = new AvCallBuilder().apiKey(key)
					.symbol("^GDAXI")
					.build();
			callables.add(new Callable<String>() {

				@Override
				public String call() throws Exception {
					out(build.call());
					return null;
				}
			});
		}
	}

	@Test
	public void test() {
		final AvPeriodicCaller avPeriodicCaller = new AvPeriodicCaller();
		avPeriodicCaller.callPeriodicly(callables);
	}

	void out(Object o) {
		System.out.println(o);
	}

}
