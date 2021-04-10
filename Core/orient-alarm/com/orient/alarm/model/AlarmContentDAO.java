package com.orient.alarm.model;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class AlarmContentDAO extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(AlarmContentDAO.class);

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String URL = "url";

    protected void initDao() {
        //do nothing
    }

    public void save(AlarmContent transientInstance) {
        log.debug("saving AlarmContent instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AlarmContent persistentInstance) {
        log.debug("deleting AlarmContent instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AlarmContent findById(java.lang.String id) {
        log.debug("getting AlarmContent instance with id: " + id);
        try {
            AlarmContent instance = (AlarmContent) getHibernateTemplate()
                    .get("AlarmContent", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List findByExample(AlarmContent instance) {
        log.debug("finding AlarmContent instance by example");
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
        log.debug("finding AlarmContent instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AlarmContent as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByTitle(Object title) {
        return findByProperty(TITLE, title);
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findByUrl(Object url) {
        return findByProperty(URL, url);
    }

    public List findAll() {
        log.debug("finding all AlarmContent instances");
        try {
            String queryString = "from AlarmContent";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AlarmContent merge(AlarmContent detachedInstance) {
        log.debug("merging AlarmContent instance");
        try {
            AlarmContent result = (AlarmContent) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AlarmContent instance) {
        log.debug("attaching dirty AlarmContent instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AlarmContent instance) {
        log.debug("attaching clean AlarmContent instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public static AlarmContentDAO getFromApplicationContext(ApplicationContext ctx) {
        return (AlarmContentDAO) ctx.getBean("AlarmContentDAO");
    }

}