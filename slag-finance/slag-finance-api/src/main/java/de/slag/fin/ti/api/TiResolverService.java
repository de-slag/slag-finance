package de.slag.fin.ti.api;

import java.math.BigDecimal;

public interface TiResolverService {
	
	BigDecimal resolve(String stringToResolve, TiResolverParameter parameter);

}
