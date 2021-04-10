package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.View;
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
 * ViewDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ViewDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ViewDAO.class);

    /**
     * The Constant NAME.
     */
    public static final String NAME = "name";

    /**
     * The Constant DISPLAY_NAME.
     */
    public static final String DISPLAY_NAME = "displayName";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant EXPRESSION.
     */
    public static final String EXPRESSION = "expression";

    /**
     * The Constant TYPE.
     */
    public static final String TYPE = "type";

    /**
     * The Constant IS_VALID.
     */
    public static final String IS_VALID = "isValid";

    /**
     * The Constant VIEW_SQL.
     */
    public static final String VIEW_SQL = "viewSql";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(View transientInstance) throws Exception {
        log.debug("saving View instance");
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
    public void delete(View persistentInstance) throws Exception {
        log.debug("deleting View instance");
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
     * @return the view
     */
    public View findById(java.lang.String id) {
        log.debug("getting View instance with id: " + id);
        try {
            View instance = (View) getHibernateTemplate().get("com.orient.metamodel.metadomain.View", id);
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
    public List findByExample(View instance) {
        log.debug("finding View instance by example");
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
        log.debug("finding View instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from View as model where model." + propertyName + "= ?";
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
     * Find by description.
     *
     * @param description the description
     * @return the list
     */
    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find by expression.
     *
     * @param expression the expression
     * @return the list
     */
    public List findByExpression(Object expression) {
        return findByProperty(EXPRESSION, expression);
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
     * Find by is valid.
     *
     * @param isValid the is valid
     * @return the list
     */
    public List findByIsValid(Object isValid) {
        return findByProperty(IS_VALID, isValid);
    }

    /**
     * Find by view sql.
     *
     * @param viewSql the view sql
     * @return the list
     */
    public List findByViewSql(Object viewSql) {
        return findByProperty(VIEW_SQL, viewSql);
    }


    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all View instances");
        try {
            String queryString = "from View";
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
     * @return the view
     * @throws Exception the exception
     */
    public View merge(View detachedInstance) throws Exception {
        log.debug("merging View instance");
        try {
            View result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(View instance) {
        log.debug("attaching dirty View instance");
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
    public void attachClean(View instance) {
        log.debug("attaching clean View instance");
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
    public static ViewDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ViewDAO) ctx.getBean("ViewDAO");
    }

}