package de.slag.finance.api;

import java.nio.file.Path;

public interface FinImportService {
	
	void importData(Path importDir);

	void importData();

}
