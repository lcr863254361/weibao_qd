package com.orient.sysmodel.domain.workflow;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for JbpmTaskAssign entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .JbpmTaskAssign
  * @author MyEclipse Persistence Tools 
 */

public class JbpmTaskAssignDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(JbpmTaskAssignDAO.class);
	//property constants
	public static final String PIID = "piid";
	public static final String SUB_PIID = "subPiid";
	public static final String TASKNAME = "taskname";
	public static final String USERNAME = "username";
	public static final String ASSIGN_USER_ID = "assignUser";



    
    public void save(JbpmTaskAssign transientInstance) {
        log.debug("saving JbpmTaskAssign instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(JbpmTaskAssign persistentInstance) {
        log.debug("deleting JbpmTaskAssign instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public JbpmTaskAssign findById( java.lang.Long id) {
        log.debug("getting JbpmTaskAssign instance with id: " + id);
        try {
            JbpmTaskAssign instance = (JbpmTaskAssign) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.workflow.JbpmTaskAssign", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(JbpmTaskAssign instance) {
        log.debug("finding JbpmTaskAssign instance by example");
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
      log.debug("finding JbpmTaskAssign instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from JbpmTaskAssign as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPiid(Object piid
	) {
		return findByProperty(PIID, piid
		);
	}
	
	public List findBySubPiid(Object subPiid
	) {
		return findByProperty(SUB_PIID, subPiid
		);
	}
	
	public List findByTaskname(Object taskname
	) {
		return findByProperty(TASKNAME, taskname
		);
	}
	
	public List findByUsername(Object username
	) {
		return findByProperty(USERNAME, username
		);
	}
	
	public List findByAssignUserId(Object assignUserId
	) {
		return findByProperty(ASSIGN_USER_ID, assignUserId
		);
	}
	

	public List findAll() {
		log.debug("finding all JbpmTaskAssign instances");
		try {
			String queryString = "from JbpmTaskAssign";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public JbpmTaskAssign merge(JbpmTaskAssign detachedInstance) {
        log.debug("merging JbpmTaskAssign instance");
        try {
            JbpmTaskAssign result = (JbpmTaskAssign) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(JbpmTaskAssign instance) {
        log.debug("attaching dirty JbpmTaskAssign instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(JbpmTaskAssign instance) {
        log.debug("attaching clean JbpmTaskAssign instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}