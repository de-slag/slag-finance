package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.common.model.beans.SystemLog;
import de.slag.common.model.beans.SystemLogDao;

@Repository
public class SystemLogDaoImpl extends AbstractDao<SystemLog> implements SystemLogDao {

	@Override
	protected Class<SystemLog> getPersistentType() {
		return SystemLog.class;
	}

}
