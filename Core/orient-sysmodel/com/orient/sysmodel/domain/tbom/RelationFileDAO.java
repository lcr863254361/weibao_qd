/*
 * Title: RelationFileDAO.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * RelationTBOM entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.rcp.design.domain.RelationTbom
 * @author MyEclipse Persistence Tools
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
		RuntimeException.class, IOException.class,Exception.class })
public class RelationFileDAO extends HibernateDaoSupport  {
    
    /** The Constant log. */
    private static final Log log = LogFactory.getLog(RelationFileDAO.class);
	//property constants



	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	protected void initDao() {
		//do nothing
	}
    
    /**
	 * Save.
	 * 
	 * @param transientInstance
	 *            the transient instance
	 */
    public void save(RelationFile transientInstance) {
        log.debug("saving RelationFile instance");
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
	 * @param persistentInstance
	 *            the persistent instance
	 */
	public void delete(RelationFile persistentInstance) {
        log.debug("deleting RelationFile instance");
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
	 * @param id
	 *            the id
	 * 
	 * @return the relation file
	 */
    public RelationFile findById( java.lang.Long id) {
        log.debug("getting RelationFile instance with id: " + id);
        try {
        	RelationFile instance = (RelationFile) getHibernateTemplate()
                    .get("com.rcp.design.domain.RelationFile", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /**
	 * Find by example.
	 * 
	 * @param instance
	 *            the instance
	 * 
	 * @return the list
	 */
    public List findByExample(RelationFile instance) {
        log.debug("finding RelationFile instance by example");
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
	 * @param propertyName
	 *            the property name
	 * @param value
	 *            the value
	 * 
	 * @return the list
	 */
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding RelationFile instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RelationFile as model where model." 
         						+ propertyName + "= ?";
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
		log.debug("finding all RelationFile instances");
		try {
			String queryString = "from RelationFile";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    /**
	 * Merge.
	 * 
	 * @param detachedInstance
	 *            the detached instance
	 * 
	 * @return the relation file
	 */
    public RelationFile merge(RelationFile detachedInstance) {
        log.debug("merging RelationFile instance");
        try {
        	RelationFile result = (RelationFile) getHibernateTemplate()
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
	 * @param instance
	 *            the instance
	 */
    public void attachDirty(RelationFile instance) {
        log.debug("attaching dirty RelationFile instance");
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
	 * @param instance
	 *            the instance
	 */
    public void attachClean(RelationFile instance) {
        log.debug("attaching clean RelationFile instance");
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
	 * @param ctx
	 *            the ctx
	 * 
	 * @return the from application context
	 */
	public static RelationFileDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (RelationFileDAO) ctx.getBean("RelationFileDAO");
	}
}