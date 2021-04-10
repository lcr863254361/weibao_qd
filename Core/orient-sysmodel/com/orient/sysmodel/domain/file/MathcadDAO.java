package com.orient.sysmodel.domain.file;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Mathcad entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Mathcad
  * @author MyEclipse Persistence Tools 
 */

public class MathcadDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(MathcadDAO.class);
	//property constants



    
    public void save(Mathcad transientInstance) {
        log.debug("saving Mathcad instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Mathcad persistentInstance) {
        log.debug("deleting Mathcad instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Mathcad findById( Mathcad id) {
        log.debug("getting Mathcad instance with id: " + id);
        try {
            Mathcad instance = (Mathcad) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.file.Mathcad", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Mathcad instance) {
        log.debug("finding Mathcad instance by example");
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
      log.debug("finding Mathcad instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Mathcad as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all Mathcad instances");
		try {
			String queryString = "from Mathcad";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Mathcad merge(Mathcad detachedInstance) {
        log.debug("merging Mathcad instance");
        try {
            Mathcad result = (Mathcad) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Mathcad instance) {
        log.debug("attaching dirty Mathcad instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Mathcad instance) {
        log.debug("attaching clean Mathcad instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}