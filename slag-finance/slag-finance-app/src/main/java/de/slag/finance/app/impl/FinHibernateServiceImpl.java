package de.slag.finance.app.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import de.slag.common.db.h2.InMemoryProperties;
import de.slag.common.db.hibernate.AbstractHibernateServiceImpl;
import de.slag.common.db.hibernate.HibernateService;

@Service
public class FinHibernateServiceImpl extends AbstractHibernateServiceImpl implements HibernateService {

	@Override
	protected Properties getProperties() {
		return new InMemoryProperties();
	}

}
