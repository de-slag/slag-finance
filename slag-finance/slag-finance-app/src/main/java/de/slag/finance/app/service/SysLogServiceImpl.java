package de.slag.finance.app.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import de.slag.common.Dao;
import de.slag.common.model.beans.SysLog;
import de.slag.common.model.beans.SysLog.Severity;
import de.slag.common.model.dao.SysLogDao;
import de.slag.common.model.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private SysLogDao sysLogDao;


	@Override
	public Dao<SysLog> getDao() {
		return sysLogDao;
	}
	
	@Override
	public void log(Class<?> clazz, Severity severity, String info) {
		SysLogService.super.log(clazz, severity, StringUtils.abbreviate(info, 4000));
	}

}
