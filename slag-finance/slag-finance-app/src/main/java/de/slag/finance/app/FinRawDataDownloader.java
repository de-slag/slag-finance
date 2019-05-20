package de.slag.finance.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
	private static final String CLI_OPTION_INTERVAL = "i";

	private static final Map<String, Integer> intervalMap = new HashMap<>();

	public static void main(String[] args) throws IOException, ParseException {
		setup();
		final CommandLine parse = precheckAndParseCliOptions(args);

		final String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String filenname = parse.getOptionValue(CLI_OPTION_OUTPUT_DIR) + "/846900_" + format + ".csv";

		final Properties configProperties = SlagConfigSupportUtils.getConfigProperties();
		final String baseUrl = configProperties.getProperty(Constants.DOWNLOAD_BASEURL);
		if (baseUrl == null) {
			throw new RuntimeException("base url ist empty");
		}

		String interval = "m";
		if ("y".equals(parse.getOptionValue(CLI_OPTION_INTERVAL))) {
			interval = "y";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("wget ");
		sb.append("-O " + filenname + " ");
		sb.append(baseUrl + "?notationId=");
		sb.append("20735");
		sb.append("&dateStart=");
		sb.append(startDate(interval));
		sb.append("&interval=" + ("y".equals(interval) ? "Y":"M") +"1");

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

	private static String startDate(String interval) {
		return new SimpleDateFormat("dd.MM.yyyy")
				.format(DateUtils.toDate(LocalDate.now().minusDays(intervalMap.get(interval))));
	}

	private static void setup() {
		LoggingUtils.activateLogging(Level.INFO);
		intervalMap.put("m", 25);
		intervalMap.put("y", 360);
	}

	private static CommandLine precheckAndParseCliOptions(String[] args) throws ParseException {

		final Options options = CliOptionsUtils.createOptions();
		options.addOption(CLI_OPTION_OUTPUT_DIR, true, "output directory");
		options.addOption(CLI_OPTION_INTERVAL, true, "interval, [m]onth or [y]ear");

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
