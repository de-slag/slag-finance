package de.slag.finance.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.context.SlagContext;

@Service
public class FinCentralServiceImpl implements FinCentralService {

	private static final Log LOG = LogFactory.getLog(FinCentralServiceImpl.class);

	private FinSecondaryService finSecondaryService = SlagContext.getBean(FinSecondaryService.class);

	public void doNothing() {
		LOG.info("service: " + finSecondaryService);
	}

}
