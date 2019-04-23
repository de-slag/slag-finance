package de.slag.finance.data;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

import de.slag.common.base.BaseException;
import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.DateUtils;
import de.slag.common.utils.SlagConfigSupportUtils;
import de.slag.finance.Constants;
import de.slag.finance.data.model.KPI;
import de.slag.finance.data.model.RawDataPoint;
import de.slag.finance.logic.FinProperties;

public class RawDataSource implements FinRawDataSource<RawDataPoint> {

	private static final String DEFAULT_SDF = "yyyy-MM-dd";

	private Collection<String> filenames = new ArrayList<String>();

	private Function<CSVRecord, RawDataPoint> f = csv -> {
		final RawDataPoint rdp = new RawDataPoint();

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_SDF);
		final Date parse;
		try {
			parse = simpleDateFormat.parse(csv.get("DATE"));
		} catch (ParseException e) {
			throw new BaseException(e);
		}
		rdp.setDate(DateUtils.toLocalDate(parse));

		rdp.setIsin(csv.get("ISIN"));

		final String valueString = csv.get("VALUE");
		final Double valueDouble = Double.valueOf(valueString);
		rdp.setValue(BigDecimal.valueOf(valueDouble));

		return rdp;
	};

	public RawDataSource() {
		final String financeWorkdir = FinProperties.financeWorkdir();
		final File dir = new File(financeWorkdir);
		if (!dir.exists()) {
			throw new BaseException("dir not exists: " + financeWorkdir);
		}

		final String property = FinProperties.property(Constants.RAW_DATA_POSTFIX);

		final File[] listFiles = dir.listFiles((currentdir, filename) -> {
			return filename.endsWith(property);
		});

		filenames.addAll(Arrays.stream(listFiles).map(file -> file.getAbsolutePath()).collect(Collectors.toList()));

	}

	public Collection<Long> findAllIds() {
		final Collection<CSVRecord> records = getRecords();

		final List<Long> collect = records.stream().map(record -> record.get("ID")).map(id -> Long.valueOf(id))
				.collect(Collectors.toList());

		return new ArrayList<>(collect);
	}

	private Collection<CSVRecord> getRecords() {
		return filenames.stream().flatMap(filename -> {
			try {
				return CsvUtils.getRecords(filename).stream();
			} catch (IOException e) {
				throw new BaseException(e);
			}
		}).collect(Collectors.toList());

	}

	@Override
	public Collection<RawDataPoint> findAll() {
		final Collection<CSVRecord> records = getRecords();

		final Collection<RawDataPoint> collect = records.stream().map(f).collect(Collectors.toList());

		return new ArrayList<>(collect);
	}
}
