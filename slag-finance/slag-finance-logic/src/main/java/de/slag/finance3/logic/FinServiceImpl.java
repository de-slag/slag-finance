package de.slag.finance3.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.finance.IsinWknDao;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.IsinWkn;
import de.slag.finance3.logic.importer.FinImportService;

@Service
public class FinServiceImpl implements FinService {

	private static final Log LOG = LogFactory.getLog(FinServiceImpl.class);

	@Resource
	private FinImportService finImportService;

	@Resource
	private FinIsinWknGeneratorService finIsinWknGeneratorService;

	@Resource
	private IsinWknDao isinWknDao;

	@Override
	public FinDataPoint calc(String isin, LocalDate date, Kpi kpi, int[] parameters) {
		switch (kpi) {
		case PRICE:
			final Optional<FinDataPoint> optional = Optional.empty();// = finDataPointService.get(isin, date, kpi,
																		// parameters);
			if (!optional.isPresent()) {
				throw new BaseException(String.format("Price is not available for: %s %s", isin, date));
			}

		default:
			throw new BaseException(String.format("Kpi '%s' is not supported yet", kpi));
		}
	}

	@Override
	public void importData(Path fromPath) {
		finImportService.importData(fromPath);
	}

	@Override
	public void assertIsinWkn() {
		
		LOG.info("assert isin-wkn...");
		final Collection<IsinWkn> generate = finIsinWknGeneratorService.generate();

		final List<String> existingIsins = isinWknDao.findAllIds()
				.stream()
				.map(id -> isinWknDao.loadById(id))
				.filter(isinWkn -> isinWkn.isPresent())
				.map(isinWkn -> isinWkn.get())
				.map(isinWkn -> isinWkn.getIsin())
				.collect(Collectors.toList());

		final List<IsinWkn> isinWknToSave = generate.stream()
				.filter(g -> !existingIsins.contains(g.getIsin()))
				.collect(Collectors.toList());
		LOG.info(String.format("isin-wkns to save: '%s'", isinWknToSave));
		isinWknToSave.forEach(isinWkn -> isinWknDao.save(isinWkn));
		
		LOG.info(isinWknDao.findAllIds());
	}
}
