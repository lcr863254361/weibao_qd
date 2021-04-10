package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.ViewResultColumn;
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
 * ViewResultColumnDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ViewResultColumnDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ViewResultColumnDAO.class);

    /**
     * The Constant VIEW_ID.
     */
    public static final String VIEW_ID = "cwmViews";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(ViewResultColumn transientInstance) throws Exception {
        log.debug("saving ViewResultColumn instance");
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
    public void delete(ViewResultColumn persistentInstance) throws Exception {
        log.debug("deleting ViewResultColumn instance");
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
     * @return the view result column
     */
    public ViewResultColumn findById(java.lang.String id) {
        log.debug("getting ViewResultColumn instance with id: " + id);
        try {
            ViewResultColumn instance = (ViewResultColumn) getHibernateTemplate().get("com.orient.metamodel.metadomain.ViewResultColumn", id);
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
    public List findByExample(ViewResultColumn instance) {
        log.debug("finding ViewResultColumn instance by example");
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
        log.debug("finding ViewResultColumn instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ViewResultColumn as model where model." + propertyName + "= ?";
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
        log.debug("finding all ViewResultColumn instances");
        try {
            String queryString = "from ViewResultColumn";
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
     * @return the view result column
     * @throws Exception the exception
     */
    public ViewResultColumn merge(ViewResultColumn detachedInstance) throws Exception {
        log.debug("merging ViewResultColumn instance");
        try {
            ViewResultColumn result = (ViewResultColumn) getHibernateTemplate()
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
    public void attachDirty(ViewResultColumn instance) {
        log.debug("attaching dirty ViewResultColumn instance");
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
    public void attachClean(ViewResultColumn instance) {
        log.debug("attaching clean ViewResultColumn instance");
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
    public static ViewResultColumnDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ViewResultColumnDAO) ctx.getBean("ViewResultColumnDAO");
    }

}