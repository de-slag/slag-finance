package de.slag.finance.interfaces.ov;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.common.XiDataDao;
import de.slag.finance.IsinWknDao;
import de.slag.finance.api.AvailableProperties;
import de.slag.finance.api.FinAdminSupport;
import de.slag.finance.api.FinStageService;

@Service
public class FinOvStageServiceImpl implements FinStageService {
	
	@Resource
	private XiDataDao xiDataDao;
	
	@Resource
	private IsinWknDao isinWknDao;

	public void stage() {
		final Path path = Paths.get(FinAdminSupport.getSafe(AvailableProperties.IMPORT_DIR));
		FinRawDataStageRunner finRawDataStageRunner = new FinRawDataStageRunner(path, xiDataDao, isinWknDao);
		finRawDataStageRunner.run();
	}
}
