package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.ArithViewAttribute;
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
 * ArithViewAttributeDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ArithViewAttributeDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ArithViewAttributeDAO.class);

//	public static final String VALUE = "value";
//	public static final String DISPLAY_VALUE = "displayValue";
//	public static final String IMAGE_URL = "imageURL";
//	public static final String DESCRIPTION = "description";
    /**
     * The Constant VIEW_ID.
     */
    public static final String VIEW_ID = "view";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(ArithViewAttribute transientInstance) throws Exception {
        log.debug("saving ArithViewAttribute instance");
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
    public void delete(ArithViewAttribute persistentInstance) throws Exception {
        log.debug("deleting ArithViewAttribute instance");
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
     * @return the arith view attribute
     */
    public ArithViewAttribute findById(java.lang.String id) {
        log.debug("getting ArithViewAttribute instance with id: " + id);
        try {
            ArithViewAttribute instance = (ArithViewAttribute) getHibernateTemplate().get("com.orient.metamodel.metadomain.ArithViewAttribute", id);
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
    public List findByExample(ArithViewAttribute instance) {
        log.debug("finding ArithViewAttribute instance by example");
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
        log.debug("finding ArithViewAttribute instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ArithViewAttribute as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by view id.
     *
     * @param viewId the view id
     * @return the list
     */
    public List findByViewId(Object viewId) {
        return findByProperty(VIEW_ID, viewId);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all ArithViewAttribute instances");
        try {
            String queryString = "from ArithViewAttribute";
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
     * @return the arith view attribute
     * @throws Exception the exception
     */
    public ArithViewAttribute merge(ArithViewAttribute detachedInstance) throws Exception {
        log.debug("merging ArithViewAttribute instance");
        try {
            ArithViewAttribute result = (ArithViewAttribute) getHibernateTemplate()
                    .merge(detachedInstance);
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
    public void attachDirty(ArithViewAttribute instance) {
        log.debug("attaching dirty ArithViewAttribute instance");
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
    public void attachClean(ArithViewAttribute instance) {
        log.debug("attaching clean ArithViewAttribute instance");
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
    public static ArithViewAttributeDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ArithViewAttributeDAO) ctx.getBean("ArithViewAttributeDAO");
    }

}