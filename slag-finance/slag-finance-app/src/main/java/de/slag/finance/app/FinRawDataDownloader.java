package de.slag.finance.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.CliOptionsUtils;
import de.slag.common.utils.ConsoleUtils;
import de.slag.common.utils.DateUtils;
import de.slag.common.utils.SlagConfigSupportUtils;
import de.slag.finance.Constants;

public class FinRawDataDownloader {

	private static final Log LOG = LogFactory.getLog(FinRawDataDownloader.class);

	private static final String CLI_OPTION_OUTPUT_DIR = "o";

	public static void main(String[] args) throws IOException, ParseException {
		LoggingUtils.activateLogging(Level.INFO);
		final CommandLine parse = precheckAndParseCliOptions(args);

		final String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String filenname = parse.getOptionValue(CLI_OPTION_OUTPUT_DIR) + "/download-" + format + ".csv";

		final Properties configProperties = SlagConfigSupportUtils.getConfigProperties();
		final String baseUrl = configProperties.getProperty(Constants.DOWNLOAD_BASEURL);
		if (baseUrl == null) {
			throw new RuntimeException("base url ist empty");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("wget ");
		sb.append("-O " + filenname + " ");
		sb.append(baseUrl + "?notationId=");
		sb.append("20735");
		sb.append("&dateStart=");
		sb.append(new SimpleDateFormat("dd.MM.yyyy").format(DateUtils.toDate(LocalDate.now().minusDays(25))));
		sb.append("&interval=M1");

		LOG.info("download...");
		final String command = sb.toString();
		LOG.info(String.format("command: '%s'", command));
		LOG.info(ConsoleUtils.runConsoleCommand(command));
		LOG.info("download...done");
		final byte[] readAllBytes = Files.readAllBytes(Paths.get(filenname));
		if (readAllBytes.length == 0) {
			throw new RuntimeException(String.format("Something went wrong: no content in file: '%s'", filenname));
		}
	}

	private static CommandLine precheckAndParseCliOptions(String[] args) throws ParseException {

		final Options options = CliOptionsUtils.createOptions();
		options.addOption(CLI_OPTION_OUTPUT_DIR, true, "output directory");

		final CommandLine parse = CliOptionsUtils.parse(options, args);
		final String outputPath = parse.getOptionValue(CLI_OPTION_OUTPUT_DIR);
		if (StringUtils.isEmpty(outputPath)) {
			CliOptionsUtils.printHelpAndExit(options);
		}
		final Path path = Paths.get(outputPath);
		final boolean exists = Files.exists(path);
		if (!exists) {
			throw new RuntimeException("path not exists: " + outputPath);
		}

		return parse;
	}

	static {

	}

}
