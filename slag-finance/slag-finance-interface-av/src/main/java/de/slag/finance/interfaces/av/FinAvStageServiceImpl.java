package de.slag.finance.interfaces.av;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.common.base.BaseException;
import de.slag.finance.api.FinStageService;

@Service
public class FinAvStageServiceImpl implements FinStageService {

	private static final Log LOG = LogFactory.getLog(FinAvStageServiceImpl.class);

	public void stage() {
		LOG.info("simulate runtime...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new BaseException(e);
		}
		
		LOG.error("not implemented yet");
	}
}
