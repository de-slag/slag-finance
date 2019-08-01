package de.slag.finance3.ti.parse;

import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.slag.common.base.BaseException;
import de.slag.finance.model.Kpi;

public class TiParserUtilsTest {

	@Test
	public void testWma() {
		final TiParserResult tiResult = TiParserUtils.parse("WMA_38");
		Assert.assertNotNull(tiResult);
		Assert.assertThat(tiResult.getKpi(), is(Kpi.WMA));

		final Map<String, Integer> parameter = tiResult.getParameter();
		Assert.assertThat(parameter.size(), is(1));

		final String key = parameter.keySet()
				.stream()
				.findAny()
				.get();

		Assert.assertThat(key, is("VALUE"));
		Assert.assertThat(parameter.get(key), is(38));
	}

	@Test
	public void testSma() {
		final TiParserResult tiResult = TiParserUtils.parse("SMA_3");
		Assert.assertNotNull(tiResult);
		Assert.assertThat(tiResult.getKpi(), is(Kpi.SMA));

		final Map<String, Integer> parameter = tiResult.getParameter();
		Assert.assertThat(parameter.size(), is(1));

		final String key = parameter.keySet()
				.stream()
				.findAny()
				.get();

		Assert.assertThat(key, is("VALUE"));
		Assert.assertThat(parameter.get(key), is(3));
	}

	@Test(expected = NumberFormatException.class)
	public void testSmanoValidParameter() {
		TiParserUtils.parse("SMA_A");
	}

	@Test(expected = BaseException.class)
	public void testSmaNotTMoreThanTwoParts() {
		TiParserUtils.parse("SMA_3_2");
	}

	@Test(expected = BaseException.class)
	public void testNoUnderscore() {
		TiParserUtils.parse("XXX");
	}

}
