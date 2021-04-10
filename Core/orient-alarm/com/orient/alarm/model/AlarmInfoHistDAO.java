package com.orient.alarm.model;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AlarmInfoHistDAO extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(AlarmInfoHistDAO.class);

    public static final String NLEVEL = "nlevel";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String URL = "url";

    protected void initDao() {
        //do nothing
    }

    public void save(AlarmInfoHist transientInstance) {
        log.debug("saving AlarmInfoHist instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AlarmInfoHist persistentInstance) {
        log.debug("deleting AlarmInfoHist instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AlarmInfoHist findById(java.lang.String id) {
        log.debug("getting AlarmInfoHist instance with id: " + id);
        try {
            AlarmInfoHist instance = (AlarmInfoHist) getHibernateTemplate()
                    .get("AlarmInfoHist", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AlarmInfoHist instance) {
        log.debug("finding AlarmInfoHist instance by example");
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
        log.debug("finding AlarmInfoHist instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AlarmInfoHist as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByNlevel(Object nlevel) {
        return findByProperty(NLEVEL, nlevel);
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
        log.debug("finding all AlarmInfoHist instances");
        try {
            String queryString = "from AlarmInfoHist";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AlarmInfoHist merge(AlarmInfoHist detachedInstance) {
        log.debug("merging AlarmInfoHist instance");
        try {
            AlarmInfoHist result =  getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AlarmInfoHist instance) {
        log.debug("attaching dirty AlarmInfoHist instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AlarmInfoHist instance) {
        log.debug("attaching clean AlarmInfoHist instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List getMyAlarmHist(final String userId) {

        List alarmHist = getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = " select a.* from alarm_info_hist a, alarm_user_hist b";
                sql += " where a.id = b.alarmId and b.userId=:userId";
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(AlarmInfoHist.class);
                query.setString("userId", userId);
                return query.list();
            }

        });

        return alarmHist;
    }

    public List getMyAlarm(final String userId) {

        List myAlarmList = getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = " select a.* from alarm_info a, alarm_user b";
                sql += " where a.id = b.alarmId and b.userId=:userId";
                sql += " and a.isAlarm=:isAlarm";
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(AlarmInfo.class);
                query.setString("userId", userId);
                query.setString("isAlarm", "1");
                return query.list();
            }

        });

        return myAlarmList;
    }

    public List getMyAlarm(final String userId, final int start, final int limit) {

        List myAlarmList = getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {

                String sql = " select a.* from alarm_info a, alarm_user b";
                sql += " where a.id = b.alarmId and b.userId=:userId";
                sql += " and a.isAlarm=:isAlarm";
                sql += " order by to_number(a.id) desc";
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(AlarmInfo.class);
                query.setString("userId", userId);
                query.setString("isAlarm", "1");
                query.setFirstResult(start);
                query.setMaxResults(limit);
                return query.list();
            }

        });

        return myAlarmList;
    }

    public int getMyAlarmCnt(final String userId) {

        int myAlarmCnt = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = " select count(a.id) from alarm_info a, alarm_user b";
                sql += " where a.id = b.alarmId and b.userId=:userId";
                sql += " and a.isAlarm=:isAlarm";
                sql += " order by to_number(a.id) desc";
                SQLQuery query = session.createSQLQuery(sql);
                query.setString("userId", userId);
                query.setString("isAlarm", "1");
                return ((BigDecimal) query.uniqueResult()).intValue();
            }

        });

        return myAlarmCnt;
    }

    public static AlarmInfoHistDAO getFromApplicationContext(ApplicationContext ctx) {
        return (AlarmInfoHistDAO) ctx.getBean("AlarmInfoHistDAO");
    }

}