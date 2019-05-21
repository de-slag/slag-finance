package de.slag.finance.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.base.BaseException;
import de.slag.common.base.SlagConfig;
import de.slag.common.context.SlagContext;
import de.slag.common.db.h2.InMemoryProperties;
import de.slag.common.db.hibernate.HibernateDbUpdateUtils;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.CsvTransformUtils;
import de.slag.common.utils.CsvTransformUtils.CsvTransformException;
import de.slag.finance.FinAvailableSettings;
import de.slag.finance.imp.RawDataImportService;
import de.slag.finance.logic.service.FinDataPointService;
import de.slag.finance.model.FinDataPoint;

public class FinApp {

	private static final Log LOG = LogFactory.getLog(FinApp.class);

	public static void main(String[] args) {
		LoggingUtils.activateLogging(Level.DEBUG);
		try {
			new FinApp().run();
		} catch (Throwable t) {
			LOG.error("fehler", t);
		} finally {
			LOG.info("exit");
			System.exit(0);
		}
	}

	public void run() {

		setup();
		try {
			prepareImport();
		} catch (CsvTransformException e) {
			throw new BaseException(e);
		}
		importData();
		calc();
	}

	private void calc() {
		final FinDataPointService finDataPointService = SlagContext.getBean(FinDataPointService.class);
		Collection<Long> findAllIds = finDataPointService.findAllIds();
		throw new BaseException("found: " + findAllIds.size());
		// TODO Auto-generated method stub

	}

	private void prepareImport() throws CsvTransformException {
		final String workdir = SlagConfig.getConfigProperties().getProperty(FinAvailableSettings.FINANCE_IMPORTFOLDER);
		String in = workdir + "/export.csv";
		String stage1 = workdir + "/export1.csv";
		String stage2 = workdir + "/export2.csv";
		String stage3 = workdir + "/export3.csv";
		String stage4 = workdir + "/export4.csv";
		String stage5 = workdir + "/export5.csv";
		String stage6 = workdir + "/export6.csv";
		String stage7 = workdir + "/export7.raw.csv";

		CsvTransformUtils.renameHeader(in, stage1, "Datum", "DATE");
		CsvTransformUtils.renameHeader(stage1, stage2, "Schluss", "VALUE");
		CsvTransformUtils.removeColumns(stage2, stage3, "Eroeffnung", "Hoch", "Tief", "Volumen");
		CsvTransformUtils.removeEmptyLines(stage3, stage4);

		CsvTransformUtils.addColumn(stage4, stage5, "ISIN", "DE0008469008");

		String sdfOut = "yyyy-MM-dd";
		String sdfIn = "dd.MM.yyyy";

		CsvTransformUtils.umformat(stage5, stage6, "DATE", origin -> {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdfIn);
			Date parse;
			try {
				parse = simpleDateFormat.parse(origin);
			} catch (ParseException e) {
				throw new BaseException(e);
			}
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(sdfOut);
			return simpleDateFormat2.format(parse);
		});

		CsvTransformUtils.umformat(stage6, stage7, "VALUE", origin -> {
			String replace = origin.replace(".", "");
			String replace2 = replace.replace(",", ".");
			return replace2;
		});

	}

	private void importData() {
		final RawDataImportService rawDataImportService = SlagContext.getBean(RawDataImportService.class);

		rawDataImportService
				.importFrom(SlagConfig.getConfigProperties().getProperty(FinAvailableSettings.FINANCE_IMPORTFOLDER));
	}

	private void setup() {
		LOG.info("setup...");
		SlagContext.init();

		if (!HibernateDbUpdateUtils.isValid(new InMemoryProperties())) {
			LOG.warn("db not valid");
			HibernateDbUpdateUtils.update(new InMemoryProperties());
		}
		LOG.info("db update done");
		LOG.info("setup...done.");
	}

}
