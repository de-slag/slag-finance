package de.slag.finance3.logic.importer;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.common.utils.CsvUtils;

@Service
public class FinImportServiceImpl implements FinImportService {

	private static final Log LOG = LogFactory.getLog(FinImportServiceImpl.class);

	private final Predicate<? super File> FILTER_FILE_CSV = file -> file.getName()
			.endsWith(".csv");

	final Function<? super File, ? extends Stream<? extends CSVRecord>> FLATMAP_FILE_TO_CSV_RECORDS = file -> CsvUtils
			.getRecords(file.getAbsolutePath())
			.stream();

	@Override
	public void importData(Path importDir) {
		Objects.requireNonNull(importDir, "import dir must not be null");

		final File folder = importDir.toFile();
		if (!folder.isDirectory()) {
			throw new BaseException(String.format("not a directory: '%s'", importDir));
		}

		final List<File> filesList = Arrays.asList(folder.listFiles());
		LOG.info(String.format("import data from: '%s'", importDir));
		final List<CSVRecord> collect = filesList.stream()
				.filter(FILTER_FILE_CSV)
				.flatMap(FLATMAP_FILE_TO_CSV_RECORDS)
				.collect(Collectors.toList());
		
		
		
		collect.forEach(System.out::println);
		
		
	}
}
