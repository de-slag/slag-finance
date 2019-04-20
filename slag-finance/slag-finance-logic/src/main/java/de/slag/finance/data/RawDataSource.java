package de.slag.finance.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.csv.CSVRecord;

import de.slag.common.base.BaseException;
import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.SlagConfigSupportUtils;
import de.slag.finance.data.model.RawDataPoint;
import de.slag.finance.data.model.FinRawDataHolder;
import de.slag.finance.logic.FinProperties;

public class RawDataSource implements FinRawDataSource<RawDataPoint> {

	private String filename;

	public RawDataSource() {
		Properties configProperties = SlagConfigSupportUtils.getConfigProperties();
		FinProperties finProperties = new FinProperties(configProperties);
		filename = finProperties.getFinanceWorkdir() + "/" + finProperties.getRawDataFile();

	}
	
	public Collection<Long> findAllIds() {
		final Collection<CSVRecord> records = getRecords();

		final List<Long> collect = records.stream().map(record -> record.get("ID")).map(id -> Long.valueOf(id))
				.collect(Collectors.toList());

		return new ArrayList<>(collect);
	}

	private Collection<CSVRecord> getRecords() {
		try {
			return CsvUtils.getRecords(filename);
		} catch (IOException e) {
			throw new BaseException(e);
		}
	}
	

	@Override
	public Collection<FinRawDataHolder<RawDataPoint>> findAll() {
		final Collection<CSVRecord> records = getRecords();
		
		// TODO Auto-generated method stub
		return null;
	}
	
	private class SourceRawDataHolder implements FinRawDataHolder<RawDataPoint> {
		
		private CSVRecord record;

		public SourceRawDataHolder(CSVRecord record) {
			this.record = record;
		}

		@Override
		public RawDataPoint get() {
			final RawDataPoint rawDataPoint = new RawDataPoint();
			
			mapping
			
			return rawDataPoint;
		}
		
	}

}
