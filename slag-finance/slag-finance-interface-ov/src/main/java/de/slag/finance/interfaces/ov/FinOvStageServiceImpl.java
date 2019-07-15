package de.slag.finance.interfaces.ov;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.common.base.AdmService;
import de.slag.common.base.BaseException;
import de.slag.common.base.event.Event;
import de.slag.common.base.event.EventAction;
import de.slag.common.base.event.EventBus;
import de.slag.common.model.beans.SysLog.Severity;
import de.slag.common.model.service.SysLogService;
import de.slag.finance.StockTitleDao;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.api.Constants;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance.api.FinStageService;
import de.slag.finance.api.StagingException;
import de.slag.finance.model.StockTitle;

@Service
public class FinOvStageServiceImpl implements FinStageService {

	private static final Log LOG = LogFactory.getLog(FinOvStageServiceImpl.class);

	@Resource
	private XiDataDao xiDataDao;

	@Resource
	private StockTitleDao stockTitleDao;

	@Resource
	private SysLogService sysLogService;

	@Resource
	private AdmService admService;

	@PostConstruct
	public void setUp() {
		EventBus.addAction(new EventAction() {

			@Override
			public void run(Event event) {
				if (!(event instanceof FinOvDownloadedEvent)) {
					return;
				}
				try {
					stage0();
				} catch (StagingException e) {
					LOG.error(e);
					sysLogService.log(Severity.ERROR, e.getMessage());
				}
			}
		});
	}

	public void stage() throws StagingException {
		sysLogService.info(this.getClass(), "start downloading + staging");
		download0();
	}

	private void stage0() throws StagingException {
		sysLogService.info(this.getClass(), "start statging");
		String workdir = FinAdminSupport.getSafe(AvailableProperties.WORKDIR);
		String baseStageDir = workdir + "/" + Constants.SUB_WORKDIR_STAGING;

		final Path path = Paths.get(baseStageDir);
		if (!path.toFile().exists()) {
			throw new StagingException(String.format("stage directory not exists: '%s'", baseStageDir));
		}

		FinRawDataStageRunner finRawDataStageRunner = new FinRawDataStageRunner(path, xiDataDao, stockTitleDao);
		finRawDataStageRunner.run();

		sysLogService.info(this.getClass(), " staging done.");
	}

	private void download0() {
		sysLogService.info(this.getClass(), "start download");
		String workdir = FinAdminSupport.getSafe(AvailableProperties.WORKDIR);
		String baseStageDir = workdir + "/" + Constants.SUB_WORKDIR_STAGING;

		final String ovUrl = admService.getSafe(AvailableProperties.FINANCE_INTERFACE_OV_URL);

		final Collection<StockTitle> stockTitles = stockTitleDao.findAll();

		final Collection<OvDownloadRunner> runners = new ArrayList<>();
		stockTitles.forEach(stockTitle -> {
			final String isin = stockTitle.getIsin();
			final String downloadDir = baseStageDir + "/" + isin;
			final File file = new File(downloadDir);
			if (!file.exists()) {
				sysLogService.info(this.getClass(), "no dir found for, creating  " + stockTitle.getIsin());
				file.mkdir();
			}
			final String notation = stockTitle.getNotation();
			if (StringUtils.isEmpty(notation)) {
				sysLogService.error(this.getClass(), "no notation found for " + stockTitle.getIsin());
				return;
			}

			final File targetFile = new File(downloadDir + "/" + System.currentTimeMillis() + ".csv");

			final URL url = new OvUrlBuilder().baseUrl(ovUrl).date(new Date()).notation(notation).build();

			runners.add(new OvDownloadRunner(url, targetFile));
		});

		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

		runners.forEach(executor::submit);
		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new BaseException(e);
		}
		sysLogService.info(this.getClass(), "download done.");
		EventBus.occure(new FinOvDownloadedEvent());
	}

}
