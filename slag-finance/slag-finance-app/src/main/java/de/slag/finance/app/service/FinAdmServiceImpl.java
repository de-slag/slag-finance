package de.slag.finance.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.slag.common.base.SlagProperties;
import de.slag.common.base.adm.AbstractAdmServiceImpl;
import de.slag.common.base.adm.AdmService;

@Service
public class FinAdmServiceImpl extends AbstractAdmServiceImpl implements AdmService {

	public FinAdmServiceImpl() {
		super(() -> {
			final Properties configProperties = SlagProperties.getConfigProperties();
			final Set<Object> keySet = configProperties.keySet();
			final Map<String, String> map = new HashMap<>();
			keySet.stream().filter(key -> key instanceof String).map(key -> (String) key)
					.forEach(key -> map.put(key, configProperties.getProperty(key)));

			return map;
		});
	}

}
