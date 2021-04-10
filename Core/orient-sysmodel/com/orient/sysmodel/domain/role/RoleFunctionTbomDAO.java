package com.orient.sysmodel.domain.role;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for RoleFunctionTbom entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .RoleFunctionTbom
  * @author MyEclipse Persistence Tools 
 */

public class RoleFunctionTbomDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(RoleFunctionTbomDAO.class);
	//property constants



    
    public void save(RoleFunctionTbom transientInstance) {
        log.debug("saving RoleFunctionTbom instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(RoleFunctionTbom persistentInstance) {
        log.debug("deleting RoleFunctionTbom instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public RoleFunctionTbom findById( RoleFunctionTbomId id) {
        log.debug("getting RoleFunctionTbom instance with id: " + id);
        try {
            RoleFunctionTbom instance = (RoleFunctionTbom) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.role.RoleFunctionTbom", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(RoleFunctionTbom instance) {
        log.debug("finding RoleFunctionTbom instance by example");
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
      log.debug("finding RoleFunctionTbom instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RoleFunctionTbom as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all RoleFunctionTbom instances");
		try {
			String queryString = "from RoleFunctionTbom";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public RoleFunctionTbom merge(RoleFunctionTbom detachedInstance) {
        log.debug("merging RoleFunctionTbom instance");
        try {
            RoleFunctionTbom result = (RoleFunctionTbom) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RoleFunctionTbom instance) {
        log.debug("attaching dirty RoleFunctionTbom instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(RoleFunctionTbom instance) {
        log.debug("attaching clean RoleFunctionTbom instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}