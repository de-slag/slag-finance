package de.slag.finance.interfaces.ov;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.common.model.beans.SystemLog;
import de.slag.common.model.beans.SystemLogDao;
import de.slag.finance.IsinWknDao;
import de.slag.finance.api.AvailableProperties;
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
	private SystemLogDao systemLogDao;

	public void stage() throws StagingException {
		systemLogDao.save(new SystemLog(this.getClass().getName() + " start staging..."));
		// final Path path =
		// Paths.get(FinAdminSupport.getSafe(AvailableProperties.IMPORT_DIR));
		String workdir = FinAdminSupport.getSafe(AvailableProperties.WORKDIR);
		String stageDir = workdir + "/staging";
		final Path path = Paths.get(stageDir);
		if (!path.toFile().exists()) {
			throw new StagingException(String.format("stage directory not exists: '%s'", stageDir));
		}

		FinRawDataStageRunner finRawDataStageRunner = new FinRawDataStageRunner(path, xiDataDao, isinWknDao);
		finRawDataStageRunner.run();

		systemLogDao.save(new SystemLog(this.getClass().getName() + " staging done."));
	}
}
