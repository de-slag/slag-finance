package de.slag.finance3.logic;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import de.slag.common.base.SlagProperties;

public final class FinProperties {

	private static final String FIN_PREFIX = "finance";

	public static Collection<String> getAllKeys() {
		final Properties configProperties = SlagProperties.getConfigProperties();
		final Set<Object> keySet = configProperties.keySet();
		return keySet.stream()
				.filter(key -> key instanceof String)
				.map(key -> (String) key)
				.filter(key -> key.startsWith(FIN_PREFIX))
				.collect(Collectors.toList());
	}

}
