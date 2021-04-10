package com.orient.sysmodel.domain.user;
// default package


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for UserLoginHistory entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .UserLoginHistory
  * @author MyEclipse Persistence Tools 
 */

public class UserLoginHistoryDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(UserLoginHistoryDAO.class);
	//property constants
	public static final String USER_NAME = "userName";
	public static final String USER_DISPALYNAME = "userDispalyname";
	public static final String USER_IP = "userIp";
	public static final String OP_TYPE = "opType";
	public static final String OP_MESSAGE = "opMessage";
	public static final String USER_DEPTNAME = "userDeptname";



    
    public void save(UserLoginHistory transientInstance) {
        log.debug("saving UserLoginHistory instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(UserLoginHistory persistentInstance) {
        log.debug("deleting UserLoginHistory instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public UserLoginHistory findById( java.lang.Long id) {
        log.debug("getting UserLoginHistory instance with id: " + id);
        try {
            UserLoginHistory instance = (UserLoginHistory) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.user.UserLoginHistory", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserLoginHistory instance) {
        log.debug("finding UserLoginHistory instance by example");
        try {
            List results = getHibernateTemplate()
                    .findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding UserLoginHistory instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserLoginHistory as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUserName(Object userName
	) {
		return findByProperty(USER_NAME, userName
		);
	}
	
	public List findByUserDispalyname(Object userDispalyname
	) {
		return findByProperty(USER_DISPALYNAME, userDispalyname
		);
	}
	
	public List findByUserIp(Object userIp
	) {
		return findByProperty(USER_IP, userIp
		);
	}
	
	public List findByOpType(Object opType
	) {
		return findByProperty(OP_TYPE, opType
		);
	}
	
	public List findByOpMessage(Object opMessage
	) {
		return findByProperty(OP_MESSAGE, opMessage
		);
	}
	
	public List findByUserDeptname(Object userDeptname
	) {
		return findByProperty(USER_DEPTNAME, userDeptname
		);
	}
	

	public List findAll() {
		log.debug("finding all UserLoginHistory instances");
		try {
			String queryString = "from UserLoginHistory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public UserLoginHistory merge(UserLoginHistory detachedInstance) {
        log.debug("merging UserLoginHistory instance");
        try {
            UserLoginHistory result = (UserLoginHistory) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserLoginHistory instance) {
        log.debug("attaching dirty UserLoginHistory instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UserLoginHistory instance) {
        log.debug("attaching clean UserLoginHistory instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getHqlResult(String searchsql) {

		log.debug("getting  UserLoginHistory searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  UserLoginHistory searchsql failed", re);
			return null;
		}
	}
    
    public List getSqlResult(String searchsql) { 
		log.debug("getting  UserLoginHistory searchsql sentence" + searchsql);
		try {
			SQLQuery query = this.getSession().createSQLQuery(searchsql);
			List result = query.list();
			return result;
		} catch (RuntimeException re) {
			log.error("getting  UserLoginHistory searchsql failed", re);
			return null;
		}
	}
}