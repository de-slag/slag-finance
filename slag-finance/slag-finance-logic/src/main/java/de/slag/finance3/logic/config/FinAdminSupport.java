package de.slag.finance3.logic.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import de.slag.common.base.BaseException;
import de.slag.common.base.SlagProperties;

public final class FinAdminSupport {

	private static final String FIN_PREFIX = "finance";

	private static final Map<String, String> MAP = new HashMap<>();

	static {
		final Properties configProperties = SlagProperties.getConfigProperties();
		final Set<Object> keySet = configProperties.keySet();
		keySet.stream().filter(key -> key instanceof String).map(key -> (String) key)
				.filter(key -> key.startsWith(FIN_PREFIX))
				.forEach(key -> MAP.put(key, configProperties.getProperty(key)));
	}

	public static Map<String, String> getAll() {
		final HashMap<String, String> hashMap = new HashMap<>();
		hashMap.putAll(MAP);
		return hashMap;
	}

	public static Map<String, String> getAll(String startWith) {
		final List<String> keys = MAP.keySet().stream().filter(key -> key.startsWith(startWith))
				.collect(Collectors.toList());
		final HashMap<String, String> subMap = new HashMap<>();
		keys.forEach(key -> subMap.put(key, MAP.get(key)));
		return subMap;
	}

	public static String getSafe(String key) {
		return get(key).orElseThrow(() -> new BaseException("not administrated: " + key));
	}

	public static Optional<String> get(String key) {
		Objects.requireNonNull(key, "key must not be null");
		if (!MAP.containsKey(key)) {
			return Optional.empty();
		}
		return Optional.of(MAP.get(key));
	}
}
