package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.TableColumn;
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
 * TableColumnDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180,
        rollbackFor = {RuntimeException.class, IOException.class, Exception.class})
public class TableColumnDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(TableColumnDAO.class);

    /**
     * The Constant TABLE_ID.
     */
    public static final String TABLE_ID = "table";

    /**
     * The Constant TYPE.
     */
    public static final String TYPE = "type";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(TableColumn transientInstance) throws Exception {
        log.debug("saving TableColumn instance");
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
    public void delete(TableColumn persistentInstance) throws Exception {
        log.debug("deleting TableColumn instance");
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
     * @return the table column
     */
    public TableColumn findById(java.lang.String id) {
        log.debug("getting TableColumn instance with id: " + id);
        try {
            return getHibernateTemplate().get(TableColumn.class, id);
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
    public List findByExample(TableColumn instance) {
        log.debug("finding TableColumn instance by example");
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
        log.debug("finding TableColumn instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TableColumn as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
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
     * Find by table and type.
     *
     * @param tableId the table id
     * @param type    the type
     * @return the list
     */
    public List findByTableAndType(Object tableId, Object type) {
        log.debug("finding TableColumn instance with property: " + TABLE_ID + ", value: " + tableId + "  " + TYPE + ", value: " + type);
        try {
            String queryString = "from TableColumn as model where model." + TABLE_ID + "= ? and model." + TYPE + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{tableId, type});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all TableColumn instances");
        try {
            String queryString = "from TableColumn";
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
     * @return the table column
     * @throws Exception the exception
     */
    public TableColumn merge(TableColumn detachedInstance) throws Exception {
        log.debug("merging TableColumn instance");
        try {
            TableColumn result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(TableColumn instance) {
        log.debug("attaching dirty TableColumn instance");
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
    public void attachClean(TableColumn instance) {
        log.debug("attaching clean TableColumn instance");
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
    public static TableColumnDAO getFromApplicationContext(ApplicationContext ctx) {
        return (TableColumnDAO) ctx.getBean("TableColumnDAO");
    }

}
