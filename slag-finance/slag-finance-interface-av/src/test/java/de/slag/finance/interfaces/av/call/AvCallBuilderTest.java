package de.slag.finance.interfaces.av.call;

import java.util.Collection;

import org.junit.Test;

import de.slag.finance.interfaces.av.AvRawDataPoint;

public class AvCallBuilderTest {

	@Test
	public void testBuild() throws Exception {
		final AvCall build = new AvCallBuilder().apiKey("SEOG69AIA6X9PGR2").symbol("^GDAXI").build();
		final Collection<AvRawDataPoint> call = build.call();
		call.forEach(System.out::println);
	}

}
