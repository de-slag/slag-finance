package de.slag.fin.ti.parse;

import java.util.HashMap;
import java.util.Map;

import de.slag.finance.model.Kpi;

public class TiParserResult {

	private Kpi kpi;

	private final Map<String, Integer> parameter = new HashMap<>();

	public Kpi getKpi() {
		return kpi;
	}

	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}

	public Map<String, Integer> getParameter() {
		return parameter;
	}
	
	

}
