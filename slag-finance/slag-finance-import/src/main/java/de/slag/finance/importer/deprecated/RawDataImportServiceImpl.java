package de.slag.finance.importer.deprecated;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.DateUtils;
import de.slag.finance.deprecated.FinDataPointFactory;
import de.slag.finance.deprecated.FinDataPointService;

//@Service
public class RawDataImportServiceImpl implements RawDataImportService {

	private static final Log LOG = LogFactory.getLog(RawDataImportServiceImpl.class);

	private final FinDataPointService finDataPointService = SlagContext.getBean(FinDataPointService.class);

	@Override
	public void importFrom(String folderName) {
		final List<File> importFiles = getImportFiles(
				Objects.requireNonNull(folderName, "'folder name' must not be null"));

		if (importFiles.isEmpty()) {
			LOG.info("nothing to import");
			return;
		}

		final List<CSVRecord> csvRecords = getCsvRecords(importFiles);

		final boolean emptyValues = csvRecords.stream().anyMatch(rec -> {
			for (int i = 0; i < rec.size(); i++) {
				if (StringUtils.EMPTY.equals(rec.get(i))) {
					return true;
				}
			}
			return false;
		});

		if (emptyValues) {
			throw new BaseException("Values incomplete");
		}

		final List<RawDataPoint> collect = csvRecords
				.stream()
					.map(csv -> rawDataPoint(csv))
					.collect(Collectors.toList());

		final List<RawDataPoint> toImport = collect
				.stream()
					.filter(rdp -> !finDataPointService.exists(rdp.getIsin(), rdp.getDate()))
					.collect(Collectors.toList());

		toImport.forEach(imp -> LOG.info("import: " + imp));

		for (final RawDataPoint rdp : toImport) {
			final FinDataPointFactory dpFactory = finDataPointService.getFactory();

//			dpFactory.setIsin(rdp.getIsin());
//			dpFactory.setKpi(Kpi.PRICE);
//			dpFactory.setDate(rdp.getDate());
//			dpFactory.setValue(rdp.getValue());
//			LOG.info("SAVE: " + dpFactory);
//			finDataPointService.save(dpFactory.create());

		}

	}

	private List<CSVRecord> getCsvRecords(final List<File> importFiles) {
		final List<CSVRecord> csvRecords = importFiles.stream().flatMap(f -> {
	
			return CsvUtils.getRecords(f.getAbsolutePath()).stream();

		}).collect(Collectors.toList());
		return csvRecords;
	}

	private List<File> getImportFiles(String folderName) {
		final File file = new File(Objects.requireNonNull(folderName, "'foler name' must not be null"));

		final File[] listFiles = file.listFiles();
		if (listFiles == null) {
			return Collections.emptyList();
		}

		final List<File> importFiles = Arrays
				.stream(listFiles)
					.filter(f -> f.getAbsolutePath().endsWith("raw.csv"))
					.collect(Collectors.toList());
		return importFiles;
	}

	private RawDataPoint rawDataPoint(CSVRecord csv) {
		final RawDataPoint rdp = new RawDataPoint();
		rdp.setIsin(csv.get("ISIN"));
		try {
			rdp.setDate(DateUtils.toLocalDate(new SimpleDateFormat("yyyy.MM.dd").parse(csv.get("DATE"))));
		} catch (final ParseException e) {
			throw new BaseException(e);
		}
		rdp.setValue(BigDecimal.valueOf(Double.valueOf(csv.get("VALUE"))));
		return rdp;

	}

}
