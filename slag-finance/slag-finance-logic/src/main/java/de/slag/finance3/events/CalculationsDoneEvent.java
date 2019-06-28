package de.slag.finance3.events;

import de.slag.common.base.event.InfoEvent;

public class CalculationsDoneEvent extends InfoEvent {
	
	public CalculationsDoneEvent() {
		super();
	}

	public CalculationsDoneEvent(String info) {
		super(info);
	}

}
