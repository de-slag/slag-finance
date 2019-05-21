package de.slag.finance.test;

import java.io.File;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slag.common.base.SlagProperties;
import de.slag.common.context.SlagContext;
import de.slag.common.db.h2.InMemoryProperties;
import de.slag.common.db.hibernate.HibernateDbUpdateUtils;
import de.slag.common.logging.LoggingUtils;
import de.slag.common.utils.ResourceUtils;
import de.slag.finance.FinProperties;
import de.slag.finance.imp.RawDataImportService;
import de.slag.finance.logic.service.FinDataPointService;

public class OverAllTest {
	
	private Properties properties = SlagProperties.getConfigProperties();
	
	private RawDataImportService rawDataImportService;
	
	private FinDataPointService finDataPointService;
	
	@Before
	public void setup() {
		LoggingUtils.activateLogging(Level.DEBUG);
		
		SlagContext.init();
		rawDataImportService = SlagContext.getBean(RawDataImportService.class);
		finDataPointService = SlagContext.getBean(FinDataPointService.class);
		
		final File rawData = ResourceUtils.getFileFromResources("raw-data/dax.raw.csv");
		properties.setProperty(FinProperties.SLAG_FINANCE_IMPORTFOLDER, rawData.getParent());
		HibernateDbUpdateUtils.update(new InMemoryProperties());
	}
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		rawDataImportService.importFrom(properties.getProperty(FinProperties.SLAG_FINANCE_IMPORTFOLDER));

		Assert.assertThat(finDataPointService.count(), Matchers.is(250));
		
	}
	
	

}
