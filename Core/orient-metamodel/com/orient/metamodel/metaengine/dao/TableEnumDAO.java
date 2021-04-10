package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.TableEnum;
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
 * TableEnumDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class TableEnumDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(TableEnumDAO.class);

    /**
     * The Constant EXPRESSION.
     */
    public static final String EXPRESSION = "expression";

    /**
     * The Constant TABLE_ENUM_SQL.
     */
    public static final String TABLE_ENUM_SQL = "tableEnumSql";

    /**
     * The Constant RESTRICTION_ID.
     */
    public static final String RESTRICTION_ID = "restrictionId";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(TableEnum transientInstance) throws Exception {
        log.debug("saving TableEnum instance");
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
    public void delete(TableEnum persistentInstance) throws Exception {
        log.debug("deleting TableEnum instance");
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
     * @return the table enum
     */
    public TableEnum findById(java.lang.String id) {
        log.debug("getting TableEnum instance with id: " + id);
        try {
            TableEnum instance = (TableEnum) getHibernateTemplate().get("com.orient.metamodel.metadomain.TableEnum", id);
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
    public List findByExample(TableEnum instance) {
        log.debug("finding TableEnum instance by example");
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
        log.debug("finding TableEnum instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TableEnum as model where model." + propertyName + "= ?";
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
     * Find by restriction id.
     *
     * @param restrictionId the restriction id
     * @return the list
     */
    public List findByRestrictionId(Object restrictionId) {
        return findByProperty(RESTRICTION_ID, restrictionId);
    }

    /**
     * Find by table enum sql.
     *
     * @param tableEnumSql the table enum sql
     * @return the list
     */
    public List findByTableEnumSql(Object tableEnumSql) {
        return findByProperty(TABLE_ENUM_SQL, tableEnumSql);
    }


    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all TableEnum instances");
        try {
            String queryString = "from TableEnum";
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
     * @return the table enum
     * @throws Exception the exception
     */
    public TableEnum merge(TableEnum detachedInstance) throws Exception {
        log.debug("merging TableEnum instance");
        try {
            TableEnum result = (TableEnum) getHibernateTemplate()
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
    public void attachDirty(TableEnum instance) {
        log.debug("attaching dirty TableEnum instance");
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
    public void attachClean(TableEnum instance) {
        log.debug("attaching clean TableEnum instance");
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
    public static TableEnumDAO getFromApplicationContext(ApplicationContext ctx) {
        return (TableEnumDAO) ctx.getBean("TableEnumDAO");
    }

}