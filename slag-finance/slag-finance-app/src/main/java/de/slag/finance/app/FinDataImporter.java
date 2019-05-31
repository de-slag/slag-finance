package de.slag.finance.app;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.CliOptionsUtils;
import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.DateUtils;
import de.slag.common.utils.FileDirectoryUtils;
import de.slag.finance.data.model.Kpi;
import de.slag.finance.logic.service.FinDataPointService;
import de.slag.finance.model.FinDataPoint;

public class FinDataImporter {

	private static final String SDF_DATE_FORMAT = "yyyy-MM-dd";

	private static final Log LOG = LogFactory.getLog(FinDataImporter.class);

	private static final String CLI_OPTION_INPUT_DIR = "id";
	private static final String CLI_OPTION_OUTPUT_FILE = "of";

	private static final String DATE = "DATE";

	private static final String PRICE = "PRICE";
	private static final String ISIN = "ISIN";

	public void importData(String directoryOfInput, String fileOfOutput) {

		final String inputDir = Objects.requireNonNull(directoryOfInput, "input dir must not be null");
		final String outputFile = Objects.requireNonNull(fileOfOutput, "output file must not be null");

		if (!new File(outputFile).exists()) {
			createOutputFile(outputFile);
		}

		final Collection<CSVRecord> records = CsvUtils.getRecords(outputFile);
		final FinDataPointService finDataPointService = SlagContext.getBean(FinDataPointService.class);
		records.stream()
				.filter(rec -> {
					final String isin = rec.get(ISIN);
					final LocalDate date2 = date(rec);	
					return !finDataPointService.exists(isin, date2);
				})
				.forEach(rec -> {
					final BigDecimal valueOf = BigDecimal.valueOf(Double.valueOf(rec.get(PRICE)));
					final String isin = rec.get(ISIN);
					final LocalDate localDate = date(rec);
				
					final FinDataPoint dp = finDataPointService.create();
					dp.setDate(localDate);
					dp.setIsin(isin);
					dp.setKpi(Kpi.PRICE);
					dp.setValue(valueOf);
					
					finDataPointService.save(dp);
					
					
					
					
					
				});

	}

	private LocalDate date(CSVRecord rec) {
		final String dateString = rec.get(DATE);
		final SimpleDateFormat sdf = new SimpleDateFormat(SDF_DATE_FORMAT);
		Date parsedDate;
		try {
			parsedDate = sdf.parse(dateString);
		} catch (java.text.ParseException e) {
			throw new BaseException(e);
		}
		final LocalDate localDate = DateUtils.toLocalDate(parsedDate);
		return localDate;
	}

	private void createOutputFile(String outputFile) {
		final List<String> newHeader = Arrays.asList(DATE, ISIN, PRICE);
		final Collection<Collection<String>> csv = new ArrayList<>();
		csv.add(newHeader);
		try {
			CsvUtils.write(csv, Paths.get(outputFile));
		} catch (IOException e) {
			throw new BaseException(e);
		}
	}

	public static void main(String[] args) throws ParseException {
		setUp();
		final Options cliOptions = CliOptionsUtils.createOptions();
		cliOptions.addOption(CLI_OPTION_INPUT_DIR, true, "input dir");
		cliOptions.addOption(CLI_OPTION_OUTPUT_FILE, true, "output file");

		final CommandLine parse = CliOptionsUtils.parse(cliOptions, args);
		final String outputFile = parse.getOptionValue(CLI_OPTION_OUTPUT_FILE);
		final String inputDir = parse.getOptionValue(CLI_OPTION_INPUT_DIR);
		new FinDataImporter().importData(inputDir, outputFile);

	}

	private static void setUp() {
		LoggingUtils.activateLogging(Level.DEBUG);
		SlagContext.init();
	}
}
