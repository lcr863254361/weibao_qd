package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.ViewRefColumn;
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
 * ViewRefColumnDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ViewRefColumnDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ViewRefColumnDAO.class);

//    public static final String VIEW_ID = "cwmViews";
    /**
     * The Constant VIEW_ID.
     */
    public static final String VIEW_ID = "viewid";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(ViewRefColumn transientInstance) throws Exception {
        log.debug("saving ViewRefColumn instance");
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
    public void delete(ViewRefColumn persistentInstance) throws Exception {
        log.debug("deleting ViewRefColumn instance");
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
     * @return the view ref column
     */
    public ViewRefColumn findById(java.lang.String id) {
        log.debug("getting ViewRefColumn instance with id: " + id);
        try {
            ViewRefColumn instance = (ViewRefColumn) getHibernateTemplate().get("com.orient.metamodel.metadomain.ViewRefColumn", id);
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
    public List findByExample(ViewRefColumn instance) {
        log.debug("finding ViewRefColumn instance by example");
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
        log.debug("finding ViewRefColumn instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ViewRefColumn as model where model." + propertyName + "= ?";
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
        log.debug("finding all ViewRefColumn instances");
        try {
            String queryString = "from ViewRefColumn";
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
     * @return the view ref column
     * @throws Exception the exception
     */
    public ViewRefColumn merge(ViewRefColumn detachedInstance) throws Exception {
        log.debug("merging ViewRefColumn instance");
        try {
            ViewRefColumn result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(ViewRefColumn instance) {
        log.debug("attaching dirty ViewRefColumn instance");
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
    public void attachClean(ViewRefColumn instance) {
        log.debug("attaching clean ViewRefColumn instance");
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
    public static ViewRefColumnDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ViewRefColumnDAO) ctx.getBean("ViewRefColumnDAO");
    }

}