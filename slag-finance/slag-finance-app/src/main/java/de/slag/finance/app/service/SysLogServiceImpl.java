package de.slag.finance.app.service;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.common.model.beans.SysLog;
import de.slag.common.model.beans.SysLog.Severity;
import de.slag.common.model.beans.SysLogEntry;
import de.slag.common.model.dao.SysLogDao;
import de.slag.common.model.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private SysLogDao sysLogDao;

	@Override
	public void log(Severity severity, String info) {
		SysLog sysLog = new SysLog();
		sysLog.setSeverity(severity);
		sysLog.setInfo(info);
		sysLogDao.save(sysLog);
	}

	@Override
	public Collection<SysLogEntry> findBy(Predicate<SysLogEntry> filter) {
		return sysLogDao.findAll().stream().filter(filter).collect(Collectors.toList());
	}

}
