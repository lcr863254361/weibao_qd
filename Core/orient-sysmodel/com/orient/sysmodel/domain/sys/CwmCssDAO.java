package com.orient.sysmodel.domain.sys;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for CwmCss entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .CwmCss
  * @author MyEclipse Persistence Tools 
 */

public class CwmCssDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(CwmCssDAO.class);
	//property constants



    
    public void save(CwmCss transientInstance) {
        log.debug("saving CwmCss instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(CwmCss persistentInstance) {
        log.debug("deleting CwmCss instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public CwmCss findById( CwmCss id) {
        log.debug("getting CwmCss instance with id: " + id);
        try {
            CwmCss instance = (CwmCss) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.sys.CwmCss", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(CwmCss instance) {
        log.debug("finding CwmCss instance by example");
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
      log.debug("finding CwmCss instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CwmCss as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all CwmCss instances");
		try {
			String queryString = "from CwmCss";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public CwmCss merge(CwmCss detachedInstance) {
        log.debug("merging CwmCss instance");
        try {
            CwmCss result = (CwmCss) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CwmCss instance) {
        log.debug("attaching dirty CwmCss instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(CwmCss instance) {
        log.debug("attaching clean CwmCss instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}