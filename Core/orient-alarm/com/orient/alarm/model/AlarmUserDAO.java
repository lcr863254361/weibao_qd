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

public class AlarmUserDAO extends HibernateDaoSupport {

    private static final Logger log = LoggerFactory.getLogger(AlarmUserDAO.class);

    public static final String USER_NAME = "userName";
    public static final String ALL_NAME = "allName";
    public static final String PASSWORD = "password";
    public static final String SEX = "sex";
    public static final String PHONE = "phone";
    public static final String POST = "post";
    public static final String SPECIALTY = "specialty";
    public static final String GRADE = "grade";
    public static final String CREATE_USER = "createUser";
    public static final String UPDATE_USER = "updateUser";
    public static final String NOTES = "notes";
    public static final String STATE = "state";
    public static final String MOBILE = "mobile";
    public static final String FLG = "flg";
    public static final String DEP_ID = "depId";
    public static final String IS_DEL = "isDel";
    public static final String _EMAIL = "EMail";
    public static final String LOCK_STATE = "lockState";
    public static final String LOGIN_FAILURES = "loginFailures";

    protected void initDao() {
        //do nothing
    }

    public void save(AlarmUser transientInstance) {
        log.debug("saving AlarmUser instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AlarmUser persistentInstance) {
        log.debug("deleting AlarmUser instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AlarmUser findById(java.lang.String id) {
        log.debug("getting AlarmUser instance with id: " + id);
        try {
            AlarmUser instance = (AlarmUser) getHibernateTemplate()
                    .get("AlarmUser", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AlarmUser instance) {
        log.debug("finding AlarmUser instance by example");
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
        log.debug("finding AlarmUser instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AlarmUser as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByUserName(Object userName) {
        return findByProperty(USER_NAME, userName);
    }

    public List findByAllName(Object allName) {
        return findByProperty(ALL_NAME, allName);
    }

    public List findByPassword(Object password) {
        return findByProperty(PASSWORD, password);
    }

    public List findBySex(Object sex) {
        return findByProperty(SEX, sex);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByPost(Object post) {
        return findByProperty(POST, post);
    }

    public List findBySpecialty(Object specialty) {
        return findByProperty(SPECIALTY, specialty);
    }

    public List findByGrade(Object grade) {
        return findByProperty(GRADE, grade);
    }

    public List findByCreateUser(Object createUser) {
        return findByProperty(CREATE_USER, createUser);
    }

    public List findByUpdateUser(Object updateUser) {
        return findByProperty(UPDATE_USER, updateUser);
    }

    public List findByNotes(Object notes) {
        return findByProperty(NOTES, notes);
    }

    public List findByState(Object state) {
        return findByProperty(STATE, state);
    }

    public List findByMobile(Object mobile) {
        return findByProperty(MOBILE, mobile);
    }

    public List findByFlg(Object flg) {
        return findByProperty(FLG, flg);
    }

    public List findByDepId(Object depId) {
        return findByProperty(DEP_ID, depId);
    }

    public List findByIsDel(Object isDel) {
        return findByProperty(IS_DEL, isDel);
    }

    public List findByEMail(Object EMail) {
        return findByProperty(_EMAIL, EMail);
    }

    public List findByLockState(Object lockState) {
        return findByProperty(LOCK_STATE, lockState);
    }

    public List findByLoginFailures(Object loginFailures) {
        return findByProperty(LOGIN_FAILURES, loginFailures);
    }

    public List findAll() {
        log.debug("finding all AlarmUser instances");
        try {
            String queryString = "from AlarmUser";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AlarmUser merge(AlarmUser detachedInstance) {
        log.debug("merging AlarmUser instance");
        try {
            AlarmUser result = (AlarmUser) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AlarmUser instance) {
        log.debug("attaching dirty AlarmUser instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AlarmUser instance) {
        log.debug("attaching clean AlarmUser instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<AlarmUser> getAlarmUserByIds(final String userIds) {

        return getHibernateTemplate().execute(new HibernateCallback<List<AlarmUser>>() {
            @Override
            public List<AlarmUser> doInHibernate(Session session) throws HibernateException, SQLException {
                String hsql = "from AlarmUser a where a.id in('" + userIds.replace(",", "','") + "')";
                Query query = session.createQuery(hsql);
                return query.list();
            }
        });
    }

    public List<AlarmUser> getAlarmUserByNames(final String userNames) {

        return getHibernateTemplate().execute(new HibernateCallback<List<AlarmUser>>() {
            @Override
            public List<AlarmUser> doInHibernate(Session session) throws HibernateException, SQLException {
                String hsql = "from AlarmUser a where a.userName in('" + userNames.replace(",", "','") + "')";
                Query query = session.createQuery(hsql);
                return query.list();

            }
        });
    }

    public static AlarmUserDAO getFromApplicationContext(ApplicationContext ctx) {
        return (AlarmUserDAO) ctx.getBean("AlarmUserDAO");
    }

}