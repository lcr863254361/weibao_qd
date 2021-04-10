package com.orient.sysmodel.domain.syslog;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for CodeToName entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .CodeToName
  * @author MyEclipse Persistence Tools 
 */

public class CodeToNameDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(CodeToNameDAO.class);
	//property constants



    
    public void save(CodeToName transientInstance) {
        log.debug("saving CodeToName instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(CodeToName persistentInstance) {
        log.debug("deleting CodeToName instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public CodeToName findById( CodeToNameId id) {
        log.debug("getting CodeToName instance with id: " + id);
        try {
            CodeToName instance = (CodeToName) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.syslog.CodeToName", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(CodeToName instance) {
        log.debug("finding CodeToName instance by example");
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
      log.debug("finding CodeToName instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CodeToName as model where model.id." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all CodeToName instances");
		try {
			String queryString = "from CodeToName";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public CodeToName merge(CodeToName detachedInstance) {
        log.debug("merging CodeToName instance");
        try {
            CodeToName result = (CodeToName) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CodeToName instance) {
        log.debug("attaching dirty CodeToName instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(CodeToName instance) {
        log.debug("attaching clean CodeToName instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getSqlResult(String searchsql) {

		log.debug("getting  CodeToName searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  CodeToName searchsql failed", re);
			return null;
		}
	}
}