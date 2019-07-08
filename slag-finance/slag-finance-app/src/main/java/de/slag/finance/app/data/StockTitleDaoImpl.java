package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.StockTitleDao;
import de.slag.finance.model.StockTitle;

@Repository
public class StockTitleDaoImpl extends AbstractDao<StockTitle> implements StockTitleDao {

	@Override
	protected Class<StockTitle> getPersistentType() {
		return StockTitle.class;
	}

}
