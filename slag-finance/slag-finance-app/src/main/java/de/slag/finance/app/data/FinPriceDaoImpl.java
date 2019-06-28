package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.FinPriceDao;
import de.slag.finance.model.FinPrice;

@Repository
public class FinPriceDaoImpl extends AbstractDao<FinPrice> implements FinPriceDao {

	@Override
	protected Class<FinPrice> getPersistentType() {
		return FinPrice.class;
	}

}
