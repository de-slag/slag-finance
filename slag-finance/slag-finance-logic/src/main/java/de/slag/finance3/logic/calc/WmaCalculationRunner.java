package de.slag.finance3.logic.calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import de.slag.common.base.BaseException;

public class WmaCalculationRunner implements Callable<BigDecimal> {

	private final MathContext MC = new MathContext(7, RoundingMode.HALF_UP);

	private List<BigDecimal> inputDatas = new ArrayList<BigDecimal>();

	public WmaCalculationRunner(List<BigDecimal> inputDatas) {
		this.inputDatas.addAll(Objects.requireNonNull(inputDatas, "inputDatas null"));
	}

	@Override
	public BigDecimal call() throws Exception {
		
		if(inputDatas.isEmpty()) {
			throw new BaseException("input datas empty");
		}
		// count of values
		final int n = inputDatas.size();

		BigDecimal sumN = BigDecimal.ZERO;
		for (int i = 1; i <= n; i++) {
			sumN = sumN.add(BigDecimal.valueOf(i));
		}

		BigDecimal interimResult = BigDecimal.ZERO;

		final AtomicInteger ai = new AtomicInteger(n);
		for (BigDecimal data : inputDatas) {
			interimResult = interimResult.add(data.multiply(BigDecimal.valueOf(ai.getAndDecrement())));
		}

		return interimResult.divide(sumN, MC);
	}

}