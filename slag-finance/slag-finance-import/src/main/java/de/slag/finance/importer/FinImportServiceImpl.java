package de.slag.finance.importer;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.api.dao.XiDataDao;
import de.slag.common.base.BaseException;
import de.slag.common.utils.CsvUtils;
import de.slag.finance.api.FinImportService;
import de.slag.finance.api.FinPriceDao;

@Service
public class FinImportServiceImpl implements FinImportService {

	private static final Log LOG = LogFactory.getLog(FinImportServiceImpl.class);
	
	@Resource
	private XiDataDao xiDataDao;
	
	@Resource
	private FinPriceDao finPriceDao;


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
	
	@Override
	public void importData() {
		LOG.info("import data...");
		final FinPriceImportRunner finPriceImportRunner = new FinPriceImportRunner(finPriceDao, xiDataDao);
		finPriceImportRunner.run();
	}
}
