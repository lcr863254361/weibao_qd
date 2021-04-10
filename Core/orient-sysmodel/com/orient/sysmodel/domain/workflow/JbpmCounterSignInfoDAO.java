package com.orient.sysmodel.domain.workflow;
 

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for JbpmCounterSignInfo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .JbpmCounterSignInfo
  * @author MyEclipse Persistence Tools 
 */

public class JbpmCounterSignInfoDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(JbpmCounterSignInfoDAO.class);
	

    
    public void save(JbpmCounterSignInfo transientInstance) {
        log.debug("saving JbpmCounterSignInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(JbpmCounterSignInfo persistentInstance) {
        log.debug("deleting JbpmCounterSignInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public JbpmCounterSignInfo findById( JbpmCounterSignInfoId id) {
        log.debug("getting JbpmCounterSignInfo instance with id: " + id);
        try {
            JbpmCounterSignInfo instance = (JbpmCounterSignInfo) getHibernateTemplate()
                    .get("JbpmCounterSignInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(JbpmCounterSignInfo instance) {
        log.debug("finding JbpmCounterSignInfo instance by example");
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
      log.debug("finding JbpmCounterSignInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from JbpmCounterSignInfo as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all JbpmCounterSignInfo instances");
		try {
			String queryString = "from JbpmCounterSignInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public JbpmCounterSignInfo merge(JbpmCounterSignInfo detachedInstance) {
        log.debug("merging JbpmCounterSignInfo instance");
        try {
            JbpmCounterSignInfo result = (JbpmCounterSignInfo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(JbpmCounterSignInfo instance) {
        log.debug("attaching dirty JbpmCounterSignInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(JbpmCounterSignInfo instance) {
        log.debug("attaching clean JbpmCounterSignInfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}