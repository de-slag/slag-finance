package de.slag.finance3.parsing;

import java.util.HashMap;
import java.util.Map;

import de.slag.common.base.BaseException;
import de.slag.finance.model.Kpi;

public class KpiParserUtils {

	public static KpiParserResult parse(String s) {
		final String[] split = s.split("_");

		if (split.length != 2) {
			throw new BaseException("up to now only supported 'KPI_nn'");
		}

		final Kpi kpi = Kpi.valueOf(split[0].toUpperCase());

		final Map<String, Integer> parameters = new HashMap<String, Integer>();

		parameters.put("value", Integer.valueOf(split[1]));
		
		return createResult(kpi, parameters);

	}

	private static KpiParserResult createResult(Kpi kpi, Map<String, Integer> parameters) {
		return new KpiParserResult() {

			@Override
			public Map<String, Integer> getParameter() {
				return parameters;
			}

			@Override
			public Kpi getKpi() {
				return kpi;
			}
		};
	}

}
