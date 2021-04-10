package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.ConsExpression;
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
 * ConsExpressionDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ConsExpressionDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ConsExpressionDAO.class);

    /**
     * The Constant EXPRESSION.
     */
    public static final String EXPRESSION = "expression";

    /**
     * The Constant RESULT.
     */
    public static final String RESULT = "result";

    /**
     * The Constant PRI.
     */
    public static final String PRI = "pri";

    /**
     * The Constant TABLE_ID.
     */
    public static final String TABLE_ID = "table";

    protected void initDao() {
        // do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     */
    public void save(ConsExpression transientInstance) {
        log.debug("saving ConsExpression instance");
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
     */
    public void delete(ConsExpression persistentInstance) {
        log.debug("deleting ConsExpression instance");
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
     * @return the cons expression
     */
    public ConsExpression findById(java.lang.String id) {
        log.debug("getting ConsExpression instance with id: " + id);
        try {
            ConsExpression instance = (ConsExpression) getHibernateTemplate().get("com.orient.metamodel.metadomain.ConsExpression", id);
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
    public List findByExample(ConsExpression instance) {
        log.debug("finding ConsExpression instance by example");
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
        log.debug("finding ConsExpression instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ConsExpression as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
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
     * Find by result.
     *
     * @param result the result
     * @return the list
     */
    public List findByResult(Object result) {
        return findByProperty(RESULT, result);
    }

    /**
     * Find by table id.
     *
     * @param tableId the table id
     * @return the list
     */
    public List findByTableId(Object tableId) {
        return findByProperty(TABLE_ID, tableId);
    }

    /**
     * Find by pri.
     *
     * @param pri the pri
     * @return the list
     */
    public List findByPri(Object pri) {
        return findByProperty(PRI, pri);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all ConsExpression instances");
        try {
            String queryString = "from ConsExpression";
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
     * @return the cons expression
     */
    public ConsExpression merge(ConsExpression detachedInstance) {
        log.debug("merging ConsExpression instance");
        try {
            ConsExpression result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(ConsExpression instance) {
        log.debug("attaching dirty ConsExpression instance");
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
    public void attachClean(ConsExpression instance) {
        log.debug("attaching clean ConsExpression instance");
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
    public static ConsExpressionDAO getFromApplicationContext(
            ApplicationContext ctx) {
        return (ConsExpressionDAO) ctx.getBean("ConsExpressionDAO");
    }

}