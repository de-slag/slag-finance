package de.slag.finance;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.slag.common.Dao;
import de.slag.common.base.BaseException;
import de.slag.finance.model.FinPrice;

public interface FinPriceDao extends Dao<FinPrice> {
	
	default Optional<FinPrice> loadPriceBy(String isin, Date date) {
		Collection<Long> findAllIds = findAllIds();
		final List<FinPrice> prices = findAllIds.stream()
				.map(id -> loadById(id))
				.filter(v -> v.isPresent())
				.map(v -> v.get())
				.filter(v -> v instanceof FinPrice)
				.map(v -> (FinPrice) v)
				.filter(p -> p.getDate().equals(date))
				.filter(p -> p.getIsin().equals(isin))
				.collect(Collectors.toList());
		if (prices.size() > 1) {
			throw new BaseException("more than one!");
		}
		return prices.stream().findAny();
	}

}
