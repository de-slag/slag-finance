package de.slag.finance.interfaces.ov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.base.event.EventBus;
import de.slag.common.model.XiData;
import de.slag.common.utils.CsvUtils;
import de.slag.finance.IsinWknDao;
import de.slag.finance.api.Constants;
import de.slag.finance.api.DataStagedEvent;
import de.slag.finance.model.IsinWkn;

public class FinRawDataStageRunner implements Runnable {

	private static final Log LOG = LogFactory.getLog(FinRawDataStageRunner.class);

	private static final Collection<String> INVALID_CHARS = Arrays.asList(";", "=");

	private final Path basePath;

	private final XiDataDao xiDataDao;

	private final IsinWknDao isinWknDao;

	private int count;

	public FinRawDataStageRunner(Path basePath, XiDataDao xiDataDao, IsinWknDao isinWknDao) {
		super();
		this.basePath = basePath;
		this.xiDataDao = xiDataDao;
		this.isinWknDao = isinWknDao;
	}

	@Override
	public void run() {
		try {
			run0();
		} catch (IOException e) {
			throw new BaseException(e);
		}
		EventBus.occure(new DataStagedEvent("data staged: " + count));
	}

	private void run0() throws IOException {
		final Collection<Path> stageDirectories = determineStageDirectories();

		LOG.info(String.format("%s directories to stage", stageDirectories.size()));

		stageDirectories.forEach(stageDirectory -> {
			try {
				stage(stageDirectory);
			} catch (IOException e) {
				throw new BaseException(e);
			}
		});

	}

	private void stage(Path stageDirectory) throws IOException {
		final Collection<Path> csvFiles = Files.walk(stageDirectory).filter(Files::isRegularFile)
				.filter(file -> file.toString().endsWith(".csv")).collect(Collectors.toList());

		LOG.info(String.format("%s files to stage", csvFiles.size()));

		csvFiles.stream().map(file -> file.toFile()).forEach(this::stage);
	}

	private Collection<Path> determineStageDirectories() {
		final Collection<IsinWkn> allIsinWkns = isinWknDao.findAll();

		final String absolutePath = basePath.toFile().getAbsolutePath();
		final String normalizedPathString = normalizedPathString(absolutePath);

		return allIsinWkns.stream().map(isinWkn -> isinWkn.getIsin()).map(isin -> normalizedPathString + "/" + isin)
				.map(absolutePathString -> new File(absolutePathString)).filter(file -> file.exists())
				.filter(file -> file.isDirectory()).map(file -> file.toPath()).collect(Collectors.toList());
	}

	private String normalizedPathString(String notNormalizedPathString) {
		if (notNormalizedPathString.contains("\\")) {
			return notNormalizedPathString.replace("\\", "/");
		}
		return notNormalizedPathString;
	}

	private void stage(File csvFile) {
		final String absolutePath = csvFile.getAbsolutePath();
		String[] split = normalizedPathString(absolutePath).split("/");
		int preLast = split.length - 2;
		String isin = split[preLast];

		LOG.info(String.format("stage %s...", absolutePath));
		Collection<String> header = CsvUtils.getHeader(absolutePath);
		Collection<CSVRecord> records = CsvUtils.getRecords(absolutePath).stream().filter(CsvUtils.FILTER_NO_EMPTY)
				.collect(Collectors.toList());
		records.forEach(rec -> stage(header, rec, isin));
	}

	private void stage(Collection<String> header, CSVRecord record, String isin) {

		final Collection<String> values = new ArrayList<>();
		header.forEach(headColumn -> {
			final String value = record.get(headColumn);

			assertNoInvalidCharacters(headColumn);
			assertNoInvalidCharacters(value);

			values.add(headColumn + "=" + value);
		});
		values.add("Isin=" + isin);

		final XiData xiData = new XiData();
		xiData.setType(Constants.FIN_PRICE_EXCHANGE_IMPORT_TYPE);
		xiData.setValue(String.join(";", values));
		xiDataDao.save(xiData);
		count++;
	}

	private void assertNoInvalidCharacters(String s) {
		INVALID_CHARS.forEach(c -> {
			if (s.contains(c))
				throw new BaseException(s + " contains " + c);
		});
	}

}
