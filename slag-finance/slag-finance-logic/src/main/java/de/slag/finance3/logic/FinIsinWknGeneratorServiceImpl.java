package de.slag.finance3.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.finance.model.IsinWkn;

@Service
public class FinIsinWknGeneratorServiceImpl implements FinIsinWknGeneratorService {

	private static final Log LOG = LogFactory.getLog(FinIsinWknGeneratorServiceImpl.class);

	private static final Map<String, String> ISIN_WKN_MAP = new HashMap<>();

	static {
		ISIN_WKN_MAP.put("DE0008469008", "846900");
	}

	@Override
	public Collection<IsinWkn> generate() {
		LOG.info(String.format("generate '%s'...", ISIN_WKN_MAP.keySet()));
		return ISIN_WKN_MAP.keySet()
				.stream()
				.map(isin -> new IsinWkn.Builder().isin(isin)
						.wkn(ISIN_WKN_MAP.get(isin))
						.build())
				.collect(Collectors.toList());
	}

}
