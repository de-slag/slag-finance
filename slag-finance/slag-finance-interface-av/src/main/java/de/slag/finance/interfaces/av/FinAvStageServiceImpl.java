package de.slag.finance.interfaces.av;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.finance.api.FinStageService;

@Service
public class FinAvStageServiceImpl implements FinStageService {

	private static final Log LOG = LogFactory.getLog(FinAvStageServiceImpl.class);

	public void stage() {	
		LOG.error("not implemented yet");
	}
}
