package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.finance.IsinWknDao;
import de.slag.finance.model.IsinWkn;

@Repository
public class IsinWknDaoImpl extends AbstractDao<IsinWkn> implements IsinWknDao {

	@Override
	protected Class<IsinWkn> getPersistentType() {
		return IsinWkn.class;
	}

}
