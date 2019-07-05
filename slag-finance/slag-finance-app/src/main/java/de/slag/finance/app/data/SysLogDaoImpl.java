package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.db.AbstractDao;
import de.slag.common.model.beans.SysLog;
import de.slag.common.model.dao.SysLogDao;

@Repository
public class SysLogDaoImpl extends AbstractDao<SysLog> implements SysLogDao {

	@Override
	protected Class<SysLog> getPersistentType() {
		return SysLog.class;
	}

}
