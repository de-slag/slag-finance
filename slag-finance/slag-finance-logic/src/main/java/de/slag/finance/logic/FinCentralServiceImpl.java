package de.slag.finance.logic;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.common.context.SlagContext;
import de.slag.finance.data.model.KPI;

@Service
public class FinCentralServiceImpl implements FinCentralService {

	private static final Log LOG = LogFactory.getLog(FinCentralServiceImpl.class);

	private FinSecondaryService finSecondaryService = SlagContext.getBean(FinSecondaryService.class);

	public void doNothing() {
		LOG.info("service: " + finSecondaryService);
	}

}
