package de.slag.finance.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import de.slag.common.utils.FileDirectoryUtils;

public class FinDataImporter {

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

		final Collection<File> allFilesOf = FileDirectoryUtils.allFilesOf(inputDir);
		allFilesOf.forEach(f -> LOG.info(f.getName()));

	}

	private void addIfAny(Map<String, String> values, String filename) {
		Collection<CSVRecord> records;
		try {
			records = CsvUtils.getRecords(filename);
		} catch (IOException e) {
			throw new BaseException(e);
		}
		final boolean isPresent = records.stream()
				.filter(record -> record.get(DATE)
						.equals(values.get(DATE)))
				.filter(record -> record.get(ISIN)
						.equals(values.get(ISIN)))
				.findAny()
				.isPresent();
		if (isPresent) {
			return;
		}
		

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
