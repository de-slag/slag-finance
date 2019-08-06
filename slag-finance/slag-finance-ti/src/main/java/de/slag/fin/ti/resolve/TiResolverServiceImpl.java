package de.slag.fin.ti.resolve;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.fin.ti.api.TiResolverParameter;
import de.slag.fin.ti.api.TiResolverService;
import de.slag.fin.ti.api.TiStore;
import de.slag.finance.model.Kpi;

@Service
public class TiResolverServiceImpl implements TiResolverService {

	@Resource
	private TiStore tiStore;

	@Override
	public BigDecimal resolve(String stringToResolve, TiResolverParameter parameter) {
		Collection<String> separated = Arrays.asList(stringToResolve.split(";"));

		Collection<TiResolverOperation> operations = separated.stream()
				.flatMap(opString -> operationOf(opString, parameter).stream())
				.collect(Collectors.toList());

		Collection<BigDecimal> results = operations.stream()
				.map(op -> op.getValue())
				.collect(Collectors.toList());

		BigDecimal result = addAll(results);
		return result.divide(BigDecimal.valueOf(operations.size()));
	}

	/**
	 * 
	 * @param parameter
	 * @param String    like "KPI_3-KPI_7"
	 * @return
	 */
	private Collection<TiResolverOperation> operationOf(String s, TiResolverParameter parameter) {
		final Collection<TiResolverOperation> result = new ArrayList<>();
		if (s.contains("-")) {
			// TODO assert only one "-"
			String[] split = s.split("-");

			TiResolverIndicator first = indicatorOf(split[0], parameter);
			TiResolverIndicator second = indicatorOf(split[1], parameter);

			TiResolverOperation operation = new TiResolverOperation();
			operation.setFirst(first);
			operation.setSecond(second);
			result.add(operation);

		} else if (s.contains(":")) {
			// TODO assert only one ":"

			String[] split = s.split(":");

			TiResolverIndicator first = indicatorOf(split[0], parameter);
			Double valueOf = Double.valueOf(split[1]);
			TiResolverOperation op = new TiResolverOperation();
			op.setFirst(first);
			op.setSecond(indicatorOf(valueOf));
			result.add(op);

			TiResolverOperation op2 = new TiResolverOperation();
			op.setFirst(indicatorOf(100 - valueOf));
			op.setSecond(first);
			result.add(op2);

		} else {
			throw new UnsupportedOperationException("not supported: " + s);
		}
		return result;
	}

	private TiResolverIndicator indicatorOf(String string, TiResolverParameter parameter) {
		String[] split = string.split("_"); // TODO assert this

		Kpi kpi = Kpi.valueOf(split[0].toUpperCase());
		switch (kpi) {
		case PRICE:
			//tiStore.get(Price.class, parameter.getIsin(), parameter.getDate());
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
		return null;
	}

	private TiResolverIndicator indicatorOf(Double double1) {
		return new TiResolverIndicator() {

			final Double d = double1;

			@Override
			public BigDecimal getValue() {
				return BigDecimal.valueOf(d);
			}
		};
	}

	private BigDecimal addAll(Collection<BigDecimal> results) {
		BigDecimal result = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : results) {
			result = result.add(bigDecimal);
		}
		return result;
	}

}
