package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.FinSmaDao;
import de.slag.finance.model.FinSma;

@Repository
public class FinSmaDaoImpl extends AbstractDao<FinSma> implements FinSmaDao {

	@Override
	protected Class<FinSma> getPersistentType() {
		return FinSma.class;
	}

}
