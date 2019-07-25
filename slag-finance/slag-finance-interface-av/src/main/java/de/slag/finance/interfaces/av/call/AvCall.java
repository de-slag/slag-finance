package de.slag.finance.interfaces.av.call;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.output.timeseries.Daily;
import org.patriques.output.timeseries.data.StockData;

import de.slag.finance.interfaces.av.AvRawDataPoint;

public class AvCall implements Callable<Collection<AvRawDataPoint>> {

	private String apiKey;

	private String symbol;

	@Override
	public Collection<AvRawDataPoint> call() throws Exception {
		int timeout = 3000;
		AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

		// IntraDay response = stockTimeSeries.intraDay(symbol, Interval.ONE_MIN,
		// OutputSize.COMPACT);
		final Daily response = stockTimeSeries.daily(symbol);

		Map<String, String> metaData = response.getMetaData();
		System.out.println("Information: " + metaData.get("1. Information"));
		System.out.println("Stock: " + metaData.get("2. Symbol"));

		List<StockData> stockData = response.getStockData();

		return stockData.stream().map(stock -> new AvRawDataPoint() {
			@Override
			public String toString() {
				return stock.getDateTime() + " " + stock.getClose();
			}
		}).collect(Collectors.toList());

	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
