package com.orient.alarm.model;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.List;

public class AlarmInfoDAO extends HibernateDaoSupport {

    public static final String NLEVEL = "nlevel";
    public static final String CLASSNAME = "classname";
    public static final String PARAMS = "params";
    private static final Logger log = LoggerFactory.getLogger(AlarmInfoDAO.class);

    public static AlarmInfoDAO getFromApplicationContext(ApplicationContext ctx) {
        return (AlarmInfoDAO) ctx.getBean("AlarmInfoDAO");
    }

    protected void initDao() {
        //do nothing
    }

    public void save(AlarmInfo transientInstance) {
        log.debug("saving AlarmInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AlarmInfo persistentInstance) {
        log.debug("deleting AlarmInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AlarmInfo findById(java.lang.String id) {
        log.debug("getting AlarmInfo instance with id: " + id);
        try {
            AlarmInfo instance = (AlarmInfo) getHibernateTemplate()
                    .get(AlarmInfo.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AlarmInfo instance) {
        log.debug("finding AlarmInfo instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AlarmInfo instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AlarmInfo as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByNlevel(Object nlevel) {
        return findByProperty(NLEVEL, nlevel
        );
    }

    public List findByClassname(Object classname) {
        return findByProperty(CLASSNAME, classname
        );
    }

    public List findByParams(Object params) {
        return findByProperty(PARAMS, params
        );
    }

    public List findAll() {
        log.debug("finding all AlarmInfo instances");
        try {
            String queryString = "from AlarmInfo";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AlarmInfo merge(AlarmInfo detachedInstance) {
        log.debug("merging AlarmInfo instance");
        try {
            AlarmInfo result = (AlarmInfo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AlarmInfo instance) {
        log.debug("attaching dirty AlarmInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AlarmInfo instance) {
        log.debug("attaching clean AlarmInfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<AlarmInfo> getUnTriggerAlarms() {

        return getHibernateTemplate().execute(new HibernateCallback<List<AlarmInfo>>() {
            @Override
            public List<AlarmInfo> doInHibernate(Session session) throws HibernateException, SQLException {
                String hsql = "from AlarmInfo as model where model.classname is not null";
                Query query = session.createQuery(hsql).setCacheable(true).setCacheRegion("AlarmCache");
                return query.list();
            }
        });

    }
}