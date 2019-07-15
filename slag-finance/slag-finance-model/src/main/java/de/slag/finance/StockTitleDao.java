package de.slag.finance;

import java.util.Optional;

import de.slag.common.Dao;
import de.slag.finance.model.StockTitle;

public interface StockTitleDao extends Dao<StockTitle> {

	default Optional<StockTitle> findBy(String isin) {
		return findAll().stream()
					.filter(stockTitle -> stockTitle.getIsin().equals(isin))
					.findAny();
	}

}
