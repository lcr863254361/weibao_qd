package com.orient.sysmodel.domain.role;
// default package

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for PartOperations entities.
 * Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions.
 * Each of these methods provides additional information for how to configure it for the desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 * @see .PartOperations
 */

public class PartOperationsDAO extends HibernateDaoSupport {
    private static final Log log = LogFactory.getLog(PartOperationsDAO.class);
    //property constants
    public static final String ROLE_ID = "roleId";
    public static final String TABLE_ID = "tableId";
    public static final String COLUMN_ID = "columnId";
    public static final String OPERATIONS_ID = "operationsId";
    public static final String FILTER = "filter";
    public static final String IS_TABLE = "isTable";


    public void save(PartOperations transientInstance) {
        log.debug("saving PartOperations instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PartOperations persistentInstance) {
        log.debug("deleting PartOperations instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PartOperations findById(java.lang.Long id) {
        log.debug("getting PartOperations instance with id: " + id);
        try {
            PartOperations instance = (PartOperations) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.role.PartOperations", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List findByExample(PartOperations instance) {
        log.debug("finding PartOperations instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PartOperations instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from PartOperations as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByRoleId(Object roleId
    ) {
        return findByProperty(ROLE_ID, roleId
        );
    }

    public List findByTableId(Object tableId
    ) {
        return findByProperty(TABLE_ID, tableId
        );
    }

    public List findByColumnId(Object columnId
    ) {
        return findByProperty(COLUMN_ID, columnId
        );
    }

    public List findByOperationsId(Object operationsId
    ) {
        return findByProperty(OPERATIONS_ID, operationsId
        );
    }

    public List findByFilter(Object filter
    ) {
        return findByProperty(FILTER, filter
        );
    }

    public List findByIsTable(Object isTable
    ) {
        return findByProperty(IS_TABLE, isTable
        );
    }


    public List findAll() {
        log.debug("finding all PartOperations instances");
        try {
            String queryString = "from PartOperations";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PartOperations merge(PartOperations detachedInstance) {
        log.debug("merging PartOperations instance");
        try {
            PartOperations result = (PartOperations) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PartOperations instance) {
        log.debug("attaching dirty PartOperations instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PartOperations instance) {
        log.debug("attaching clean PartOperations instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<PartOperations> findByCriterion(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(PartOperations.class);
        for (Criterion criterion : criterions)
            criteria.add(criterion);
        return criteria.list();
    }
}