package de.slag.finance3.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.base.SlagProperties;
import de.slag.common.base.event.EventBus;
import de.slag.common.context.SlagContext;
import de.slag.common.model.beans.SystemLog;
import de.slag.common.model.beans.SystemLog.Severity;
import de.slag.common.model.beans.SystemLogDao;
import de.slag.common.utils.DateUtils;
import de.slag.finance.FinPriceDao;
import de.slag.finance.FinSmaDao;
import de.slag.finance.IsinWknDao;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance.api.FinImportService;
import de.slag.finance.api.FinService;
import de.slag.finance.api.FinStageService;
import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.deprecated.FinStockDateUtils;
import de.slag.finance.model.AbstractFinDataPoint;
import de.slag.finance.model.FinPrice;
import de.slag.finance.model.FinSma;
import de.slag.finance.model.IsinWkn;
import de.slag.finance.model.Kpi;
import de.slag.finance3.events.CalculationsDoneEvent;
import de.slag.finance3.events.CalulationsPreparedEvent;
import de.slag.finance3.logic.calc.SmaCalcUtils;

@Service
public class FinServiceImpl implements FinService {

	private static final Log LOG = LogFactory.getLog(FinServiceImpl.class);

	@Resource
	private IsinWknDao isinWknDao;

	@Resource
	private FinPriceDao finPriceDao;

	@Resource
	private XiDataDao xiDataDao;

	@Resource
	private FinSmaDao finSmaDao;

	@Resource
	private FinImportService finImportService;

	@Resource
	private SystemLogDao systemLogDao;

	private Collection<FinStageService> stageServices = new ArrayList<>();

	@PostConstruct
	public void setUp() {
		FinAdminSupport.getSafe(AvailableProperties.ALLOVER_START_DATE);
	}

	private Collection<FinStageService> stageServices() {
		if (stageServices.isEmpty()) {
			stageServices.add(SlagContext.getBean(FinStageService.class, "finOvStageServiceImpl"));
			stageServices.add(SlagContext.getBean(FinStageService.class, "finAvStageServiceImpl"));

			if (stageServices.isEmpty()) {
				throw new BaseException("no stage services found");
			}
		}
		return stageServices;

	}

	@Override
	public AbstractFinDataPoint calc(String isin, LocalDate date, Kpi kpi, Integer[] parameters) {
		final String string = SlagProperties.get(AvailableProperties.ALLOVER_START_DATE);
		if (StringUtils.isEmpty(string)) {
			throw new BaseException("not adminstrated: " + AvailableProperties.ALLOVER_START_DATE);
		}
		final Date parse;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd").parse(string);
		} catch (ParseException e) {
			throw new BaseException(e);
		}
		if (date.isBefore(DateUtils.toLocalDate(parse))) {
			throw new BaseException("date before allover start date: " + date);
		}

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
		finImportService.importData();
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

		final Date parse;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd")
					.parse(FinAdminSupport.getSafe(AvailableProperties.ALLOVER_START_DATE));
		} catch (ParseException e) {
			throw new BaseException(e);
		}

		final LocalDate alloverStartDate = DateUtils.toLocalDate(parse);

		LocalDate current = LocalDate.now().minusDays(1);
		Collection<LocalDate> allDates = new ArrayList<>();
		while (current.isAfter(alloverStartDate)) {
			allDates.add(current);
			current = current.minusDays(1);
		}

		final List<LocalDate> stockDays = allDates.stream().filter(day -> FinStockDateUtils.isStockDay(day))
				.collect(Collectors.toList());

		final List<Callable<Integer>> tasks = new ArrayList<>();

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

			final List<IsinWkn> isinWkns = allIds.stream().map(id -> isinWknDao.loadById(id)).map(v -> v.get())
					.collect(Collectors.toList());

			isinWkns.forEach(isinWkn -> {
				stockDays.forEach(stockDay -> {
					

					tasks.add(new Callable<Integer>() {

						@Override
						public Integer call() throws Exception {
							calc(isinWkn.getIsin(), stockDay, kpi, parameters.toArray(new Integer[0]));
							return 0;
						}
					});
				});

			});
		});

		EventBus.occure(new CalulationsPreparedEvent("tasks: " + tasks.size()));

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Collections.shuffle(tasks);

		final long start = System.currentTimeMillis();

		tasks.forEach(executor::submit);
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			throw new BaseException(e);
		}
		
		for (Callable<Integer> callable : tasks) {
			try {
				callable.call();
			} catch (Exception e) {
				throw new BaseException(e);
			}
		}

		EventBus.occure(new CalculationsDoneEvent(
				"took (ms): " + DateUtils.millisecondsToHumanReadable(System.currentTimeMillis() - start)));

	}

	private FinDataStore getDataStore() {
		return new FinDataStore() {

			@Override
			public void put(AbstractFinDataPoint dp) {
				if (dp instanceof FinPrice) {
					finPriceDao.save((FinPrice) dp);
				} else if (dp instanceof FinSma) {
					finSmaDao.save((FinSma) dp);
				} else {
					throw new UnsupportedOperationException();
				}
				LOG.info(dp);

			}

			@Override
			public Optional<AbstractFinDataPoint> get(String isin, LocalDate date, Kpi kpi, Integer... params) {
				switch (kpi) {
				case PRICE:
					final Optional<FinPrice> loadPriceBy = finPriceDao.loadPriceBy(isin, DateUtils.toDate(date));
					if (!loadPriceBy.isPresent()) {
						throw new BaseException("no price found for: " + isin + ", " + date);
					}
					return Optional.of(loadPriceBy.get());

				default:
					throw new UnsupportedOperationException();
				}
			}

			@Override
			public <T extends AbstractFinDataPoint> Optional<T> get(Class<T> type, String isin, LocalDate date,
					Integer... params) {

				final Optional<AbstractFinDataPoint> result;
				if (FinPrice.class.isAssignableFrom(type)) {
					result = get(isin, date, Kpi.PRICE, params);
				} else {
					throw new BaseException("not supported: " + type);
				}
				if (!result.isPresent()) {
					return Optional.empty();
				}
				final AbstractFinDataPoint abstractFinDataPoint = result.get();
				return Optional.of(type.cast(abstractFinDataPoint));

			}
		};
	}

	public void stageData() {

		final Collection<Callable<Integer>> tasks = new ArrayList<>();

		stageServices().forEach(service -> {
			tasks.add(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					service.stage();
					return 0;
				}
			});
		});

		ExecutorService exec = Executors.newScheduledThreadPool(1);
		tasks.forEach(exec::submit);

		exec.shutdown();
		try {
			exec.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new BaseException(e);
		}
		tasks.forEach(task -> {
			try {
				task.call();
			} catch (Exception e) {
				systemLogDao.save(new SystemLog(Severity.ERROR, e.getMessage()));
				throw new BaseException(e);
			}
		});

	}

}
