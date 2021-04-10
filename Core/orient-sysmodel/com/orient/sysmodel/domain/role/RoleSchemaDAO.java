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
 	* A data access object (DAO) providing persistence and search support for RoleSchema entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .RoleSchema
  * @author MyEclipse Persistence Tools 
 */

public class RoleSchemaDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(RoleSchemaDAO.class);
	//property constants



    
    public void save(RoleSchema transientInstance) {
        log.debug("saving RoleSchema instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(RoleSchema persistentInstance) {
        log.debug("deleting RoleSchema instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public RoleSchema findById( RoleSchemaId id) {
        log.debug("getting RoleSchema instance with id: " + id);
        try {
            RoleSchema instance = (RoleSchema) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.role.RoleSchema", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(RoleSchema instance) {
        log.debug("finding RoleSchema instance by example");
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
      log.debug("finding RoleSchema instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RoleSchema as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all RoleSchema instances");
		try {
			String queryString = "from RoleSchema";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public RoleSchema merge(RoleSchema detachedInstance) {
        log.debug("merging RoleSchema instance");
        try {
            RoleSchema result = (RoleSchema) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RoleSchema instance) {
        log.debug("attaching dirty RoleSchema instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(RoleSchema instance) {
        log.debug("attaching clean RoleSchema instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}