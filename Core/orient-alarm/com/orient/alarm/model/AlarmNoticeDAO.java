package com.orient.alarm.model;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class AlarmNoticeDAO extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(AlarmNoticeDAO.class);

    public static final String NOTICETYPE = "noticetype";
    public static final String TRIGGERTYPE = "triggertype";
    public static final String TIMETYPE = "timetype";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String WEEK = "week";
    public static final String TIME = "time";


    protected void initDao() {
        //do nothing
    }

    public void save(AlarmNotice transientInstance) {
        log.debug("saving AlarmNotice instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AlarmNotice persistentInstance) {
        log.debug("deleting AlarmNotice instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AlarmNotice findById(java.lang.String id) {
        log.debug("getting AlarmNotice instance with id: " + id);
        try {
            AlarmNotice instance = (AlarmNotice) getHibernateTemplate()
                    .get("AlarmNotice", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List findByExample(AlarmNotice instance) {
        log.debug("finding AlarmNotice instance by example");
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
        log.debug("finding AlarmNotice instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AlarmNotice as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByNoticetype(Object noticetype) {
        return findByProperty(NOTICETYPE, noticetype);
    }

    public List findByTriggertype(Object triggertype) {
        return findByProperty(TRIGGERTYPE, triggertype);
    }

    public List findByTimetype(Object timetype) {
        return findByProperty(TIMETYPE, timetype);
    }

    public List findByYear(Object year) {
        return findByProperty(YEAR, year);
    }

    public List findByMonth(Object month) {
        return findByProperty(MONTH, month);
    }

    public List findByWeek(Object week) {
        return findByProperty(WEEK, week);
    }

    public List findByTime(Object time) {
        return findByProperty(TIME, time);
    }


    public List findAll() {
        log.debug("finding all AlarmNotice instances");
        try {
            String queryString = "from AlarmNotice";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AlarmNotice merge(AlarmNotice detachedInstance) {
        log.debug("merging AlarmNotice instance");
        try {
            AlarmNotice result = (AlarmNotice) getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AlarmNotice instance) {
        log.debug("attaching dirty AlarmNotice instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AlarmNotice instance) {
        log.debug("attaching clean AlarmNotice instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public static AlarmNoticeDAO getFromApplicationContext(ApplicationContext ctx) {
        return (AlarmNoticeDAO) ctx.getBean("AlarmNoticeDAO");
    }

}