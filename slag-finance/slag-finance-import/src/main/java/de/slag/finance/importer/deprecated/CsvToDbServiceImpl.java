package de.slag.finance.importer.deprecated;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.common.utils.CsvUtils;
import de.slag.finance.deprecated.FinDataPoint;
import de.slag.finance.deprecated.FinDataPointDao;

//@Service
public class CsvToDbServiceImpl {

	private FinDataPointDao finDataPointDao = SlagContext.getBean(FinDataPointDao.class);

	public void readIn(String absoluteCsvFileName, Function<CSVRecord, FinDataPoint> transformer) {
		final Collection<CSVRecord> records = CsvUtils.getRecords(absoluteCsvFileName);
		
		final List<FinDataPoint> collect = records.stream().map(transformer).collect(Collectors.toList());
		collect.forEach(dp -> finDataPointDao.save(dp));

	}
}
