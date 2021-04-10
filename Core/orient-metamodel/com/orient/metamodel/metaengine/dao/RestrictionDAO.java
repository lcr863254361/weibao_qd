package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Restriction;
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
 * RestrictionDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class RestrictionDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(RestrictionDAO.class);

    /**
     * The Constant NAME.
     */
    public static final String NAME = "name";

    /**
     * The Constant DISPLAY_NAME.
     */
    public static final String DISPLAY_NAME = "displayName";

    /**
     * The Constant TYPE.
     */
    public static final String TYPE = "type";

    /**
     * The Constant ID_MULTI_SELECTED.
     */
    public static final String ID_MULTI_SELECTED = "idMultiSelected";

    /**
     * The Constant ERROR_INFO.
     */
    public static final String ERROR_INFO = "errorInfo";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant DISPLAY_TYPE.
     */
    public static final String DISPLAY_TYPE = "displayType";

    /**
     * The Constant MAX_LENGTH.
     */
    public static final String MAX_LENGTH = "maxLength";

    /**
     * The Constant MIN_LENGTH.
     */
    public static final String MIN_LENGTH = "minLength";

    /**
     * The Constant IS_VALID.
     */
    public static final String IS_VALID = "isValid";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(Restriction transientInstance) throws Exception {
        log.debug("saving Restriction instance");
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
    public void delete(Restriction persistentInstance) throws Exception {
        log.debug("deleting Restriction instance");
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
     * @return the restriction
     */
    public Restriction findById(java.lang.String id) {
        log.debug("getting Restriction instance with id: " + id);
        try {
            Restriction instance = (Restriction) getHibernateTemplate().get("com.orient.metamodel.metadomain.Restriction", id);
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
    public List findByExample(Restriction instance) {
        log.debug("finding Restriction instance by example");
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
        log.debug("finding Restriction instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Restriction as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the list
     */
    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    /**
     * Find by display name.
     *
     * @param displayName the display name
     * @return the list
     */
    public List findByDisplayName(Object displayName) {
        return findByProperty(DISPLAY_NAME, displayName);
    }

    /**
     * Find by type.
     *
     * @param type the type
     * @return the list
     */
    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    /**
     * Find by id multi selected.
     *
     * @param idMultiSelected the id multi selected
     * @return the list
     */
    public List findByIdMultiSelected(Object idMultiSelected) {
        return findByProperty(ID_MULTI_SELECTED, idMultiSelected);
    }

    /**
     * Find by error info.
     *
     * @param errorInfo the error info
     * @return the list
     */
    public List findByErrorInfo(Object errorInfo) {
        return findByProperty(ERROR_INFO, errorInfo);
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
     * Find by display type.
     *
     * @param displayType the display type
     * @return the list
     */
    public List findByDisplayType(Object displayType) {
        return findByProperty(DISPLAY_TYPE, displayType);
    }

    /**
     * Find by max length.
     *
     * @param maxLength the max length
     * @return the list
     */
    public List findByMaxLength(Object maxLength) {
        return findByProperty(MAX_LENGTH, maxLength);
    }

    /**
     * Find by min length.
     *
     * @param minLength the min length
     * @return the list
     */
    public List findByMinLength(Object minLength) {
        return findByProperty(MIN_LENGTH, minLength);
    }

    /**
     * Find by is valid.
     *
     * @param isValid the is valid
     * @return the list
     */
    public List findByIsValid(Object isValid) {
        return findByProperty(IS_VALID, isValid);
    }


    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all Restriction instances");
        try {
            String queryString = "from Restriction";
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
     * @return the restriction
     * @throws Exception the exception
     */
    public Restriction merge(Restriction detachedInstance) throws Exception {
        log.debug("merging Restriction instance");
        try {
            Restriction result = (Restriction) getHibernateTemplate()
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
    public void attachDirty(Restriction instance) {
        log.debug("attaching dirty Restriction instance");
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
    public void attachClean(Restriction instance) {
        log.debug("attaching clean Restriction instance");
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
    public static RestrictionDAO getFromApplicationContext(ApplicationContext ctx) {
        return (RestrictionDAO) ctx.getBean("RestrictionDAO");
    }

}