package de.slag.finance3.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.utils.DateUtils;
import de.slag.finance.FinDataPointDao;
import de.slag.finance.IsinWknDao;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.model.AbstractFinDataPoint;
import de.slag.finance.model.FinDataPoint;
import de.slag.finance.model.FinPrice;
import de.slag.finance.model.FinSma;
import de.slag.finance.model.IsinWkn;
import de.slag.finance3.AvailableProperties;
import de.slag.finance3.logic.calc.SmaCalcUtils;
import de.slag.finance3.logic.config.FinAdminSupport;
import de.slag.finance3.logic.importer.FinImportService;

@Service
public class FinServiceImpl implements FinService {

	private static final Log LOG = LogFactory.getLog(FinServiceImpl.class);

	private FinImportService finImportService;

	@Resource
	private IsinWknDao isinWknDao;

	@Resource
	private FinDataPointDao finDataPointDao;
	
	@Resource
	private XiDataDao xiDataDao;

	@Override
	public AbstractFinDataPoint calc(String isin, LocalDate date, Kpi kpi, Integer[] parameters) {
		switch (kpi) {
		case PRICE:
			final Optional<FinDataPoint> optional = Optional.empty();
			if (!optional.isPresent()) {
				throw new BaseException(String.format("Price is not available for: %s %s", isin, date));
			}
		case SMA:
			return SmaCalcUtils.calc(isin, date, kpi, parameters[0], getDataStore());

		default:
			throw new BaseException(String.format("Kpi '%s' is not supported yet", kpi));
		}
	}

	@Override
	public void importData() {
		final FinPriceImportRunner finPriceImportRunner = new FinPriceImportRunner(finDataPointDao,xiDataDao);
		finPriceImportRunner.run();
		
	}

	@Override
	public void assertIsinWkn() {

		LOG.info("assert isin-wkn...");
		final String wknIsinsProperty = FinAdminSupport.getSafe(AvailableProperties.DATA_WKN_ISINS);

		final List<String> wknIsins = Arrays.asList(wknIsinsProperty.split(";"));

		final Collection<IsinWkn> isinWkns = new ArrayList<IsinWkn>();
		wknIsins.forEach(wknIsin -> {
			final String[] split = wknIsin.split(":");
			if (split.length != 2) {
				throw new BaseException("not valid: " + wknIsin);
			}
			final String wkn = split[0];
			final String isin = split[1];

			IsinWkn isinWkn = new IsinWkn.Builder().wkn(wkn).isin(isin).build();
			LOG.info(String.format("add '%s'", isinWkn));
			isinWkns.add(isinWkn);
		});

		final List<String> existingIsins = isinWknDao.findAllIds().stream().map(id -> isinWknDao.loadById(id))
				.filter(isinWkn -> isinWkn.isPresent()).map(isinWkn -> isinWkn.get()).map(isinWkn -> isinWkn.getIsin())
				.collect(Collectors.toList());

		final List<IsinWkn> isinWknToSave = isinWkns.stream().filter(g -> !existingIsins.contains(g.getIsin()))
				.collect(Collectors.toList());

		LOG.info(String.format("isin-wkns to save: '%s'", isinWknToSave));
		isinWknToSave.forEach(isinWkn -> isinWknDao.save(isinWkn));

		LOG.info(isinWknDao.findAllIds());
	}

	@Override
	public void calcAllAdministered() {
		final List<String> admKpis = Arrays.asList(FinAdminSupport.getSafe(AvailableProperties.CALC_KPIS).split(";"));
		admKpis.forEach(admKpi -> {
			LOG.info(admKpi);
			String[] split = admKpi.split("_");
			final Kpi kpi = Kpi.valueOf(split[0]);
			ArrayList<Integer> parameters = new ArrayList<>();
			for (int i = 1; i < split.length; i++) { // sic! ignore first
				parameters.add(Integer.valueOf(split[i]));
			}
			LOG.info(String.format("kpi: '%s' %s", kpi, parameters));

			final Collection<Long> allIds = isinWknDao.findAllIds();

			allIds.forEach(id -> {
				final IsinWkn isinWkn = isinWknDao.loadById(id).orElseThrow();
				calc(isinWkn.getIsin(), LocalDate.now(), kpi, parameters.toArray(new Integer[0]));

			});
		});
	}

	private FinDataStore getDataStore() {
		return new FinDataStore() {

			@Override
			public void put(AbstractFinDataPoint dp) {
				finDataPointDao.save(dp);
			}

			@Override
			public Optional<AbstractFinDataPoint> get(String isin, LocalDate date, Kpi kpi, Integer... params) {
				Collection<Long> findAllIds = finDataPointDao.findAllIds();

				Class<? extends AbstractFinDataPoint> clazz = determineClass(kpi);

				return findAllIds.stream()
					.map(id -> finDataPointDao.loadById(id))
					.filter(v -> v.isPresent())
					.map(v -> v.get())
					.filter(v -> clazz.isInstance(v))
					.filter(v -> v.getDate().equals(DateUtils.toDate(date)))
					.filter(v -> v.getIsin().equals(isin))
					.filter(v -> Arrays.deepEquals(v.getParameter(), params))
					.findAny();
			}

			private Class<? extends AbstractFinDataPoint> determineClass(Kpi kpi) {
				switch (kpi) {
				case PRICE:
					return FinPrice.class;
				case SMA:
					return FinSma.class;
				default:
					throw new BaseException("not supported: " + kpi);
				}
			}
		};
	}

	@Override
	public void stagetData(Path path) {
		final FinRawDataStageRunner stageRunner = new FinRawDataStageRunner(path, xiDataDao);
		stageRunner.run();
		
	}
}
