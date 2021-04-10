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
 	* A data access object (DAO) providing persistence and search support for RoleArith entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .RoleArith
  * @author MyEclipse Persistence Tools 
 */

public class RoleArithDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(RoleArithDAO.class);
	//property constants



    
    public void save(RoleArith transientInstance) {
        log.debug("saving RoleArith instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(RoleArith persistentInstance) {
        log.debug("deleting RoleArith instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public RoleArith findById( RoleArithId id) {
        log.debug("getting RoleArith instance with id: " + id);
        try {
            RoleArith instance = (RoleArith) getSession()
                    .get("com.orient.sysmodel.domain.role.RoleArith", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(RoleArith instance) {
        log.debug("finding RoleArith instance by example");
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
      log.debug("finding RoleArith instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RoleArith as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all RoleArith instances");
		try {
			String queryString = "from RoleArith";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public RoleArith merge(RoleArith detachedInstance) {
        log.debug("merging RoleArith instance");
        try {
            RoleArith result = (RoleArith) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RoleArith instance) {
        log.debug("attaching dirty RoleArith instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(RoleArith instance) {
        log.debug("attaching clean RoleArith instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}