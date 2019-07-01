package de.slag.finance.importer;

import java.util.Comparator;

import org.apache.commons.csv.CSVRecord;

public class FinImportCSVRecordComparator implements Comparator<CSVRecord>{

	@Override
	public int compare(CSVRecord arg0, CSVRecord arg1) {
		return arg0.get("Datum").compareTo(arg1.get("Datum"));
	}

}
