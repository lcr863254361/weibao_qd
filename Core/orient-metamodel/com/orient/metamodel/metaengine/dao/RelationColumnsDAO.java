package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.RelationColumns;
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
 * RelationColumnsDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class RelationColumnsDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(RelationColumnsDAO.class);

    /**
     * The Constant RELATIONTYPE.
     */
    public static final String RELATIONTYPE = "relationtype";

    /**
     * The Constant OWNERSHIP.
     */
    public static final String OWNERSHIP = "ownership";

    /**
     * The Constant IS_FK.
     */
    public static final String IS_FK = "isFk";

    /**
     * The Constant COLUMN_ID.
     */
    public static final String COLUMN_ID = "cwmTabColumnsByColumnId";

    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(RelationColumns transientInstance) throws Exception {
        log.debug("saving RelationColumns instance");
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
    public void delete(RelationColumns persistentInstance) throws Exception {
        log.debug("deleting RelationColumns instance");
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
     * @return the relation columns
     */
    public RelationColumns findById(java.lang.String id) {
        log.debug("getting RelationColumns instance with id: " + id);
        try {
            RelationColumns instance = (RelationColumns) getHibernateTemplate().get("com.orient.metamodel.metadomain.RelationColumns", id);
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
    public List findByExample(RelationColumns instance) {
        log.debug("finding RelationColumns instance by example");
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
        log.debug("finding RelationColumns instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from RelationColumns as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by relationtype.
     *
     * @param relationtype the relationtype
     * @return the list
     */
    public List findByRelationtype(Object relationtype
    ) {
        return findByProperty(RELATIONTYPE, relationtype
        );
    }

    /**
     * Find by ownership.
     *
     * @param ownership the ownership
     * @return the list
     */
    public List findByOwnership(Object ownership
    ) {
        return findByProperty(OWNERSHIP, ownership
        );
    }

    /**
     * Find by is fk.
     *
     * @param isFK the is fk
     * @return the list
     */
    public List findByIsFK(Object isFK
    ) {
        return findByProperty(IS_FK, isFK
        );
    }

    /**
     * Find by column id.
     *
     * @param ColumnId the column id
     * @return the list
     */
    public List findByColumnId(Object ColumnId
    ) {
        return findByProperty(COLUMN_ID, ColumnId
        );
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all RelationColumns instances");
        try {
            String queryString = "from RelationColumns";
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
     * @return the relation columns
     * @throws Exception the exception
     */
    public RelationColumns merge(RelationColumns detachedInstance) throws Exception {
        log.debug("merging RelationColumns instance");
        try {
            RelationColumns result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(RelationColumns instance) {
        log.debug("attaching dirty RelationColumns instance");
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
    public void attachClean(RelationColumns instance) {
        log.debug("attaching clean RelationColumns instance");
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
    public static RelationColumnsDAO getFromApplicationContext(ApplicationContext ctx) {
        return (RelationColumnsDAO) ctx.getBean("RelationColumnsDAO");
    }
}