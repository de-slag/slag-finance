package de.slag.finance3.report;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.finance.FinPriceDao;

@Service
public class FinReportServiceImpl implements FinReportService {

	private static final Log LOG = LogFactory.getLog(FinReportServiceImpl.class);

	@Resource
	private XiDataDao xiDataDao;

	@Resource
	private FinPriceDao finPriceDao;

	@Override
	public void reportStatistic() {
		final Collection<String> stats = new ArrayList<>();
		stats.add("STAGING AREA: " + xiDataDao.findAllIds().size());
		stats.add("PRICES: " + finPriceDao.findAllIds().size());

		LOG.info("\n" + String.join("\n", stats));

	}

}
