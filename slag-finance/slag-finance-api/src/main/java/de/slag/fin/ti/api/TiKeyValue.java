package de.slag.fin.ti.api;

public interface TiKeyValue {

	String getKey();

	Integer getValue();

	default TiKeyValue of(String key, Integer value) {
		return new TiKeyValue() {
			
			@Override
			public Integer getValue() {
				return value;
			}
			
			@Override
			public String getKey() {
				return key;
			}
		};
	}

}
