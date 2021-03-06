package de.slag.finance.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.slag.common.api.Dao;
import de.slag.common.base.BaseException;
import de.slag.finance.model.FinPrice;

public interface FinPriceDao extends Dao<FinPrice> {

	default Optional<FinPrice> loadPriceBy(String isin, Date date) {
		Collection<Long> findAllIds = findAllIds();

		final List<FinPrice> p1 = findAllIds.stream()
				.map(id -> loadById(id))
				.filter(v -> v.isPresent())
				.map(v -> v.get())
				.filter(p -> p.getDate().compareTo(date) == 0)
				.filter(p -> p.getIsin().equals(isin))
				.collect(Collectors.toList());



		if (p1.size() > 1) {
			throw new BaseException("more than one!");
		}
		return p1.stream().findAny();
	}

}
