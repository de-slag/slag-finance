package de.slag.finance.app.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import de.slag.common.context.SubClassesUtils;
import de.slag.common.db.h2.InMemoryProperties;
import de.slag.common.db.hibernate.HibernateResource;
import de.slag.common.db.hibernate.SessionFactoryUtils;
import de.slag.common.model.EntityBean;

@Component
public class FinHibernateResource implements HibernateResource {

	private SessionFactory sessionFactory;

	@PostConstruct
	public void setUp() {
		final Collection<Class<?>> registerClasses = SubClassesUtils.findAllSubclassesOf(EntityBean.class);
		sessionFactory = SessionFactoryUtils.createSessionFactory(getDbProperties(),
				new ArrayList<>(registerClasses));
	}

	@Override
	public InMemoryProperties getDbProperties() {
		return new InMemoryProperties();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
