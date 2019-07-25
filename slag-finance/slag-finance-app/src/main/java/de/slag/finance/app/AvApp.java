package de.slag.finance.app;

import java.util.Collection;

import de.slag.common.base.BaseException;
import de.slag.finance.interfaces.av.AvRawDataPoint;
import de.slag.finance.interfaces.av.call.AvCall;
import de.slag.finance.interfaces.av.call.AvCallBuilder;

public class AvApp {

	public static void main(String[] args) {
		final String key = "SEOG69AIA6X9PGR2";
		final String symbol = "^GDAXI";
		
		final AvCall build = new AvCallBuilder().apiKey(key).symbol(symbol).build();
		Collection<AvRawDataPoint> call;
		try {
			call = build.call();
		} catch (Exception e) {
			throw new BaseException(e);
		}
		call.forEach(System.out::println);
	}

}
