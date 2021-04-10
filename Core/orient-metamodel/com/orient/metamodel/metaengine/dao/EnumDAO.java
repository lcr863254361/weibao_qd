package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Enum;
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
 * EnumDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class EnumDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(EnumDAO.class);

    /**
     * The Constant VALUE.
     */
    public static final String VALUE = "value";

    /**
     * The Constant DISPLAY_VALUE.
     */
    public static final String DISPLAY_VALUE = "displayValue";

    /**
     * The Constant IMAGE_URL.
     */
    public static final String IMAGE_URL = "imageURL";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant RESTRICTION_ID.
     */
    public static final String RESTRICTION_ID = "restrictionID";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(Enum transientInstance) throws Exception {
        log.debug("saving Enum instance");
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
    public void delete(Enum persistentInstance) throws Exception {
        log.debug("deleting Enum instance");
        try {
            getHibernateTemplate().evict(persistentInstance);
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
     * @return the enum
     */
    public Enum findById(java.lang.String id) {
        log.debug("getting Enum instance with id: " + id);
        try {
            Enum instance = (Enum) getHibernateTemplate().get("com.orient.metamodel.metadomain.Enum", id);
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
    public List findByExample(Enum instance) {
        log.debug("finding Enum instance by example");
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
        log.debug("finding Enum instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Enum as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by value.
     *
     * @param value the value
     * @return the list
     */
    public List findByValue(Object value) {
        return findByProperty(VALUE, value);
    }

    /**
     * Find by restriction id.
     *
     * @param restrictionId the restriction id
     * @return the list
     */
    public List findByRestrictionId(Object restrictionId) {
        return findByProperty(RESTRICTION_ID, restrictionId);
    }

    /**
     * Find by display value.
     *
     * @param displayValue the display value
     * @return the list
     */
    public List findByDisplayValue(Object displayValue) {
        return findByProperty(DISPLAY_VALUE, displayValue);
    }

    /**
     * Find by image url.
     *
     * @param imageURL the image url
     * @return the list
     */
    public List findByImageURL(Object imageURL) {
        return findByProperty(IMAGE_URL, imageURL);
    }

    /**
     * Find by description.
     *
     * @param description the description
     * @return the list
     */
    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all Enum instances");
        try {
            String queryString = "from Enum";
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
     * @return the enum
     * @throws Exception the exception
     */
    public Enum merge(Enum detachedInstance) throws Exception {
        log.debug("merging Enum instance");
        try {
            Enum result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(Enum instance) {
        log.debug("attaching dirty Enum instance");
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
    public void attachClean(Enum instance) {
        log.debug("attaching clean Enum instance");
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
    public static EnumDAO getFromApplicationContext(ApplicationContext ctx) {
        return (EnumDAO) ctx.getBean("EnumDAO");
    }

}