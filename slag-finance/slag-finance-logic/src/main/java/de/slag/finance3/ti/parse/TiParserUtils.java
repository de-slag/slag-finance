package de.slag.finance3.ti.parse;

import java.util.HashMap;
import java.util.Map;

import de.slag.common.base.BaseException;
import de.slag.finance.model.Kpi;
import de.slag.finance3.ti.Sma;
import de.slag.finance3.ti.Wma;

public class TiParserUtils {

	public static TiParserResult parse(String tiString) {
		final String[] tiStringSplit = tiString.split("_");
		if (tiStringSplit.length < 2) {
			throw new BaseException("contains no '_': " + tiString);
		}

		final String kpiString = tiStringSplit[0];
		final Kpi kpi = Kpi.valueOf(kpiString.toUpperCase());
		final Map<Enum<?>, Integer> parameterMap = new HashMap<>();

		switch (kpi) {
		case SMA:
			if (tiStringSplit.length != 2) {
				throw new BaseException("no valid tiString for SMA:" + tiString);
			}
			final Integer paramSma1 = Integer.valueOf(tiStringSplit[1]);
			parameterMap.put(Sma.Result.VALUE, paramSma1);
			break;

		case WMA:
			if (tiStringSplit.length != 2) {
				throw new BaseException("no valid tiString for WMA:" + tiString);
			}
			final Integer paramWma1 = Integer.valueOf(tiStringSplit[1]);
			parameterMap.put(Wma.Result.VALUE, paramWma1);
			break;

		default:
			throw new UnsupportedOperationException(tiString);
		}

		final TiParserResult tiParserResult = new TiParserResult();
		tiParserResult.setKpi(kpi);
		parameterMap.keySet()
				.forEach(key -> tiParserResult.getParameter()
						.put(key.name(), parameterMap.get(key)));
		return tiParserResult;
	}

}
