package de.slag.finance.interfaces.av.call;

import org.apache.commons.lang3.builder.Builder;

public class AvCallBuilder implements Builder<AvCall> {
	
	private String apiKey;
	
	private String symbol;
	
	public AvCallBuilder apiKey(String apiKey) {
		this.apiKey = apiKey;
		return this;
	}
	
	public AvCallBuilder symbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	@Override
	public AvCall build() {
		final AvCall avCall = new AvCall();
		
		avCall.setApiKey(apiKey);
		avCall.setSymbol(symbol);
		
		return avCall;
	}

}
