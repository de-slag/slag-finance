import java.io.IOException;
import java.util.Collection;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.CsvUtils;
import de.slag.common.utils.ResourceUtils;

public class RawDataTest {
	
	private static final Log LOG = LogFactory.getLog(RawDataTest.class);

	@Before
	public void setup() {
		LoggingUtils.activateLogging();
	}
	
	@Test
	public void test() throws IOException {

		final String filename = "test-data.raw.csv";
		final Collection<CSVRecord> records = CsvUtils.getRecords(ResourceUtils.getAbsolutePathFromResources(filename));
		Assert.assertTrue(!records.isEmpty());
		
		records.forEach(record -> LOG.info(record));
		

	}

}
