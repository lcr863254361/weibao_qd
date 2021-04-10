package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.RelationTableEnum;
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
 * RelationTableEnumDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class RelationTableEnumDAO extends HibernateDaoSupport {

    /**
     * The Constant log.
     */
    private static final Log log = LogFactory.getLog(RelationTableEnumDAO.class);

    /**
     * The Constant TABLE_ENUM_ID.
     */
    public static final String TABLE_ENUM_ID = "cwmTableEnum";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(RelationTableEnum transientInstance) throws Exception {
        log.debug("saving RelationTableEnum instance");
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
    public void delete(RelationTableEnum persistentInstance) throws Exception {
        log.debug("deleting RelationTableEnum instance");
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
     * @return the relation table enum
     */
    public RelationTableEnum findById(java.lang.String id) {
        log.debug("getting RelationTableEnum instance with id: " + id);
        try {
            RelationTableEnum instance = (RelationTableEnum) getHibernateTemplate().get("com.orient.metamodel.metadomain.RelationTableEnum", id);
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
    public List findByExample(RelationTableEnum instance) {
        log.debug("finding RelationTableEnum instance by example");
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
     * Find by table enum id.
     *
     * @param tableEnumId the table enum id
     * @return the list
     */
    public List findByTableEnumId(Object tableEnumId) {
        return findByProperty(TABLE_ENUM_ID, tableEnumId);
    }

    /**
     * Find by property.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list
     */
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding RelationTableEnum instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from RelationTableEnum as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
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
        log.debug("finding all RelationTableEnum instances");
        try {
            String queryString = "from RelationTableEnum";
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
     * @return the relation table enum
     * @throws Exception the exception
     */
    public RelationTableEnum merge(RelationTableEnum detachedInstance) throws Exception {
        log.debug("merging RelationTableEnum instance");
        try {
            RelationTableEnum result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(RelationTableEnum instance) {
        log.debug("attaching dirty RelationTableEnum instance");
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
    public void attachClean(RelationTableEnum instance) {
        log.debug("attaching clean RelationTableEnum instance");
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
    public static RelationTableEnumDAO getFromApplicationContext(ApplicationContext ctx) {
        return (RelationTableEnumDAO) ctx.getBean("RelationTableEnumDAO");
    }

}