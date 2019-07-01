package de.slag.finance.app.deprecated;

import de.slag.common.context.SlagContext;
import de.slag.common.model.EntityBean;
import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.deprecated.FinDataPointDao;
import de.slag.finance.deprecated.FinDataPointFactory;
import de.slag.finance.deprecated.FinDataPointFactory2;
import de.slag.finance.deprecated.FinDataPointService;

public class FinDataPointServiceImpl implements FinDataPointService {

	private FinDataPointDao dao = SlagContext.getBean(FinDataPointDao.class);

	@Override
	public FinDataPointDao getDao() {
		return dao;
	}

	@Override
	public FinDataPointFactory getFactory() {
		//return new FinDataPointFactoryImpl();
		return null;
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
//		factory.setIsin(dataPoint.getIsin());
//		factory.setDate(dataPoint.getDate());
//		factory.setKpi(dataPoint.getKpi());
//		factory.setParameters(dataPoint.getParameters());
//		return factory.create();
		return null;
	}

}
