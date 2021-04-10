package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.ArithAttribute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * ArithAttributeDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ArithAttributeDAO extends HibernateDaoSupport {

    /**
     * The Constant log.
     */
    private static final Log log = LogFactory.getLog(ArithAttributeDAO.class);

    /**
     * The Constant ARITH_COLUMN_ID.
     */
    public static final String ARITH_COLUMN_ID = "acolumn";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(ArithAttribute transientInstance) throws Exception {
        log.debug("saving ArithAttribute instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * Delete.
     *
     * @param persistentInstance the persistent instance
     * @throws Exception the exception
     */
    public void delete(ArithAttribute persistentInstance) throws Exception {
        log.debug("deleting ArithAttribute instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the arith attribute
     */
    public ArithAttribute findById(java.lang.String id) {
        log.debug("getting ArithAttribute instance with id: " + id);
        try {
            ArithAttribute instance = (ArithAttribute) getHibernateTemplate()
                    .get("com.orient.metamodel.metadomain.ArithAttribute", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * Find by example.
     *
     * @param instance the instance
     * @return the list
     */
    public List findByExample(ArithAttribute instance) {
        log.debug("finding ArithAttribute instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    /**
     * Find by property.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list
     */
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ArithAttribute instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ArithAttribute as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by arith column id.
     *
     * @param arithColumnId the arith column id
     * @return the list
     */
    public List findByArithColumnId(Object arithColumnId) {
        return findByProperty(ARITH_COLUMN_ID, arithColumnId);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all ArithAttribute instances");
        try {
            String queryString = "from ArithAttribute";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * Merge.
     *
     * @param detachedInstance the detached instance
     * @return the arith attribute
     * @throws Exception the exception
     */
    public ArithAttribute merge(ArithAttribute detachedInstance) throws Exception {
        log.debug("merging ArithAttribute instance");
        try {
            ArithAttribute result = getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /**
     * Attach dirty.
     *
     * @param instance the instance
     */
    public void attachDirty(ArithAttribute instance) {
        log.debug("attaching dirty ArithAttribute instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Attach clean.
     *
     * @param instance the instance
     */
    public void attachClean(ArithAttribute instance) {
        log.debug("attaching clean ArithAttribute instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Gets the from application context.
     *
     * @param ctx the ctx
     * @return the from application context
     */
    public static ArithAttributeDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ArithAttributeDAO) ctx.getBean("ArithAttributeDAO");
    }

}