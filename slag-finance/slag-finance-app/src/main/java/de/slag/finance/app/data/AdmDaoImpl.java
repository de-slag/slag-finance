package de.slag.finance.app.data;

import org.springframework.stereotype.Repository;

import de.slag.common.api.Dao;
import de.slag.common.db.AbstractDao;
import de.slag.common.model.beans.AdmParameter;
@Repository
public class AdmDaoImpl extends AbstractDao<AdmParameter> implements Dao<AdmParameter>{

	@Override
	protected Class<AdmParameter> getPersistentType() {
		return AdmParameter.class;
	}

}
