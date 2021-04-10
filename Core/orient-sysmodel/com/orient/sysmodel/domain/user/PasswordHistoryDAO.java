package com.orient.sysmodel.domain.user;
// default package

import com.orient.sysmodel.domain.user.User;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for PasswordHistory entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .PasswordHistory
  * @author MyEclipse Persistence Tools 
 */

public class PasswordHistoryDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(PasswordHistoryDAO.class);
	//property constants
	public static final String PASSWORD = "password";



    
    public void save(PasswordHistory transientInstance) {
        log.debug("saving PasswordHistory instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(PasswordHistory persistentInstance) {
        log.debug("deleting PasswordHistory instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public PasswordHistory findById( java.lang.Long id) {
        log.debug("getting PasswordHistory instance with id: " + id);
        try {
            PasswordHistory instance = (PasswordHistory) getSession()
                    .get("com.orient.sysmodel.domain.user.PasswordHistory", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(PasswordHistory instance) {
        log.debug("finding PasswordHistory instance by example");
        try {
            List results = getSession()
                    .createCriteria("PasswordHistory")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding PasswordHistory instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from PasswordHistory as model where model." 
         						+ propertyName + "= ? order by model.passwordSetTime desc ";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPassword(Object password
	) {
		return findByProperty(PASSWORD, password
		);
	}
	

	public List findAll() {
		log.debug("finding all PasswordHistory instances");
		try {
			String queryString = "from PasswordHistory";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public PasswordHistory merge(PasswordHistory detachedInstance) {
        log.debug("merging PasswordHistory instance");
        try {
            PasswordHistory result = (PasswordHistory) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PasswordHistory instance) {
        log.debug("attaching dirty PasswordHistory instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(PasswordHistory instance) {
        log.debug("attaching clean PasswordHistory instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}