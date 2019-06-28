package de.slag.finance3.logic;

import java.util.Collection;
import java.util.Optional;

import de.slag.common.XiDataDao;
import de.slag.common.model.XiData;
import de.slag.finance.FinDataPointDao;

public class FinPriceImportRunner implements Runnable {
	
	private FinDataPointDao finDataPointDao;
	
	private XiDataDao xiDataDao;

	@Override
	public void run() {
		xiDataDao.findAllIds().forEach(this::importData);
	}
	
	private void importData(Long xiId) {
		final XiData xiData = xiDataDao.loadById(xiId).get();
		
		
		
	}
}
