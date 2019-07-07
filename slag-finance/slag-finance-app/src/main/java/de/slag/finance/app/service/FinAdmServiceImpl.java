package de.slag.finance.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.slag.common.base.AdmService;
import de.slag.common.base.SlagProperties;

@Service
public class FinAdmServiceImpl  implements AdmService {
	
	private static final Map<String, String> MAP = new HashMap<>();

	static {
		final Properties configProperties = SlagProperties.getConfigProperties();
		final Set<Object> keySet = configProperties.keySet();
		keySet.stream()
			.filter(key -> key instanceof String)
			.map(key -> (String) key)
			.forEach(key -> MAP.put(key, configProperties.getProperty(key)));
	}

	@Override
	public Optional<String> get(String key) {
		if(!MAP.containsKey(key)) {
			return Optional.empty();
		}
		return Optional.of(MAP.get(key));
	}

}
