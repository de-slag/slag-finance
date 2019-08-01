package de.slag.finance3;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.slag.finance.model.Kpi;
import de.slag.finance3.parsing.KpiParserResult;
import de.slag.finance3.parsing.KpiParserUtils;

public class KpiParserUtilsTest {

	@Test
	public void testParse() {
		final KpiParserResult result = KpiParserUtils.parse("SMA_38");
		Assert.assertThat(result.getKpi(), Matchers.is(Kpi.SMA));
		Assert.assertThat(result.getParameter().get("value"), Matchers.is(38));
	}

}
