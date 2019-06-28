package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.FinDataPointDao;
import de.slag.finance.model.AbstractFinDataPoint;

@Repository
public class FinDataPointDaoImpl extends AbstractDao<AbstractFinDataPoint> implements FinDataPointDao {

	@Override
	protected Class<AbstractFinDataPoint> getPersistentType() {
		return AbstractFinDataPoint.class;
	}

}
