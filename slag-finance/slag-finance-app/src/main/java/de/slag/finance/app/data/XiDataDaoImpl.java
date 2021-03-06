package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.api.dao.XiDataDao;
import de.slag.common.db.AbstractDao;
import de.slag.common.model.beans.XiData;

@Repository
public class XiDataDaoImpl extends AbstractDao<XiData> implements XiDataDao {

	@Override
	protected Class<XiData> getPersistentType() {
		return XiData.class;
	}

}
