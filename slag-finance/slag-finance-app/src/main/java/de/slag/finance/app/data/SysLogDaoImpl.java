package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.api.dao.SysLogDao;
import de.slag.common.db.AbstractDao;
import de.slag.common.model.beans.SysLog;

@Repository
public class SysLogDaoImpl extends AbstractDao<SysLog> implements SysLogDao {

	@Override
	protected Class<SysLog> getPersistentType() {
		return SysLog.class;
	}

}
