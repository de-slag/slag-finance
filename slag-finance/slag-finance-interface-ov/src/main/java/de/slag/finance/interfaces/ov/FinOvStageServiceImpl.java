package de.slag.finance.interfaces.ov;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.common.base.AdmService;
import de.slag.common.model.beans.SysLog.Severity;
import de.slag.common.model.service.SysLogService;
import de.slag.finance.IsinWknDao;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.api.Constants;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance.api.FinStageService;
import de.slag.finance.api.StagingException;

@Service
public class FinOvStageServiceImpl implements FinStageService {

	@Resource
	private XiDataDao xiDataDao;

	@Resource
	private IsinWknDao isinWknDao;

	@Resource
	private SysLogService sysLogService;
	
	@Resource
	private AdmService admService;

	public void stage() throws StagingException {
		sysLogService.log(Severity.INFO, this.getClass().getName() + " start staging...");
		String workdir = FinAdminSupport.getSafe(AvailableProperties.WORKDIR);
		String stageDir = workdir + "/" + Constants.SUB_WORKDIR_STAGING;
		
		
		
		
		
		
		
		
		
		
		
		final Path path = Paths.get(stageDir);
		if (!path.toFile().exists()) {
			throw new StagingException(String.format("stage directory not exists: '%s'", stageDir));
		}

		FinRawDataStageRunner finRawDataStageRunner = new FinRawDataStageRunner(path, xiDataDao, isinWknDao);
		finRawDataStageRunner.run();

		sysLogService.log(Severity.INFO, this.getClass().getName() + " staging done.");
	}
}
