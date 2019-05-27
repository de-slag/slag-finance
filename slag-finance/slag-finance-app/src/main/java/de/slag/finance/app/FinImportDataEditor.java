package de.slag.finance.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.base.BaseException;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.CliOptionsUtils;
import de.slag.common.utils.CsvTransformUtils;
import de.slag.common.utils.CsvTransformUtils.CsvTransformException;

public class FinImportDataEditor {

	private static final Log LOG = LogFactory.getLog(FinImportDataEditor.class);

	private static final String CLI_OPTION_INPUT_DIR = "id";

	private static final String CLI_OPTION_OUTPUT_DIR = "od";

	public static void main(String[] args) throws ParseException {
		LoggingUtils.activateLogging(Level.DEBUG);
		final Options cliOptions = CliOptionsUtils.createOptions();
		cliOptions.addOption(CLI_OPTION_INPUT_DIR, true, "input dir");
		cliOptions.addOption(CLI_OPTION_OUTPUT_DIR, true, "output dir");

		final CommandLine parse = CliOptionsUtils.parse(cliOptions, args);
		final String inputDirName = parse.getOptionValue(CLI_OPTION_INPUT_DIR);
		final String outputDirName = parse.getOptionValue(CLI_OPTION_OUTPUT_DIR);
		new FinImportDataEditor().editImportData(inputDirName, outputDirName);
	}

	public void editImportData(String nameOfInputDir, String nameOfOutputDir) {
		final String inputDirName = Objects.requireNonNull(nameOfInputDir, "'input dir' not set");
		final String outputDirName = Objects.requireNonNull(nameOfOutputDir, "'output dir' not set");

		final Map<String, File> inputFiles = readInputFiles(inputDirName);
		final Collection<File> inputFileValues = inputFiles.values();
		final String tmpWorkdir = tmpWorkdir();
		inputFileValues.forEach(f -> {
			try {
				editImportData(f, outputDirName, tmpWorkdir);
			} catch (CsvTransformException | IOException e) {
				throw new BaseException(e);
			}
		});
	}

	private String tmpWorkdir() {
		final long currentTimeMillis = System.currentTimeMillis();
		final String string = "/tmp/slag-finance-" + currentTimeMillis;
		new File(string).mkdir();
		return string;
	}

	private void editImportData(File f, String outputDirName, String tmpWorkdir)
			throws CsvTransformException, IOException {
		final String currentFilename = f.getName();
		String targetFileName = outputDirName + "/" + currentFilename;
		final File file = new File(targetFileName);
		if (file.exists()) {
			LOG.info(String.format("file exists: '%s', deleting...", targetFileName));
			file.delete();
		}
		
		LOG.info("transforming...");

		final String absolutePath = f.getAbsolutePath();

		final String tmpFileName1 = tmpWorkdir + "/" + currentFilename + ".1";
		final String tmpFileName2 = tmpWorkdir + "/" + currentFilename + ".2";
		final String tmpFileName3 = tmpWorkdir + "/" + currentFilename + ".3";
		final String tmpFileName4 = tmpWorkdir + "/" + currentFilename + ".4";
		final String tmpFileName5 = tmpWorkdir + "/" + currentFilename + ".5";

		CsvTransformUtils.removeColumns(absolutePath, tmpFileName1, "Eroeffnung", "Hoch", "Tief", "Volumen");
		CsvTransformUtils.renameHeader(tmpFileName1, tmpFileName2, "Datum", "DATE");
		CsvTransformUtils.renameHeader(tmpFileName2, tmpFileName3, "Schluss", "PRICE");
		CsvTransformUtils.umformat(tmpFileName3, tmpFileName4, "PRICE", s -> {
			final String replace = s.replace(".", "");
			return replace.replace(",", ".");
		});
		CsvTransformUtils.addColumn(tmpFileName4, tmpFileName5, "ISIN", "DE0008469008");

		LOG.info(String.format("create '%s'", targetFileName));

		file.createNewFile();

		final byte[] readAllBytes = Files.readAllBytes(Paths.get(tmpFileName5));
		Files.write(Paths.get(targetFileName), readAllBytes);
	}

	private Map<String, File> readInputFiles(String inputDirName) {
		final File file = Paths.get(inputDirName).toFile();
		final List<File> collect = Arrays.stream(file.listFiles()).filter(f -> !f.isDirectory())
				.collect(Collectors.toList());

		final Map<String, File> result = new HashMap<>();
		collect.forEach(f -> {
			final String name = f.getName();
			result.put(name, f);
		});
		return result;
	}

}
