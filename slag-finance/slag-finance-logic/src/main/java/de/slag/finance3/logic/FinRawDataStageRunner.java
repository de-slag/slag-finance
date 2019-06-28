package de.slag.finance3.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.common.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.model.XiData;
import de.slag.common.utils.CsvUtils;
import de.slag.finance3.Constants;

public class FinRawDataStageRunner implements Runnable {

	private static final Log LOG = LogFactory.getLog(FinRawDataStageRunner.class);

	private static final String INVALID_CHARACTERS = ";=";

	private final Path path;

	private final XiDataDao xiDataDao;

	public FinRawDataStageRunner(Path path, XiDataDao xiDataDao) {
		this.path = path;
		this.xiDataDao = xiDataDao;
	}

	@Override
	public void run() {
		try {
			run0();
		} catch (IOException e) {
			throw new BaseException(e);
		}
	}

	private void run0() throws IOException {
		Files.walk(path).filter(Files::isRegularFile).filter(file -> file.toString().endsWith(".csv"))
				.map(file -> file.toFile()).forEach(this::stage);
	}

	private void stage(File csvFile) {
		final String absolutePath = csvFile.getAbsolutePath();
		LOG.info(String.format("stage %s...", absolutePath));
		Collection<String> header = CsvUtils.getHeader(absolutePath);
		Collection<CSVRecord> records = CsvUtils.getRecords(absolutePath).stream().filter(CsvUtils.FILTER_NO_EMPTY)
				.collect(Collectors.toList());
		records.forEach(rec -> stage(header, rec));
	}

	private void stage(Collection<String> header, CSVRecord record) {

		final Collection<String> values = new ArrayList<>();
		header.forEach(headColumn -> {
			final String value = record.get(headColumn);

			assertNoInvalidCharacters(headColumn);
			assertNoInvalidCharacters(value);

			values.add(headColumn + "=" + value);
		});
		final XiData xiData = new XiData();
		xiData.setType(Constants.FIN_PRICE_EXCHANGE_IMPORT_TYPE);
		xiData.setValue(String.join(";", values));
		xiDataDao.save(xiData);
	}

	private void assertNoInvalidCharacters(String s) {

	}

}
