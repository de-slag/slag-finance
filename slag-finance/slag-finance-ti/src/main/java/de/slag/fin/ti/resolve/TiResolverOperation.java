package de.slag.fin.ti.resolve;

import java.math.BigDecimal;

public class TiResolverOperation {

	private TiResolverIndicator first;

	private TiResolverIndicator second;

	private final TiResolverOperator operator = TiResolverOperator.SUBTRACT;

	public BigDecimal getValue() {
		return first.getValue()
				.subtract(second.getValue());
	}

	public TiResolverIndicator getFirst() {
		return first;
	}

	public void setFirst(TiResolverIndicator first) {
		this.first = first;
	}

	public TiResolverIndicator getSecond() {
		return second;
	}

	public void setSecond(TiResolverIndicator second) {
		this.second = second;
	}

}
