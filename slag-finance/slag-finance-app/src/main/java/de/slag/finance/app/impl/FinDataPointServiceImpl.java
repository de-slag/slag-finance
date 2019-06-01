package de.slag.finance.app.impl;

import de.slag.common.context.SlagContext;
import de.slag.common.db.EntityBean;
import de.slag.finance.data.FinDataPointDao;
import de.slag.finance.logic.service.FinDataPointService;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.FinDataPointFactory;

public class FinDataPointServiceImpl implements FinDataPointService {

	private FinDataPointDao dao = SlagContext.getBean(FinDataPointDao.class);

	@Override
	public FinDataPointDao getDao() {
		return dao;
	}

	@Override
	public FinDataPointFactory getFactory() {
		return new FinDataPointFactoryImpl();
	}

	@Override
	public FinDataPoint create() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FinDataPoint getPersistable(FinDataPoint dataPoint) {
		if (dataPoint instanceof EntityBean) {
			return dataPoint;
		}
		final FinDataPointFactory factory = getFactory();
		factory.setIsin(dataPoint.getIsin());
		factory.setDate(dataPoint.getDate());
		factory.setKpi(dataPoint.getKpi());
		factory.setParameters(dataPoint.getParameters());
		return factory.create();
	}

}
