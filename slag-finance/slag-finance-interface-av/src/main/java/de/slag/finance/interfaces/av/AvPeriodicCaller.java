package de.slag.finance.interfaces.av;

import java.util.Collection;
import java.util.concurrent.Callable;

import de.slag.common.base.BaseException;
import de.slag.common.utils.SleepUtils;

public class AvPeriodicCaller {

	private boolean interrupted = false;
	
	public void callPeriodicly(Collection<Callable> callables) {
		callPeriodicly(callables, 13);
	}

	public void callPeriodicly(Collection<Callable> callables, int secondsDelay) {
		for (Callable<?> callable : callables) {
			if (interrupted) {
				break;
			}
			try {
				callable.call();
			} catch (Exception e) {
				throw new BaseException(e);
			}
			SleepUtils.sleepFor(secondsDelay * 1000);
		}
	}

	public void interrupt() {
		interrupted = false;
	}

}
